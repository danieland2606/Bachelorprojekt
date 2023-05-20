import {
  Address,
  GetCustomerList200ResponseInner,
  GetPolicyList200ResponseInner,
} from "$this/generated/index.ts";

export interface CustomerShort {
  id: number;
  firstName: string;
  lastName: string;
  address: Address;
}

export interface PolicyShort {
  id: number;
  name: string;
  startDate: string;
  endDate: string;
  coverage: number;
  active: boolean;
}

export function processCustomerResponse(
  policy: GetCustomerList200ResponseInner[],
) {
  return policy.map(extractRequestedCustomer);
}

function extractRequestedCustomer(policy: GetCustomerList200ResponseInner) {
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

export function processPolicyResponse(policy: GetPolicyList200ResponseInner[]) {
  return policy.map(extractRequestedPolicy);
}

function extractRequestedPolicy(policy: GetPolicyList200ResponseInner) {
  const { name } = policy?.objectOfInsurance ?? {};
  const { id, startDate, endDate, coverage, active } = policy;
  if (
    startDate == null || endDate == null ||
    coverage == null || name == null || active == null
  ) {
    throw new Error("Required fields of policy missing");
  }
  return { id, name, startDate, endDate, coverage, active } as PolicyShort;
}
