import {
  processCustomerResponse,
  processPolicyResponse,
} from "$this/common/types.ts";
import {
  Configuration,
  createConfiguration,
  CustomerAllRequired,
  CustomerApi,
  CustomerPropertyNames,
  InvoiceApi,
  PolicyAllRequired,
  PolicyApi,
  PolicyCalc,
  PolicyPropertyNames,
  ServerConfiguration,
} from "$this/generated/index.ts";

export class CustomerClient {
  static readonly required = customerRequired();
  #api = configApi(CustomerApi, "MEOWMED_CUSTOMER_SERVER");

  getCustomerList() {
    this.#api.getCustomerList(CustomerClient.required)
      .then(voidToEmpty)
      .then(processCustomerResponse);
  }

  getCustomer(id: number) {
    this.#api.getCustomer(id);
  }

  createCustomer(customer: CustomerAllRequired) {
    this.#api.createCustomer(customer);
  }

  updateCustomer(customerId: number, customer: CustomerAllRequired) {
    this.#api.updateCustomer(customerId, customer);
  }
}

export class PolicyClient {
  static readonly required = policyRequired();
  #api = configApi(PolicyApi, "MEOWMED_POLICY_SERVER");

  getPolicyList(customerId: number) {
    this.#api.getPolicyList(customerId, PolicyClient.required)
      .then(voidToEmpty)
      .then(processPolicyResponse);
  }

  getPolicy(customerId: number, policyId: number) {
    this.#api.getPolicy(customerId, policyId);
  }

  createPolicy(customerId: number, policy: PolicyAllRequired) {
    this.#api.createPolicy(customerId, policy);
  }

  updatePolicy(
    customerId: number,
    policyId: number,
    policy: PolicyAllRequired,
  ) {
    this.#api.updatePolicy(customerId, policyId, policy);
  }

  calcPolicyPrice(calc: PolicyCalc) {
    this.#api.calcPolicyPrice(calc);
  }
}

export class InvoiceClient {
  #api = configApi(InvoiceApi, "MEOWMED_INVOICE_SERVER");

  getInvoiceList(customerId: number, policyId: number) {
    return this.#api.getInvoiceList(customerId, policyId);
  }
}

export const customerClient = new CustomerClient();
export const policyClient = new PolicyClient();
export const invoiceClient = new InvoiceClient();

type API<T> = new (conf: Configuration) => T;

export function configApi<T>(API: API<T>, server: string) {
  const serverAddress = Deno.env.get(server);
  if (serverAddress == null) {
    throw new Error(`${server} URL not set`);
  }
  const baseServer = new ServerConfiguration(serverAddress, {});
  return new API(createConfiguration({ baseServer }));
}

function customerRequired() {
  return new Set<CustomerPropertyNames>(["firstName", "lastName", "address"]);
}

function policyRequired() {
  return new Set<PolicyPropertyNames>([
    "objectOfInsurance.name",
    "startDate",
    "endDate",
    "coverage",
    "active",
  ]);
}

function voidToEmpty<T>(arg: T[] | void) { //typescript sucks
  if (typeof arg === "undefined") {
    return [] as T[];
  }
  return arg;
}
