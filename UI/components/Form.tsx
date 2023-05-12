import { JSX } from "preact/jsx-runtime";
import {
  cloneElement,
  Component,
  ComponentChildren,
  VNode,
} from "preact/src/index.js";
import { Obj, resolve } from "$this/util/util.ts";

export type FormProps = JSX.HTMLAttributes<HTMLFormElement> & Data;
export type managed =
  | VNode<HTMLSelectElement>
  | VNode<HTMLInputElement>
  | VNode<HTMLTextAreaElement>;
type ManagedProp = { component: Child; data: Data };
type Child = Component | VNode | managed;

interface Data {
  readonly?: boolean;
  allrequired?: boolean;
  values?: Obj;
}

export const managedTags = ["SELECT", "INPUT", "TEXTAREA"];
export const requiredTypes = [
  "text",
  "search",
  "url",
  "tel",
  "email",
  "password",
  "date",
  "month",
  "week",
  "time",
  "datetime-local",
  "number",
];
export const disabledTypes = [
  "range",
  "color",
  "checkbox",
  "radio",
  "button",
];

export function Form(props: FormProps) {
  const { children, allrequired, readonly, values } = props;
  const data = { allrequired, readonly, values };
  const configured = configChildren(children, data);
  return (
    <form {...props} class="box-column">
      {configured}
    </form>
  );
}

function configChildren(children: ComponentChildren, data: Data) {
  return asComponentArray(children)
    .map((component) => ({ component, data }))
    .map(configComponent);
}

function asComponentArray(children: ComponentChildren) {
  if (Array.isArray(children)) {
    return children as Component[];
  } else {
    return [children as Component];
  }
}

function configComponent(prop: ManagedProp): Child {
  return configManagedElement(prop) ?? configVNode(prop) ?? prop.component;
}

function configManagedElement({ component, data }: ManagedProp) {
  let element = asManaged(component);
  if (element != null) {
    if (data.readonly) {
      element = cloneReadOnly(element);
    }
    if (data.allrequired) {
      element = cloneRequired(element);
    }
    const val = resolve(element.props.name, data.values) ?? "";
    element.props.value = val as string;
  }
  return element;
}

function isManaged(node?: VNode | null) {
  return node != null && typeof (node.type) !== "string" &&
    managedTags.includes(node.type.name.toUpperCase());
}

function asManaged(child?: Child) {
  const node = asVNode(child);
  return isManaged(node) ? child as managed : null;
}

function configVNode({ component, data }: ManagedProp) {
  const node = asVNode(component);
  if (node != null && node.props.children) {
    const children = configChildren(node.props.children, data);
    return cloneElement(node, { children });
  }
  return node;
}

function isVNode(child?: Child | null) {
  return child != null && typeof child === "object" && "key" in child &&
    "type" in child;
}

function asVNode(child?: Child) {
  return isVNode(child) ? child as VNode : null;
}

function cloneReadOnly(node: managed) {
  if (isDisableable(node)) {
    return cloneElement(node, { disabled: true });
  } else if (!isHidden(node)) {
    return cloneElement(node, { readonly: true });
  } else {
    return cloneElement(node);
  }
}

function isDisableable(node: managed) {
  if (typeof node.type !== "string") {
    const type = node.type.name.toUpperCase();
    return type === "SELECT" ||
      type === "INPUT" && disabledTypes.includes(node.props.type);
  }
  return false;
}

function isHidden(node: managed) {
  if (typeof node.type !== "string") {
    const type = node.type.name.toUpperCase();
    return type === "INPUT" && node.props.type === "hidden";
  }
  return false;
}

function cloneRequired(node: managed) {
  if (isRequirable(node)) {
    return cloneElement(node, { required: true });
  } else {
    return cloneElement(node);
  }
}

function isRequirable(child: managed) {
  return typeof child.type !== "string" &&
    (child.type.name.toUpperCase() !== "INPUT" ||
      requiredTypes.includes(child.props.type));
}
