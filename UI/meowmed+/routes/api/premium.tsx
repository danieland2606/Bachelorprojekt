import { HandlerContext, PageProps } from "$fresh/server.ts";
import { deserializePolicy } from "../customer/[customerId]/policy.tsx";
import { policyClient } from "../../util/client.ts";
import { PolicyCalc } from "../../generated/index.ts";

export const handler = {
  GET(_: Request, ctx: HandlerContext) {
    return ctx.renderNotFound();
  },
  async POST(req: Request, ctx: HandlerContext) {
    const form = await req.formData();
    const calcPolicy = deserializePolicyCalc(form);
    const { premium } = await policyClient.calcPolicyPrice(calcPolicy);
    return ctx.render({ premium });
  },
};

export default function PremiumDisplay({ data }: PageProps) {
  return (
    <>
      <p>{data.premium}</p>
    </>
  );
}

function deserializePolicyCalc(form: FormData) {
  const policyCalc: Record<string, any> = {
    customerId: form.get("customerId"),
    policy: deserializePolicy(form),
  };
  return policyCalc as PolicyCalc;
}
