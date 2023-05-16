import { HandlerContext, PageProps } from "$fresh/server.ts";
import { Item, Table, TableItems } from "$this/components/Table.tsx";
import { Search } from "$this/components/Search.tsx";
import { Address, Customer } from "$this/generated/models/all.ts";
import { compareId } from "$this/util/util.ts";
import { customerClient } from "$this/util/client.ts";

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
      <h1 class="text-4xl font-medium">Dashboard</h1>
      <div class="sm:flex py-5 justify-between block">
        <Search
          value={data.search}
          class="relative sm:inline-block block mb-4 sm:mb-0"
        >
        </Search>
        <a class="btn btn-normal flex sm:inline-flex" href="/customer">
          Neuer Kunde
        </a>
      </div>
      <Table tabledata={data.tableData}></Table>
    </>
  );
}

function formatCustomerList(customerList: Customer[], search: string) {
  return {
    headers: ["ID", "Vorname", "Nachname", "Adresse"],
    items: customerList
      .filter(customerSearch(search))
      .sort(compareId)
      .map(customerToTableRow),
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
      edit: `/customer/${customer.id}?edit`,
      delete: "",
    },
  };
}

function formatAddress(address: Address): string {
  return `${address.street}, ${address.postalCode} ${address.city}`;
}

function customerSearch(search: string) {
  if (!search) {
    return function () {
      return true;
    };
  }
  return (customer: Customer) => {
    const searchTarget =
      `${customer.id} ${customer.firstName} ${customer.lastName} ${
        customer?.address && formatAddress(customer.address)
      }`;
    return searchTarget.toLowerCase().includes(search.toLowerCase());
  };
}
