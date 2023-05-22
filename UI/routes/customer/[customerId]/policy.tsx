import { HandlerContext, PageProps } from "$fresh/server.ts";
import { policyClient } from "$this/common/policyClient.ts";
import { EditPolicy } from "$this/components/EditPolicy.tsx";
import PremiumCalculator from "$this/islands/PremiumCalculator.tsx";
import { redirect } from "$this/common/util.ts";
import { deserializePolicyFull } from "$this/common/deserialize.ts";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    return await ctx.render();
  },
  async POST(req: Request, ctx: HandlerContext) {
    const customerId = Number.parseInt(ctx.params.customerId);
    const form = await req.formData();
    const policy = deserializePolicyFull(form);
    await policyClient.createPolicy(customerId, policy);
    return redirect(`/customer/${customerId}`);
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
        <PremiumCalculator form={id} api="/api/premium" className="my-5" />
      </EditPolicy>
      <div class="sm:flex py-5 justify-between block">
        <a
          class="btn btn-normal flex sm:inline-flex mb-4 sm:mb-0"
          href={`/customer/${params.customerId}`}
        >
          Abbrechen
        </a>
        <input
          form={id}
          type="submit"
          class="btn btn-normal flex w-full sm:w-auto"
          value="Vertrag abschließen"
        >
        </input>
      </div>
    </>
  );
}
