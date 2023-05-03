import { HandlerContext, PageProps } from "$fresh/server.ts";
import { EditPolicy } from "../../../components/EditPolicy.tsx";
import { policyClient } from "../../../util/client.ts";
import {
  ObjectOfInsurance,
  Policy,
  PolicyAllRequired,
} from "../../../generated/index.ts";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    return await ctx.render();
  },
  async POST(req: Request, ctx: HandlerContext) {
    const customerId = Number.parseInt(ctx.params.customerId);
    const form = await req.formData();
    const policy = deserializePolicy(form);
    policyClient.createPolicy(customerId, policy);
    const base = new URL(req.url).origin;
    return Response.redirect(
      new URL(`/customer/${customerId}`, base),
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
        customerId={params.customerId}
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

export function deserializePolicy(form: FormData): PolicyAllRequired {
  const policy: Record<string, any> = {};
  const types = Policy.attributeTypeMap.filter(({ name }) =>
    name !== "id" && name !== "premium"
  );
  for (const prop of types) {
    if (form.has(prop.name)) {
      policy[prop.name] = form.get(prop.name);
    } else if (prop.type === "ObjectOfInsurance") {
      policy[prop.name] = {};
      for (const subProp of ObjectOfInsurance.attributeTypeMap) {
        const qualifiedName = `${prop.name}.${subProp.name}`;
        if (form.has(qualifiedName)) {
          policy[prop.name][subProp.name] = form.get(qualifiedName);
        }
      }
    }
  }
  return policy as PolicyAllRequired;
}
