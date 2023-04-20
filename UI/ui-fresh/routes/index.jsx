import { Table } from "../components/Table.tsx";
import { formatCustomerList } from "../util.ts";

const fields = ["firstName", "lastName", "address"];

export const handler = {
  async GET(_, ctx) {
    const json = await Deno.readTextFile("./static/test/customers.json");
    const data = JSON.parse(json).customerList;
    const tableData = formatCustomerList(data);
    return ctx.render(tableData);
  },
};

export default function Dashboard({ data }) {
  return (
    <>
      <h1>Mitarbeiter Dashboard</h1>
      <div class="box-row">
        <div class="search-container">
          <input type="text" placeholder="Suche.." />
          <img src="search.svg" alt="lupe" />
        </div>
        <a class="button create-new" href="/customer">Neuer Kunde</a>
      </div>
      <Table tabledata={data}></Table>
    </>
  );
}
