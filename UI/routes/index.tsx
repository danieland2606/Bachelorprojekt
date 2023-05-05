import { HandlerContext, PageProps } from "$fresh/server.ts";
import { asset } from "$fresh/runtime.ts";
import { Item, Table, TableItems } from "../components/Table.tsx";
import { Address, Customer } from "../generated/models/all.ts";
import { compareId } from "../util/util.ts";
import { customerClient } from "../util/client.ts";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    const customers = await customerClient.getCustomerList();
    const tableData = formatCustomerList(customers);
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
          <img src={asset("/search.svg")} alt="lupe" />
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
  if (
    customer.id == null || customer.firstName == null ||
    customer.lastName == null || customer.address == null
  ) {
    throw new Error("Required fields of customer missing");
  }
  return {
    item: [
      customer.id,
      customer.firstName,
      customer.lastName,
      formatAddress(customer.address),
    ],
    actions: {
      details: `/customer/${customer.id}`,
      edit: "",
      delete: "",
    },
  };
}

function formatAddress(address: Address): string {
  return `${address.street}, ${address.postalCode} ${address.city}`;
}
