function hasAnyActions(items) {
  return !!(items?.some(({ actions }) => actions !== undefined));
}

export function Table(props) {
  const { headers, items } = props.tabledata;
  const hasActions = hasAnyActions(items);
  return (
    <table {...props}>
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
                {actions?.details && <a href={actions.details}>details</a>}
                {actions?.edit && <a href={actions.edit}>edit</a>}
                {actions?.delete && <a href={actions.delete}>delete</a>}
              </td>
            )}
          </tr>
        ))}
      </tbody>
    </table>
  );
}
