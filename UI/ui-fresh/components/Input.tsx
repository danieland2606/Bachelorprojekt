import { JSX } from "preact/jsx-runtime";
import { capitalize } from "../util.ts";

export interface InputProps extends JSX.HTMLAttributes<HTMLInputElement> {
  labeltext: string;
}

export interface SelectProps extends JSX.HTMLAttributes<HTMLSelectElement> {
  labeltext: string;
  options: Array<{ value: string; label: string }>;
}

export interface Option {
  value: string;
  label: string | undefined;
}

export function opt(val: string, lbl: string) {
  return { value: val, label: lbl };
}

function labelify(val: string) {
  const uml = val
    .replaceAll("ae", "ä")
    .replaceAll("ue", "ü")
    .replaceAll("oe", "ö");
  return capitalize(uml);
}

export function makeOptions(vals: Array<Option>) {
  return vals.map(({ value, label }) => (
    <option value={value}>{label || labelify(value)}</option>
  ));
}

export function Select(props: SelectProps) {
  return (
    <div class="cell">
      <label id={props.name}>{props.labeltext}</label>
      <select required {...props} label={props.name}>
        {props.children}
      </select>
    </div>
  );
}

export function Input(props: InputProps) {
  return (
    <div class="cell">
      <label id={props.name}>{props.labeltext}</label>
      <input required {...props} label={props.name}></input>
    </div>
  );
}
