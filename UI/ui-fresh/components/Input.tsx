import { JSX } from "preact/jsx-runtime";

export interface InputProps extends JSX.HTMLAttributes<HTMLInputElement> {
  labeltext: string;
}

export interface SelectProps extends JSX.HTMLAttributes<HTMLSelectElement> {
  labeltext: string;
}

export function Select(props: SelectProps) {
  return (
    <div class="cell">
      <label id={props.name}>{props.labeltext}</label>
      <select {...props} label={props.name}>
        {props.children}
      </select>
    </div>
  );
}

export function Input(props: InputProps) {
  return (
    <div class="cell">
      <label id={props.name}>{props.labeltext}</label>
      <input {...props} label={props.name}></input>
    </div>
  );
}
