import { HandlerContext, PageProps } from "$fresh/server.ts";
import { itemSearch, Table } from "$this/components/Table.tsx";
import { Search } from "$this/components/Search.tsx";
import { Address } from "$this/generated/models/all.ts";
import { compareId } from "$this/common/util.ts";
import { CustomerShort } from "$this/common/types.ts";
import { customerClient } from "$this/common/customerClient.ts";

export const handler = {
  async GET(req: Request, ctx: HandlerContext) {
    const search = new URL(req.url).searchParams.get("search") ?? "";
    const customers = await customerClient.getCustomerList();
    const tableData = formatCustomerList(customers, search);
    return ctx.render({ tableData, search });
  },
};

export default function Dashboard({ data }: PageProps) {
  return (
    <>
      <h1>Übersicht Kunden</h1>
      <div class="py-2 md:py-4 flex flex-col-reverse md:flex-row md:justify-between">
        <Search
          value={data.search}
          class="my-2 md:my-0 flex-1 md:flex-none md:w-fit"
        />
        <a
          class="btn btn-normal flex-1 md:flex-none md:w-fit"
          href="/customer"
        >
          Neuer Kunde
        </a>
      </div>
      <Table tabledata={data.tableData} />
    </>
  );
}

function formatCustomerList(customerList: CustomerShort[], search: string) {
  const headers = ["ID", "Vorname", "Nachname", "Adresse"];
  const items = customerList
    .sort(compareId)
    .map(customerToTableItem)
    .filter(itemSearch(search));
  return { headers, items };
}

function customerToTableItem(customer: CustomerShort) {
  const { id, firstName, lastName, address } = customer;
  const row = [id, firstName, lastName, formatAddress(address)];
  const actions = { details: `/customer/${id}`, edit: `/customer/${id}?edit` };
  return { row, actions, active: true };
}

function formatAddress({ street, postalCode, city }: Address): string {
  return `${street}, ${postalCode} ${city}`;
}
