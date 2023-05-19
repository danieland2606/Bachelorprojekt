import { voidToEmpty } from "$this/common/util.ts";
import {
  Address,
  createConfiguration,
  CustomerAllRequired,
  CustomerApi,
  CustomerPropertyNames,
  GetCustomerList200ResponseInner,
  ServerConfiguration,
} from "$this/generated/index.ts";

const serverAddress = Deno.env.get("MEOWMED_CUSTOMER_SERVER");
if (serverAddress == null) {
  throw new Error("CustomerService Server URL not set");
}
const baseServer = new ServerConfiguration(serverAddress, {});
const customerApi = new CustomerApi(createConfiguration({ baseServer }));
const customerFields = new Set<CustomerPropertyNames>([
  "firstName",
  "lastName",
  "address",
]);

export interface CustomerShort {
  id: number;
  firstName: string;
  lastName: string;
  address: Address;
}

export const customerClient = {
  getCustomerList: () =>
    customerApi.getCustomerList(customerFields)
      .then(voidToEmpty)
      .then(processResponse),
  getCustomer: (id: number) => customerApi.getCustomer(id),
  createCustomer: (customer: CustomerAllRequired) =>
    customerApi.createCustomer(customer),
  updateCustomer: (customerId: number, customer: CustomerAllRequired) =>
    customerApi.updateCustomer(customerId, customer),
};

export function processResponse(policy: GetCustomerList200ResponseInner[]) {
  return policy.map(extractRequested);
}

function extractRequested(policy: GetCustomerList200ResponseInner) {
  const { id, firstName, lastName, address } = policy;
  const { street, postalCode, city } = address ?? {};
  if (
    firstName == null || lastName == null || address == null ||
    street == null || postalCode == null || city == null
  ) {
    throw new Error("Required fields of customer missing");
  }
  return { id, firstName, lastName, address } as CustomerShort;
}
