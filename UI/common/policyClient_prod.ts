import { voidToEmpty } from "$this/common/util.ts";
import { processPolicyResponse } from "$this/common/types.ts";
import {
  createConfiguration,
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

export const policyClient = {
  getPolicyList: (customerId: number) =>
    policyApi.getPolicyList(customerId, policyFields)
      .then(voidToEmpty)
      .then(processPolicyResponse),
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
