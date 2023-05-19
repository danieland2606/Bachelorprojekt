import { voidToEmpty } from "$this/common/util.ts";
import {
  createConfiguration,
  GetPolicyList200ResponseInner,
  PolicyAllRequired,
  PolicyApi,
  PolicyCalc,
  PolicyPropertyNames,
  ServerConfiguration,
} from "$this/generated/index.ts";

const serverAddress = Deno.env.get("MEOWMED_POLICY_SERVER");
if (serverAddress == null) {
  throw new Error("PolicyService Server URL not set");
}
const baseServer = new ServerConfiguration(serverAddress, {});
const policyApi = new PolicyApi(createConfiguration({ baseServer }));
const policyFields = new Set<PolicyPropertyNames>([
  "objectOfInsurance.name",
  "startDate",
  "endDate",
  "coverage",
  "active",
]);

export interface PolicyShort {
  id: number;
  name: string;
  startDate: string;
  endDate: string;
  coverage: number;
  active: boolean;
}

export const policyClient = {
  getPolicyList: (customerId: number) =>
    policyApi.getPolicyList(customerId, policyFields)
      .then(voidToEmpty)
      .then(processResponse),
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

export function processResponse(policy: GetPolicyList200ResponseInner[]) {
  return policy.map(extractRequested);
}

function extractRequested(policy: GetPolicyList200ResponseInner) {
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
