import { HandlerContext, PageProps } from "$fresh/server.ts";
import { EditPolicy } from "../../../../components/EditPolicy.tsx";
import { policyClient } from "../../../../util/client.ts";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    const customerId = Number.parseInt(ctx.params.customerId);
    const policyId = Number.parseInt(ctx.params.policyId);
    const json = await policyClient.getPolicy(customerId, policyId);
    const data = JSON.parse(json);
    return await ctx.render(data);
  },
};

export default function ShowPolicy({ params, data }: PageProps) {
  return (
    <>
      <h1>Vertragsdetails</h1>
      <EditPolicy readonly values={data}></EditPolicy>
      <div class="box-row buttons">
        <a class="button" href={`/customer/${params.customerId}`}>Zurück</a>
      </div>
    </>
  );
}
