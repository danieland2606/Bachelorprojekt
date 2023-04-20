import { Item, TableItems } from "./components/Table.tsx";
import { Address, Customer } from "./types.ts";

export function capitalize(str: string) {
  return str.charAt(0).toUpperCase() + str.slice(1);
}

export function formatAddress(address: Address | undefined): string {
  return `${address?.street}, ${address?.postalCode} ${address?.city}`;
}

function compareId({ id: id1 = 0 }, { id: id2 = 0 }) {
  return id1 - id2;
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

export function formatCustomerList(customerList: Array<Customer>): TableItems {
  return {
    headers: ["ID", "Vorname", "Nachname", "Adresse"],
    items: customerList.sort(compareId).map(customerToTableRow),
  };
}
