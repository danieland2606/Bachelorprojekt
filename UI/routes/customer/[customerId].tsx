import { HandlerContext, PageProps } from "$fresh/server.ts";
import { asset } from "$fresh/runtime.ts";
import { EditCustomer } from "../../components/EditCustomer.tsx";
import { Table } from "../../components/Table.tsx";
import { Policy } from "../../generated/models/all.ts";
import { compareId } from "../../util/util.ts";
import { customerClient, policyClient } from "../../util/client.ts";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    const customerId = Number.parseInt(ctx.params.customerId);
    const policyList = await policyClient.getPolicyList(customerId);
    const tableData = formatPolicyList(policyList, ctx.params.customerId);
    const customer = await customerClient.getCustomer(customerId);
    return ctx.render({ tableData, customer });
  },
};

export default function ShowCustomer({ data, params }: PageProps) {
  return (
    <>
      <h1>Kundendetails</h1>
      <EditCustomer readonly values={data.customer}>
      </EditCustomer>
      <div class="box-row">
        <div class="search-container">
          <input type="text" placeholder="Suche.." />
          <img src={asset("/search.svg")} alt="lupe" />
        </div>
        <a
          class="button create-new"
          href={`/customer/${params.customerId}/policy`}
        >
          Neuer Vertrag
        </a>
      </div>
      <Table tabledata={data.tableData}></Table>
      <div class="box-row buttons">
        <a class="button" href="/">Zurück</a>
      </div>
    </>
  );
}

function formatPolicyList(policyList: Array<Policy>, customerId: string) {
  const id = Number.parseInt(customerId);
  return {
    headers: ["ID", "Katze", "Beginn", "Ende", "Jahresdeckung"],
    items: policyList.sort(compareId).map((policy) =>
      policyToTableRow(policy, id)
    ),
  };
}

function policyToTableRow(policy: Policy, customerId: number) {
  if (
    policy.id == null || policy.objectOfInsurance == null ||
    policy.startDate == null || policy.endDate == null ||
    policy.coverage == null
  ) {
    throw new Error("Required fields of policy missing");
  }
  return {
    item: [
      policy.id,
      policy.objectOfInsurance.name,
      policy.startDate,
      policy.endDate,
      policy.coverage,
    ],
    actions: {
      details: `/customer/${customerId}/policy/${policy.id}`,
      edit: "",
      delete: "",
    },
  };
}
