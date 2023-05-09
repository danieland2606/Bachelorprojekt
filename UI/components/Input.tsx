import { JSX } from "preact/jsx-runtime";
import { capitalize } from "../util/util.ts";

export interface InputProps extends JSX.HTMLAttributes<HTMLInputElement> {
  labeltext: string;
}

export interface SelectProps extends JSX.HTMLAttributes<HTMLSelectElement> {
  labeltext: string;
  options?: readonly string[];
}

export function Select(props: SelectProps) {
  return (
    <div class="cell">
      <label id={props.name}>{props.labeltext}</label>
      <select {...props} label={props.name}>
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
  return (
    <div class="cell">
      <label id={props.name}>{props.labeltext}</label>
      <input {...props} label={props.name}></input>
    </div>
  );
}
