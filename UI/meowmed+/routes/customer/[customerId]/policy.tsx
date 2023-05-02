import { HandlerContext, PageProps } from "$fresh/server.ts";
import { EditPolicy } from "../../../components/EditPolicy.tsx";
import { policyClient } from "../../../util/client.ts";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    return await ctx.render();
  },
  async POST(req: Request, ctx: HandlerContext) {
    const form = await req.formData();
    for (const [key, val] of form.entries()) {
      console.debug(`key: ${key}, val: ${val}`);
    }
    const base = new URL(req.url).origin;
    return Response.redirect(
      new URL(`/customer/${ctx.params.customerId}`, base),
      303,
    );
  },
};

export default function CreatePolicy({ params }: PageProps) {
  return (
    <>
      <h1>Neuen Vertrag anlegen</h1>
      <EditPolicy
        id="new-policy"
        action={`/customer/${params.customerId}/policy`}
        method="post"
      >
        <iframe
          name="premium-calc"
          style="border-style:none;width:6em;height:6em"
        >
        </iframe>
        <input
          type="submit"
          form="new-policy"
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
          form="new-policy"
          type="submit"
          class="button"
          value="Vertrag abschließen"
        >
        </input>
      </div>
    </>
  );
}
