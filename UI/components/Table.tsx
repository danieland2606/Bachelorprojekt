import { JSX } from "preact/jsx-runtime";

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
                  {actions.details && <a class="btn btn-outline btn-sm capitalize rounded-none" href={actions.details}>details</a>}
                  {actions.edit && <a href={actions.edit}>edit</a>}
                  {actions.delete && <a href={actions.delete}>delete</a>}
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
