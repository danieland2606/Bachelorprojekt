import { HandlerContext, PageProps } from "$fresh/server.ts";
import { Item, Table, TableItems } from "../components/Table.tsx";
import { Address, Customer } from "../types.ts";
import { compareId } from "../util.ts";

const fields = ["firstName", "lastName", "address"];

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    const json = await Deno.readTextFile("./static/test/customers.json");
    const data = JSON.parse(json).customerList;
    const tableData = formatCustomerList(data);
    return ctx.render(tableData);
  },
};

export default function Dashboard({ data }: PageProps) {
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

function formatCustomerList(customerList: Array<Customer>): TableItems {
  return {
    headers: ["ID", "Vorname", "Nachname", "Adresse"],
    items: customerList.sort(compareId).map(customerToTableRow),
  };
}

function customerToTableRow(customer: Customer): Item {
  return {
    item: [
      customer.id ?? "",
      customer.firstName ?? "",
      customer.lastName ?? "",
      formatAddress(customer.address),
    ],
    actions: {
      details: `/customer/${customer.id}`,
      edit: "",
      delete: "",
    },
  };
}

function formatAddress(address: Address | undefined): string {
  return `${address?.street}, ${address?.postalCode} ${address?.city}`;
}
