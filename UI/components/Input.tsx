import { JSX } from "preact/jsx-runtime";
import { capitalize } from "$this/common/util.ts";

export const notRequirable = [
  "button",
  "submit",
  "hidden",
  "range",
  "color",
];
export const disabledTypes = [
  "range",
  "color",
  "checkbox",
  "radio",
  "button",
];

export interface InputProps extends JSX.HTMLAttributes<HTMLInputElement> {
  labeltext: string;
}

export interface SelectProps extends JSX.HTMLAttributes<HTMLSelectElement> {
  labeltext: string;
  readonly?: boolean;
  options?: readonly string[];
}

export function Select(props: SelectProps) {
  let clss;
  if (props.readonly) {
    delete props.readonly;
    clss = "pointer-events-none select w-full select-borderd";
  } else {
    clss = "select w-full select-borderd";
  }
  return (
    <div class="cell">
      <label class="label" id={props.name}>{props.labeltext}</label>
      <select {...props} label={props.name} class={clss}>
        {props.children ??
          props.options?.map((option) => (
            <option value={option}>{pretty(option)}</option>
          )) ?? ""}
      </select>
    </div>
  );
}

function pretty(val: string) {
  if (val === "none") {
    return "";
  }
  const pretty = val.replaceAll("-", " ").replaceAll("ae", "ä").replaceAll(
    "oe",
    "ö",
  ).replaceAll("ue", "ü");
  return capitalize(pretty);
}

export function Input(props: InputProps) {
  if (isDisabled(props)) {
    delete props.readonly;
    props.class += " pointer-events-none";
  }
  if (notRequirable.includes(type(props))) {
    delete props.required;
  }
  return (
    <div class="cell">
      <label class="label" id={props.name}>{props.labeltext}</label>
      <input {...props} class="input input-borderd" label={props.name}></input>
    </div>
  );
}

function isDisabled(props: InputProps) {
  return props.readonly && disabledTypes.includes(type(props));
}

function type(props: InputProps) {
  if (!props.type) {
    throw new Error(
      `type of input ${props.name} is set incorrectly: ${props.type}`,
    );
  }
  return props.type as string;
}
