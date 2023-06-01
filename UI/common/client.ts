import { ObjectSerializer } from "$this/generated/models/ObjectSerializer.ts";
import {
  processCustomerResponse,
  processPolicyResponse,
} from "$this/common/types.ts";
import {
  CalcPolicyPrice200Response,
  CustomerAllRequired,
  ID,
  Invoice,
  PolicyAllRequired,
  PolicyCalc,
} from "$this/generated/index.ts";

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

export const policyClient = {
  getPolicyList: async (_: number) => {
    const json = JSON.parse(
      await Deno.readTextFile("./static/test/policies.json"),
    );
    const response = ObjectSerializer.deserialize(
      json,
      "Array<GetPolicyList200ResponseInner>",
      "",
    );
    return processPolicyResponse(response);
  },
  getPolicy: async (_1: number, _2: number) =>
    ObjectSerializer.deserialize(
      JSON.parse(await Deno.readTextFile("./static/test/policy.json")),
      "PolicyAllRequired",
      "",
    ) as PolicyAllRequired,
  createPolicy: async (_: number, policy: PolicyAllRequired) => {
    console.debug(JSON.stringify(policy));
    return ObjectSerializer.deserialize(
      await Promise.resolve(0),
      "ID",
      "",
    ) as ID;
  },
  updatePolicy: (
    _1: number,
    _2: number,
    policy: PolicyAllRequired,
  ) => {
    console.debug(JSON.stringify(policy));
    return Promise.resolve();
  },
  calcPolicyPrice: async (calc: PolicyCalc) => {
    console.debug(JSON.stringify(calc));
    return ObjectSerializer.deserialize(
      await Promise.resolve({ premium: 45 }),
      "CalcPolicyPrice200Response",
      "",
    ) as CalcPolicyPrice200Response;
  },
};

export const invoiceClient = {
  async getInvoiceList(_1: number, _2: number) {
    const json = await Deno.readTextFile("./static/test/invoices.json");
    return ObjectSerializer.deserialize(
      JSON.parse(json),
      "Array<Invoice>",
      "",
    ) as Invoice[];
  },
};
