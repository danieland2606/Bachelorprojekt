import {
  Address,
  GetCustomerList200ResponseInner,
  GetPolicyList200ResponseInner,
} from "$this/generated/index.ts";

export class CustomerShort {
  id: number;
  firstName: string;
  lastName: string;
  address: Address;

  constructor(customer: GetCustomerList200ResponseInner) {
    if (!CustomerShort.hasRequired(customer)) {
      throw new Error("Required fields of customer missing");
    }
    this.id = customer.id;
    this.firstName = customer.firstName!;
    this.lastName = customer.lastName!;
    this.address = customer.address!;
  }

  static hasRequired(policy: GetCustomerList200ResponseInner) {
    const { firstName, lastName, address } = policy;
    const { street, postalCode, city } = address ?? {};
    return firstName != null && lastName != null && address != null &&
      street != null && postalCode != null && city != null;
  }
}

export class PolicyShort {
  id: number;
  name: string;
  startDate: string;
  endDate: string;
  coverage: number;
  active: boolean;

  constructor(policy: GetPolicyList200ResponseInner) {
    if (!PolicyShort.hasRequired(policy)) {
      throw new Error("Required fields of policy missing");
    }
    this.id = policy.id;
    this.name = policy.objectOfInsurance?.name!;
    this.startDate = policy.startDate!;
    this.endDate = policy.endDate!;
    this.coverage = policy.coverage!;
    this.active = policy.active!;
  }

  static hasRequired(policy: GetPolicyList200ResponseInner) {
    const { startDate, endDate, coverage, active } = policy;
    const { name } = policy?.objectOfInsurance ?? {};
    return startDate != null && endDate != null &&
      coverage != null && name != null && active != null;
  }
}

export function processCustomerResponse(
  customer: GetCustomerList200ResponseInner[],
) {
  return customer.map((c) => new CustomerShort(c));
}

export function processPolicyResponse(policy: GetPolicyList200ResponseInner[]) {
  return policy.map((p) => new PolicyShort(p));
}
