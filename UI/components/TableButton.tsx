import { JSX } from "preact/jsx-runtime";

interface TableButtonProps extends JSX.HTMLAttributes<HTMLAnchorElement> {
  src: string;
  alt: string;
}

export function TableButton(props: TableButtonProps) {
  return (
    <a {...props} class="btn btn-outline btn-sm rounded-none p-1 aspect-square">
      <image src={props.src} alt={props.alt} />
    </a>
  );
}
