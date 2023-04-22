import { JSX } from "preact/jsx-runtime";
import { cloneElement, Component, VNode } from "preact/src/index.js";
import { identity, Obj, resolve } from "../util.ts";
import { assert } from "https://deno.land/std@0.178.0/_util/asserts.ts";

export const managedTags = ["SELECT", "INPUT", "TEXTAREA"];
export const readonlyTypes = [
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

export type managed =
  | VNode<HTMLSelectElement>
  | VNode<HTMLInputElement>
  | VNode<HTMLTextAreaElement>;

export interface FormProps extends JSX.HTMLAttributes<HTMLFormElement> {
  readonly?: boolean;
  allrequired?: boolean;
  values?: Obj;
}

export function Form(props: FormProps) {
  const children = configChildren(props);
  return (
    <form {...props} class="box-column">
      {children}
    </form>
  );
}

function configChildren(
  { children, allrequired, readonly, values }: FormProps,
) {
  const components: Component[] = [];
  if (Array.isArray(children)) {
    components.push(...children as Component[]);
  } else {
    components.push(children as Component);
  }
  return components
    .map(asVnode)
    .filter(isManaged)
    .map(asManaged)
    .map(readonly ? cloneReadOnly : identity)
    .map(allrequired ? cloneRequired : identity)
    .map((node) => {
      node.props.value = resolve(node.props.name, values);
      return node;
    });
}

function cloneReadOnly(node: managed) {
  assert(typeof node.type !== "string");
  if (node.type.name.toUpperCase() === "SELECT") {
    return cloneElement(node, { disabled: true });
  } else {
    return cloneElement(node, { readonly: true });
  }
}

function cloneRequired(node: managed) {
  if (isRequirable(node)) {
    return cloneElement(node, { required: true });
  } else {
    return cloneElement(node);
  }
}

function isRequirable(child: managed) {
  assert(typeof child.type !== "string");
  return child.type.name.toUpperCase() !== "INPUT" ||
    readonlyTypes.includes(child.props.type);
}

function asVnode(comp: Component) {
  return comp as unknown as VNode;
}

function asManaged(node: VNode) {
  return node as unknown as managed;
}

function isManaged(child: VNode) {
  assert(typeof child.type !== "string");
  return managedTags.includes(child.type.name.toUpperCase());
}
