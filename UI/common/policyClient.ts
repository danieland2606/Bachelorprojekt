import {
  CalcPolicyPrice200Response,
  ID,
  PolicyAllRequired,
  PolicyCalc,
} from "$this/generated/index.ts";
import { ObjectSerializer } from "$this/generated/models/ObjectSerializer.ts";
import { processResponse } from "$this/common/policyClient_prod.ts";
export type { PolicyShort } from "$this/common/policyClient_prod.ts";

export const policyClient = {
  getPolicyList: async (_: number) => {
    const json = JSON.parse(
      await Deno.readTextFile("./static/test/policies.json"),
    );
    const response = ObjectSerializer.deserialize(
      json,
      "Array<GetPolicyList200ResponseInner>",
      "",
    );
    return processResponse(response);
  },
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
