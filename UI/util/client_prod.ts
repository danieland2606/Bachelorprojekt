import {
  createConfiguration,
  CustomerAllRequired,
  CustomerApi,
  CustomerPropertyNames,
  PolicyAllRequired,
  PolicyApi,
  PolicyCalc,
  PolicyPropertyNames,
  ServerConfiguration,
} from "$this/generated/index.ts";

const customerServer = new ServerConfiguration("${customerService}", {});
const customerApi = new CustomerApi(
  createConfiguration({ baseServer: customerServer }),
);
const customerFields = new Set<CustomerPropertyNames>([
  "firstName",
  "lastName",
  "address",
]);

export const customerClient = {
  getCustomerList: () =>
    customerApi.getCustomerList(customerFields).then(voidToEmpty),
  getCustomer: (id: number) => customerApi.getCustomer(id),
  createCustomer: (customer: CustomerAllRequired) =>
    customerApi.createCustomer(customer),
  updateCustomer: (customerId: number, customer: CustomerAllRequired) =>
    customerApi.updateCustomer(customerId, customer),
};

const policyServer = new ServerConfiguration("${policyService}", {});
const policyApi = new PolicyApi(
  createConfiguration({ baseServer: policyServer }),
);
const policyFields = new Set<PolicyPropertyNames>([
  "objectOfInsurance.name",
  "startDate",
  "endDate",
  "coverage",
]);

export const policyClient = {
  getPolicyList: (
    customerId: number,
  ) => policyApi.getPolicyList(customerId, policyFields).then(voidToEmpty),
  getPolicy: (customerId: number, policyId: number) =>
    policyApi.getPolicy(customerId, policyId),
  createPolicy: (customerId: number, policy: PolicyAllRequired) =>
    policyApi.createPolicy(customerId, policy),
  updatePolicy: (
    customerId: number,
    policyId: number,
    policy: PolicyAllRequired,
  ) => policyApi.updatePolicy(customerId, policyId, policy),
  calcPolicyPrice: (calc: PolicyCalc) => policyApi.calcPolicyPrice(calc),
};

function voidToEmpty<T>(arg: T[] | void) { //typescript sucks
  if (typeof arg === "undefined") {
    return [] as T[];
  }
  return arg;
}
