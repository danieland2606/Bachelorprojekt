import { HandlerContext, PageProps } from "$fresh/server.ts";
import { EditCustomer } from "$this/components/EditCustomer.tsx";
import { itemSearch, Table } from "$this/components/Table.tsx";
import { Search } from "$this/components/Search.tsx";
import { compareId, origin } from "$this/common/util.ts";
import { deserializeCustomerFull } from "$this/common/deserialize.ts";
import { customerClient } from "$this/common/customerClient.ts";
import { policyClient, PolicyShort } from "$this/common/policyClient.ts";

export const handler = {
  async GET(req: Request, ctx: HandlerContext) {
    const queryParams = new URL(req.url).searchParams;
    const edit = queryParams.get("edit") != null;
    const search = queryParams.get("search") ?? "";
    const customerId = Number.parseInt(ctx.params.customerId);
    const policyList = await policyClient.getPolicyList(customerId);
    const tableData = formatPolicyList(policyList, customerId, search);
    const customer = await customerClient.getCustomer(customerId);
    return ctx.render({ tableData, customer, edit });
  },
  async POST(req: Request, ctx: HandlerContext) {
    const customerId = Number.parseInt(ctx.params.customerId);
    const form = await req.formData();
    const customer = deserializeCustomerFull(form);
    await customerClient.updateCustomer(customerId, customer);
    const index = new URL("/", origin(req));
    return Response.redirect(index, 303);
  },
};

export default function ShowCustomer({ data, params }: PageProps) {
  const id = "edit-customer";
  return (
    <>
      <h1>Kundendetails</h1>
      <EditCustomer
        id={id}
        mode={data.edit ? "edit" : "display"}
        values={data.customer}
        allrequired
      >
      </EditCustomer>
      <div class="box-row">
        <Search
          value={data.search}
          class="relative sm:inline-block block mb-4 sm:mb-0"
        >
        </Search>
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
        {data.edit &&
          (
            <input
              form={id}
              type="submit"
              formAction={`/customer/${params.customerId}`}
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

function formatPolicyList(
  policyList: PolicyShort[],
  customerId: number,
  search = "",
) {
  const headers = ["ID", "Katze", "Beginn", "Ende", "Jahresdeckung"];
  const items = policyList
    .sort(compareId)
    .map((policy) => policyToTableItem(policy, customerId))
    .filter(itemSearch(search));
  return { headers, items };
}

function policyToTableItem(policy: PolicyShort, customerId: number) {
  const { id, name, startDate, endDate, coverage, active } = policy;
  const row = [id, name, startDate, endDate, coverage];
  const actions = {
    details: `/customer/${customerId}/policy/${id}`,
    edit: `/customer/${customerId}/policy/${id}?edit`,
  };
  return { row, actions, active };
}
