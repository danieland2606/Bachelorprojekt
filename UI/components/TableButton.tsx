import { JSX } from "preact/jsx-runtime";

export type kind = "details" | "edit" | "delete";

export interface TableButtonProps
  extends JSX.HTMLAttributes<HTMLAnchorElement> {
  kind: kind;
}

export function TableButton(props: TableButtonProps) {
  return (
    <a {...props} class="btn btn-outline btn-sm rounded-none p-1 aspect-square">
      {getImage(props.kind)}
    </a>
  );
}

function getImage(kind: kind) {
  switch (kind) {
    case "details":
      return (
        <svg
          xmlns="http://www.w3.org/2000/svg"
          //class="icon icon-tabler icon-tabler-article" //Tabler css einbinden??
          width="24"
          height="24"
          viewBox="0 0 24 24"
          stroke-width="2"
          stroke="currentColor"
          fill="none"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <path stroke="none" d="M0 0h24v24H0z" fill="none" />
          <path d="M3 4m0 2a2 2 0 0 1 2 -2h14a2 2 0 0 1 2 2v12a2 2 0 0 1 -2 2h-14a2 2 0 0 1 -2 -2z" />
          <path d="M7 8h10" />
          <path d="M7 12h10" />
          <path d="M7 16h10" />
        </svg>
      );
    case "edit":
      return (
        <svg
          xmlns="http://www.w3.org/2000/svg"
          //class="icon icon-tabler icon-tabler-edit"
          width="24"
          height="24"
          viewBox="0 0 24 24"
          stroke-width="2"
          stroke="currentColor"
          fill="none"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <path stroke="none" d="M0 0h24v24H0z" fill="none" />
          <path d="M7 7h-1a2 2 0 0 0 -2 2v9a2 2 0 0 0 2 2h9a2 2 0 0 0 2 -2v-1" />
          <path d="M20.385 6.585a2.1 2.1 0 0 0 -2.97 -2.97l-8.415 8.385v3h3l8.385 -8.415z" />
          <path d="M16 5l3 3" />
        </svg>
      );
    case "delete":
      return (
        <svg
          xmlns="http://www.w3.org/2000/svg"
          //class="icon icon-tabler icon-tabler-trash"
          width="24"
          height="24"
          viewBox="0 0 24 24"
          stroke-width="2"
          stroke="currentColor"
          fill="none"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <path stroke="none" d="M0 0h24v24H0z" fill="none" />
          <path d="M4 7l16 0" />
          <path d="M10 11l0 6" />
          <path d="M14 11l0 6" />
          <path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" />
          <path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" />
        </svg>
      );
    default:
      return "error: unknown button type";
  }
}
