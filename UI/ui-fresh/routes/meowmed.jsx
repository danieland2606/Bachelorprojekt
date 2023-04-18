import { Table } from "../components/Table.jsx";
import { formatCustomerList } from "../util.js";

export const handler = {
  async GET(_, ctx) {
    const json = await Deno.readTextFile("./static/customers.json");
    const data = JSON.parse(json).customerList;
    const tableData = formatCustomerList(data);
    console.log(JSON.stringify(tableData));
    return ctx.render(tableData);
  },
};

export default function Dashboard({ data }) {
  return (
    <>
      <h1>Mitarbeiter Dashboard</h1>
      <input type="text" placeholder="Suche.." hidden />
      <button type="button">Neuer Kunde</button>
      <Table tabledata={data}></Table>
    </>
  );
}
