import { voidToEmpty } from "$this/common/util.ts";
import { processCustomerResponse } from "$this/common/types.ts";
import {
  createConfiguration,
  CustomerAllRequired,
  CustomerApi,
  CustomerPropertyNames,
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

export const customerClient = {
  getCustomerList: () =>
    customerApi.getCustomerList(customerFields)
      .then(voidToEmpty)
      .then(processCustomerResponse),
  getCustomer: (id: number) => customerApi.getCustomer(id),
  createCustomer: (customer: CustomerAllRequired) =>
    customerApi.createCustomer(customer),
  updateCustomer: (customerId: number, customer: CustomerAllRequired) =>
    customerApi.updateCustomer(customerId, customer),
};
