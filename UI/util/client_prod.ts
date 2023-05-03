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
} from "../generated/index.ts";

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
  getCustomerList: () => customerApi.getCustomerList(customerFields),
  getCustomer: (id: number) => customerApi.getCustomer(id),
  createCustomer: (customer: CustomerAllRequired) =>
    customerApi.createCustomer(customer),
  updateCustomer: (id: number, customer: CustomerAllRequired) =>
    customerApi.updateCustomer(id, customer),
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
  getPolicyList: (customerId: number) =>
    policyApi.getPolicyList(customerId, policyFields),
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
