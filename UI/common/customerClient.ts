import { CustomerAllRequired, ID } from "$this/generated/index.ts";
import { ObjectSerializer } from "$this/generated/models/ObjectSerializer.ts";
import { processResponse } from "$this/common/customerClient_prod.ts";
export type { CustomerShort } from "$this/common/customerClient_prod.ts";

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
    return processResponse(response);
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
