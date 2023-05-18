import { HandlerContext, PageProps } from "$fresh/server.ts";
import { EditCustomer } from "$this/components/EditCustomer.tsx";
import { Table } from "$this/components/Table.tsx";
import { Search } from "$this/components/Search.tsx";
import { CustomerAllRequired, Policy } from "$this/generated/models/all.ts";
import { compareId, deserialize } from "$this/util/util.ts";
import { customerClient, policyClient } from "$this/util/client.ts";

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
    const customer = deserialize<CustomerAllRequired>(
      form,
      "CustomerAllRequired",
    );
    await customerClient.updateCustomer(customerId, customer);
    const base = new URL(req.url).origin;
    return Response.redirect(new URL("/", base), 303);
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
  policyList: Policy[],
  customerId: number,
  search = "",
) {
  return {
    headers: ["ID", "Katze", "Beginn", "Ende", "Jahresdeckung"],
    items: policyList
      .filter(policySearch(search))
      .sort(compareId)
      .map((policy) => policyToTableRow(policy, customerId)),
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
      edit: `/customer/${customerId}/policy/${policy.id}?edit`,
      delete: "",
    },
  };
}

function policySearch(search: string) {
  if (!search) {
    return function () {
      return true;
    };
  }
  return (policy: Policy) => {
    const searchTarget =
      `${policy.id} ${policy.objectOfInsurance?.name} ${policy.startDate} ${policy.endDate} ${policy.coverage}`;
    return searchTarget.toLowerCase().includes(search.toLowerCase());
  };
}
