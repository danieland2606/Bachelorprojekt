import { HandlerContext, PageProps } from "$fresh/server.ts";
import { itemSearch, Table } from "$this/components/Table.tsx";
import { SearchBar } from "$this/components/SearchBar.tsx";
import { Address } from "$this/generated/models/all.ts";
import { compareId } from "$this/common/util.ts";
import { CustomerShort } from "$this/common/types.ts";
import { customerClient } from "$this/common/client.ts";

export const handler = {
  async GET(req: Request, ctx: HandlerContext) {
    const search = new URL(req.url).searchParams.get("search") ?? "";
    const customers = await customerClient.getCustomerList();
    const [tableData, altData] = formatCustomerList(customers, search);
    return ctx.render({ tableData, altData, search });
  },
};

export default function Dashboard({ data }: PageProps) {
  const { tableData, altData, search } = data;
  return (
    <>
      <h1>Übersicht Kunden</h1>
      <SearchBar value={search}>
        <a href="/customer" class="btn px-10">
          Neuer Kunde
        </a>
      </SearchBar>
      <Table tabledata={tableData} class="max-md:hidden" />
      <Table tabledata={altData} class="md:hidden" />
    </>
  );
}

function formatCustomerList(customerList: CustomerShort[], search: string) {
  const headers = ["ID", "Vorname", "Nachname", "Adresse"];
  const altHeaders = ["Name", "Stadt"];
  const displayed = customerList.filter(itemSearch(search)).sort(compareId);
  const items = displayed.map(customerToTableItem);
  const altItems = displayed.map(customerToAltItem);
  return [{ headers, items }, { headers: altHeaders, items: altItems }];
}

function customerToTableItem(customer: CustomerShort) {
  const { id, firstName, lastName, address } = customer;
  const row = [id, firstName, lastName, formatAddress(address)];
  const actions = formatActions(id);
  return { row, actions, active: true };
}

function customerToAltItem(customer: CustomerShort) {
  const { id, firstName, lastName, address } = customer;
  const row = [`${firstName} ${lastName}`, address.city ?? ""];
  const actions = formatActions(id);
  return { row, actions, active: true };
}

function formatActions(id: number) {
  return { details: `/customer/${id}`, edit: `/customer/${id}?edit` };
}

function formatAddress({ street, postalCode, city }: Address): string {
  return `${street}, ${postalCode} ${city}`;
}
