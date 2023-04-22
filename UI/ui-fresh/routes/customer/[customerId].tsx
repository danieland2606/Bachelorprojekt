import { HandlerContext, PageProps } from "$fresh/server.ts";
import { EditCustomer } from "../../components/EditCustomer.tsx";
import { Table } from "../../components/Table.tsx";
import { TablePolicy } from "../../types.ts";
import { compareId } from "../../util.ts";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    const json = await Deno.readTextFile("./static/test/policies.json");
    const data = JSON.parse(json);
    const tableData = formatPolicyList(data, ctx.params.customerId);
    const jsoncust = await Deno.readTextFile("./static/test/customer.json");
    const datacust = JSON.parse(jsoncust);
    return ctx.render({ tableData: tableData, customer: datacust });
  },
  async POST(req: Request, _: HandlerContext) {
    const form = await req.formData();
    for (const [key, val] of form.entries()) {
      console.debug(`key: ${key}, val: ${val}`);
    }
    const base = new URL(req.url).origin;
    return Response.redirect(new URL("/", base), 303);
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
          <img src="search.svg" alt="lupe" />
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

function formatPolicyList(policyList: Array<TablePolicy>, customerId: string) {
  const id = Number.parseInt(customerId);
  return {
    headers: ["ID", "Katze", "Beginn", "Ende", "Jahresdeckung"],
    items: policyList.sort(compareId).map((policy) =>
      policyToTableRow(policy, id)
    ),
  };
}

function policyToTableRow(policy: TablePolicy, customerId: number) {
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
