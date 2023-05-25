import { JSX } from "preact/jsx-runtime";

export function ButtonRow(props: JSX.HTMLAttributes<HTMLDivElement>) {
  return (
    <div
      {...props}
      class={`${props.class} flex max-md:flex-col py-5 justify-evenly gap-4`
        .trim()}
    >
      {props.children}
    </div>
  );
}
