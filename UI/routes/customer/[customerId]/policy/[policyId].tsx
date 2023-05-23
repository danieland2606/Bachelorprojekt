import { HandlerContext, PageProps } from "$fresh/server.ts";
import { EditPolicy } from "$this/components/EditPolicy.tsx";
import { policyClient } from "$this/common/policyClient.ts";
import { deserializePolicyFull } from "$this/common/deserialize.ts";
import { redirect } from "$this/common/util.ts";

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
    const policy = deserializePolicyFull(form);
    await policyClient.updatePolicy(customerId, policyId, policy);
    return redirect(`/customer/${customerId}`);
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
      <div class="sm:flex py-5 justify-between block">
        <a
          class="btn btn-normal inline-flex mb-4 sm:mb-0"
          href={`/customer/${params.customerId}`}
        >
          Zurück
        </a>
        {data.edit &&
          (
            <input
              form={id}
              type="submit"
              formAction={`/customer/${params.customerId}/policy/${params.policyId}`}
              formMethod="post"
              class="btn btn-normal flex sm:inline-flex"
              value="Änderungen bestätigen"
            />
          )}
      </div>
    </>
  );
}
