import { HandlerContext, PageProps } from "$fresh/server.ts";
import { deserialize } from "$this/util/util.ts";
import { policyClient } from "$this/util/client.ts";
import { PolicyAllRequired, PolicyCalc } from "$this/generated/index.ts";

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
  const id = (form.get("customerId") ?? NaN) as number;
  if (isNaN(id)) { //intentionally not using Number.isNaN()
    throw new Error("customerId missing for premium calculation");
  }
  const policyCalc = {
    customerId: id,
    policy: deserialize<PolicyAllRequired>(form, "PolicyAllRequired"),
  };
  return policyCalc as PolicyCalc;
}
