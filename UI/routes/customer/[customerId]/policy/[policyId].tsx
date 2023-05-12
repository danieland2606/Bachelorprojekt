import { HandlerContext, PageProps } from "$fresh/server.ts";
import { EditPolicy } from "$this/components/EditPolicy.tsx";
import { policyClient } from "$this/util/client.ts";
import { deserialize } from "$this/util/util.ts";
import { PolicyAllRequired } from "$this/generated/index.ts";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    const customerId = Number.parseInt(ctx.params.customerId);
    const policyId = Number.parseInt(ctx.params.policyId);
    const data = await policyClient.getPolicy(customerId, policyId);
    return await ctx.render(data);
  },
  async POST(req: Request, ctx: HandlerContext) {
    const customerId = Number.parseInt(ctx.params.customerId);
    const policyId = Number.parseInt(ctx.params.policyId);
    const form = await req.formData();
    const policy = deserialize<PolicyAllRequired>(form, "PolicyAllRequired");
    await policyClient.updatePolicy(customerId, policyId, policy);
    const base = new URL(req.url).origin;
    return Response.redirect(
      new URL(`/customer/${customerId}`, base),
      303,
    );
  },
};

export default function ShowPolicy({ params, data }: PageProps) {
  return (
    <>
      <h1>Vertragsdetails</h1>
      <EditPolicy
        id="edit-policy"
        action={`/customer/${params.customerId}/policy/${params.policyId}`}
        method="post"
        values={data}
        customerId={params.customerId}
        allrequired
      >
      </EditPolicy>
      <div class="box-row buttons">
        <a class="button" href={`/customer/${params.customerId}`}>Zurück</a>
        <input
          form="edit-policy"
          type="submit"
          class="button"
          value="Änderungen bestätigen"
        >
        </input>
      </div>
    </>
  );
}
