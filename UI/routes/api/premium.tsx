import { HandlerContext, PageProps } from "$fresh/server.ts";
import { deserializePolicyFull } from "$this/util/deserialize.ts";
import { policyClient } from "$this/util/client.ts";
import { PolicyCalc } from "$this/generated/index.ts";

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
  return { customerId: id, policy: deserializePolicyFull(form) } as PolicyCalc;
}
