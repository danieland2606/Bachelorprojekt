import { JSX } from "preact/jsx-runtime";
import { asset } from "$fresh/runtime.ts";
import { TableButton } from "$this/components/TableButton.tsx";

export interface Item {
  item: Array<number | string | boolean>;
  actions: {
    details: string | undefined;
    edit: string | undefined;
    delete: string | undefined;
  };
}

export interface TableItems {
  headers: Array<string>;
  items: Array<Item>;
}

export interface TableProps extends JSX.HTMLAttributes<HTMLTableElement> {
  tabledata: TableItems;
}

function hasAnyActions(items: Array<Item>) {
  return !!(items?.some(({ actions }) => actions !== undefined));
}

export function Table(props: TableProps) {
  const { headers, items } = props.tabledata;
  const hasActions = hasAnyActions(items);
  return (
    <div class="overflow-x-auto">
      <table {...props} class={`${props.class} table w-full table-zebra`}>
        <thead>
          <tr>
            {headers.map((header) => <th scope="col">{header}</th>)}
            {hasActions && <th scope="col">Aktionen</th>}
          </tr>
        </thead>
        <tbody>
          {items.map(({ item, actions }) => (
            <tr>
              {item.map((field) => <td>{field}</td>)}
              {hasActions && (
                <td>
                  {actions.details && (
                    <TableButton
                      href={actions.details}
                      src={asset("/article.svg")}
                      alt="details"
                    />
                  )}
                  {actions.edit && (
                    <TableButton
                      href={actions.edit}
                      src={asset("/edit.svg")}
                      alt="edit"
                    />
                  )}
                  {actions.delete && (
                    <TableButton
                      href={actions.delete}
                      src={asset("/trash.svg")}
                      alt="delete"
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
