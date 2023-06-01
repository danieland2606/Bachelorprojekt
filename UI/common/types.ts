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
  if (!customerHasRequired(policy)) {
    throw new Error("Required fields of customer missing");
  }
  const { id, firstName, lastName, address } = policy;
  return { id, firstName, lastName, address } as CustomerShort;
}

function customerHasRequired(policy: GetCustomerList200ResponseInner) {
  const { firstName, lastName, address } = policy;
  const { street, postalCode, city } = address ?? {};
  return firstName != null && lastName != null && address != null &&
    street != null && postalCode != null && city != null;
}

export function processPolicyResponse(policy: GetPolicyList200ResponseInner[]) {
  return policy.map(extractRequestedPolicy);
}

function extractRequestedPolicy(policy: GetPolicyList200ResponseInner) {
  if (!policyHasRequired(policy)) {
    throw new Error("Required fields of policy missing");
  }
  const { id, startDate, endDate, coverage, active } = policy;
  const { name } = policy.objectOfInsurance!;
  return { id, name, startDate, endDate, coverage, active } as PolicyShort;
}

function policyHasRequired(policy: GetPolicyList200ResponseInner) {
  const { startDate, endDate, coverage, active } = policy;
  const { name } = policy?.objectOfInsurance ?? {};
  return startDate != null && endDate != null &&
    coverage != null && name != null && active != null;
}
