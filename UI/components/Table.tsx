import { JSX } from "preact/jsx-runtime";
import { TableButton } from "$this/components/TableButton.tsx";

export interface Item {
  row: (string | number | boolean)[];
  actions: TableActions;
  active: boolean;
}

export interface TableActions {
  details?: string;
  edit?: string;
  delete?: string;
}

export interface TableItems {
  headers: string[];
  items: Item[];
}

export interface TableProps extends JSX.HTMLAttributes<HTMLTableElement> {
  tabledata?: TableItems;
}

function hasAnyActions(items?: Item[]) {
  return !!(items?.some(({ actions }) => actions != null));
}

export function itemSearch(search: string): (item: Item) => boolean {
  if (!search) {
    return () => true;
  }
  return (item: Item) =>
    item.row.join(" ").toLowerCase().includes(search.toLowerCase());
}

export function Table(props: TableProps) {
  const { headers, items } = props.tabledata ?? {};
  delete props.tabledata;
  const hasActions = hasAnyActions(items);
  return (
    <div class="overflow-x-auto">
      <table
        {...props}
        class={`${props.class} w-full table table-zebra table-compact lg:table-normal`}
      >
        <thead>
          <tr>
            {headers?.map((header) => <th scope="col">{header}</th>)}
            {hasActions && <th scope="col"></th>}
          </tr>
        </thead>
        <tbody>
          {items?.map(({ row, actions, active }) => (
            <tr class={active ? undefined : "saturate-0 text-gray-400"}>
              {row.map((field) => <td>{field}</td>)}
              {hasActions && (
                <td>
                  {actions.details && (
                    <TableButton
                      href={actions.details}
                      kind="details"
                    />
                  )}
                  {actions.edit && (
                    <TableButton
                      href={actions.edit}
                      kind="edit"
                      disabled={!active}
                    />
                  )}
                  {actions.delete && (
                    <TableButton
                      href={actions.delete}
                      kind="delete"
                    />
                  )}
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
