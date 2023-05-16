import {
  CalcPolicyPrice200Response,
  CustomerAllRequired,
  GetCustomerList200ResponseInner,
  GetPolicyList200ResponseInner,
  ID,
  PolicyAllRequired,
  PolicyCalc,
} from "$this/generated/index.ts";
import { ObjectSerializer } from "$this/generated/models/ObjectSerializer.ts";

export const customerClient = {
  getCustomerList: async () =>
    ObjectSerializer.deserialize(
      JSON.parse(await Deno.readTextFile("./static/test/customers.json")),
      "Array<GetCustomerList200ResponseInner>",
      "",
    ) as Array<GetCustomerList200ResponseInner>,
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
  getPolicyList: async (_: number) =>
    ObjectSerializer.deserialize(
      JSON.parse(await Deno.readTextFile("./static/test/policies.json")),
      "Array<GetPolicyList200ResponseInner>",
      "",
    ) as Array<GetPolicyList200ResponseInner>,
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
