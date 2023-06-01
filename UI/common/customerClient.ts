import { CustomerAllRequired, ID } from "$this/generated/index.ts";
import { ObjectSerializer } from "$this/generated/models/ObjectSerializer.ts";
import { processCustomerResponse } from "$this/common/types.ts";

export const customerClient = {
  getCustomerList: async () => {
    const json = JSON.parse(
      await Deno.readTextFile("./static/test/customers.json"),
    );
    const response = ObjectSerializer.deserialize(
      json,
      "Array<GetCustomerList200ResponseInner>",
      "",
    );
    return processCustomerResponse(response);
  },
  getCustomer: async (_: number) =>
    ObjectSerializer.deserialize(
      JSON.parse(await Deno.readTextFile("./static/test/customer.json")),
      "CustomerAllRequired",
      "",
    ) as CustomerAllRequired,
  createCustomer: async (customer: CustomerAllRequired) => {
    console.debug(JSON.stringify(customer));
    return ObjectSerializer.deserialize(
      await Promise.resolve(0),
      "ID",
      "",
    ) as ID;
  },
  updateCustomer: (_: number, customer: CustomerAllRequired) => {
    console.debug(JSON.stringify(customer));
    return Promise.resolve();
  },
};
