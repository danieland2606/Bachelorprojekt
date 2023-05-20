import { JSX } from "preact/jsx-runtime";
import { Obj, resolve } from "$this/common/util.ts";
import {
  cloneElement,
  Component,
  ComponentChildren,
  VNode,
} from "preact/src/index.js";

export type FormProps = JSX.HTMLAttributes<HTMLFormElement> & Data;
export type managed =
  | VNode<HTMLSelectElement>
  | VNode<HTMLInputElement>
  | VNode<HTMLTextAreaElement>;
type Child = Component | VNode | managed;

interface Data {
  editable?: string[];
  allrequired?: boolean;
  values?: Obj;
}

const managedTags = ["SELECT", "INPUT", "TEXTAREA"];

export function Form(props: FormProps) {
  const { children, allrequired, editable, values } = props;
  const data = { allrequired, editable, values };
  const configured = configChildren(children, data);
  return (
    <form {...props} class="box-column">
      {configured}
    </form>
  );
}

function configChildren(children: ComponentChildren, data: Data) {
  return asComponentArray(children)
    .map((component) => configComponent(component, data));
}

function asComponentArray(children: ComponentChildren) {
  if (Array.isArray(children)) {
    return children as Component[];
  } else {
    return [children as Component];
  }
}

function configComponent(component: Child, data: Data): Child {
  if (!isVNode(component)) {
    return component;
  }
  if (!isManaged(component as VNode)) {
    return configVNode(component as VNode, data) as Child;
  }
  return configManagedElement(component as managed, data) as Child;
}

function configVNode(node: VNode, data: Data) {
  if (node != null && node.props.children) {
    const children = configChildren(node.props.children, data);
    return cloneElement(node, { children });
  }
  return node;
}

function configManagedElement(element: managed, data: Data) {
  const name = element.props.name;
  let val = element.props.value;
  if (data.values) {
    val = getSafely(name, data.values) || val;
  }
  const props = {
    required: data.allrequired,
    readonly: isReadOnly(name, data.editable),
    value: val,
  };
  return cloneElement(element, props);
}

function getSafely(property: string, data?: Obj) {
  try {
    return String(resolve(property, data));
  } catch (error) {
    console.error(
      `Exception on resolving property ${property} in ${JSON.stringify(data)}
      Exception was ${error}`,
    );
    return "";
  }
}

function isReadOnly(name: string, editable?: string[]) {
  return editable != null && !editable.includes(name);
}

function isVNode(child?: Child | null) {
  return child != null && typeof child === "object" && "key" in child &&
    "type" in child;
}

function isManaged(node?: VNode | null) {
  return node != null && typeof (node.type) !== "string" &&
    managedTags.includes(node.type.name.toUpperCase());
}
