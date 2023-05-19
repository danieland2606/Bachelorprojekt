import { HandlerContext, PageProps } from "$fresh/server.ts";
import { EditPolicy } from "$this/components/EditPolicy.tsx";
import { policyClient } from "$this/util/client.ts";
import { origin } from "$this/util/util.ts";
import { deserializePolicyFull } from "$this/util/deserialize.ts";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    return await ctx.render();
  },
  async POST(req: Request, ctx: HandlerContext) {
    const customerId = Number.parseInt(ctx.params.customerId);
    const form = await req.formData();
    const policy = deserializePolicyFull(form);
    await policyClient.createPolicy(customerId, policy);
    const customerUrl = new URL(`/customer/${customerId}`, origin(req));
    return Response.redirect(customerUrl, 303);
  },
};

export default function CreatePolicy({ params }: PageProps) {
  const id = "new-policy";
  return (
    <>
      <h1>Neuen Vertrag anlegen</h1>
      <EditPolicy
        id={id}
        action={`/customer/${params.customerId}/policy`}
        method="post"
        customerId={params.customerId}
        allrequired
        mode="create"
      >
        <iframe
          name="premium-calc"
          style="border-style:none;width:6em;height:6em"
        >
        </iframe>
        <input
          type="submit"
          form={id}
          class="button"
          formTarget="premium-calc"
          formAction="/api/premium"
          value="Rate berechnen"
        >
        </input>
      </EditPolicy>
      <div class="box-row buttons">
        <a class="button" href={`/customer/${params.customerId}`}>Abbrechen</a>
        <input
          form={id}
          type="submit"
          class="button"
          value="Vertrag abschließen"
        >
        </input>
      </div>
    </>
  );
}
