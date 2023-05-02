import {
  CustomerAllRequired,
  PolicyAllRequired,
  PolicyCalc,
} from "../generated/index.ts";

export const customerClient = {
  getCustomerList: () => Deno.readTextFile("./static/test/customers.json"),
  getCustomer: (_: number) => Deno.readTextFile("./static/test/customer.json"),
  createCustomer: (customer: CustomerAllRequired) => {
    console.debug(JSON.stringify(customer));
  },
  updateCustomer: (_: number, customer: CustomerAllRequired) => {
    console.debug(JSON.stringify(customer));
  },
};

export const policyClient = {
  getPolicyList: (_: number) =>
    Deno.readTextFile("./static/test/policies.json"),
  getPolicy: (_1: number, _2: number) =>
    Deno.readTextFile("./static/test/policy.json"),
  createPolicy: (_: number, policy: PolicyAllRequired) => {
    console.debug(JSON.stringify(policy));
  },
  updatePolicy: (
    _1: number,
    _2: number,
    policy: PolicyAllRequired,
  ) => {
    console.debug(JSON.stringify(policy));
  },
  calcPolicyPrice: (calc: PolicyCalc) => {
    console.debug(JSON.stringify(calc));
  },
};
