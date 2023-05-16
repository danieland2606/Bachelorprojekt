import { HandlerContext, PageProps } from "$fresh/server.ts";
import { EditPolicy } from "$this/components/EditPolicy.tsx";
import { policyClient } from "$this/util/client.ts";
import { deserialize } from "$this/util/util.ts";
import { PolicyAllRequired } from "$this/generated/index.ts";

export const handler = {
  async GET(req: Request, ctx: HandlerContext) {
    const customerId = Number.parseInt(ctx.params.customerId);
    const policyId = Number.parseInt(ctx.params.policyId);
    const edit = new URL(req.url).searchParams.get("edit") != null;
    const data = await policyClient.getPolicy(customerId, policyId);
    return await ctx.render({ policy: data, edit });
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
  const id = "edit-policy";
  return (
    <>
      <h1>Vertragsdetails</h1>
      <EditPolicy
        id={id}
        values={data.policy}
        customerId={params.customerId}
        allrequired
        mode={data.edit ? "edit" : "display"}
      >
      </EditPolicy>
      <div class="box-row buttons">
        <a class="button" href={`/customer/${params.customerId}`}>Zurück</a>
        {data.edit &&
          (
            <input
              form={id}
              type="submit"
              formAction={`/customer/${params.customerId}/policy/${params.policyId}`}
              formMethod="post"
              class="button"
              value="Änderungen bestätigen"
            >
            </input>
          )}
      </div>
    </>
  );
}
