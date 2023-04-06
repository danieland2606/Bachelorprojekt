export const handler = {
  async GET(_, ctx) {
    const json = await Deno.readTextFile("./static/customers.json");
    const data = JSON.parse(json).customerList;
    return ctx.render(data);
  },
};

export default function Dashboard({data}) {
  return (
    <>
      <h1>Mitarbeiter Dashboard</h1>
      <input type="text" placeholder="Suche.." hidden />
      <button type="button">Neuer Kunde</button>
      <table>
        <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Vorname</th>
            <th scope="col">Nachname</th>
            <th scope="col">Adresse</th>
            <th scope="col">Aktionen</th>
          </tr>
        </thead>
        <tbody>
          {data.map((entry) => (
            <tr>
              <td>{entry.id}</td>
              <td>{entry.firstName}</td>
              <td>{entry.lastName}</td>
              <td>
                {entry?.address?.street}, {entry?.address?.postalCode}{" "}
                {entry?.address?.city}
              </td>
              <td>
                <button type="button">details</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}
