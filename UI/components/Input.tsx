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
  labeltext?: string;
  unit?: string;
}

export interface SelectProps extends JSX.HTMLAttributes<HTMLSelectElement> {
  labeltext?: string;
  readonly?: boolean;
  options?: readonly string[];
}

export function Select(props: SelectProps) {
  const { name, options, label, clss, subProps } = prepareSelectProps(props);
  return (
    <>
      <label class="label" for={name}>{label}</label>
      <select
        {...subProps}
        id={name}
        class={`${clss} select w-full select-bordered`}
      >
        <option value=""></option>
        {subProps.children ??
          options?.map((option) => (
            <option value={option}>{pretty(option)}</option>
          )) ?? ""}
      </select>
    </>
  );
}

export function Input(props: InputProps) {
  const { clss, name, label, subProps, unit } = prepareInputProps(props);
  return (
    <>
      <label class="label" for={name}>{label}</label>
      <div class={unit ? "input-group" : undefined}>
        <input {...subProps} id={name} class={`${clss} input input-bordered`} />
        {unit && <span>{unit}</span>}
      </div>
    </>
  );
}

function prepareSelectProps(props: SelectProps) {
  const { labeltext: label, options, name } = props;
  delete props.labeltext;
  delete props.options;
  let clss = (props.class ?? "") as string;
  if (props.readonly) {
    delete props.readonly;
    clss += " pointer-events-none";
  }
  return { name, options, label, clss: clss.trim(), subProps: props };
}

function prepareInputProps(props: InputProps) {
  const { labeltext: label, name, unit } = props;
  delete props.labeltext;
  let clss = (props.class ?? "") as string;
  if (isDisabled(props)) {
    delete props.readonly;
    clss += " pointer-events-none";
  }
  if (notRequirable.includes(type(props))) {
    delete props.required;
  }
  return { name, label, unit, clss: clss.trim(), subProps: props };
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
