import { HandlerContext, PageProps } from "$fresh/server.ts";
import { EditCustomer } from "$this/components/EditCustomer.tsx";
import { itemSearch, Table } from "$this/components/Table.tsx";
import { SearchBar } from "$this/components/SearchBar.tsx";
import { ButtonRow } from "$this/components/ButtonRow.tsx";
import { compareId, redirect } from "$this/common/util.ts";
import { PolicyShort } from "$this/common/types.ts";
import { deserializeCustomerFull } from "$this/common/deserialize.ts";
import { customerClient, policyClient } from "$this/common/client.ts";
import {
  CustomerAllRequired,
  EmploymentStatus,
} from "$this/generated/index.ts";

export const handler = {
  async GET(req: Request, ctx: HandlerContext) {
    const queryParams = new URL(req.url).searchParams;
    const edit = queryParams.get("edit") != null;
    const search = queryParams.get("search") ?? "";
    const customerId = Number.parseInt(ctx.params.customerId);
    const policyList = await policyClient.getPolicyList(customerId);
    const customer = await customerClient.getCustomer(customerId);
    return ctx.render({ policyList, customer, edit, search });
  },
  async POST(req: Request, ctx: HandlerContext) {
    const customerId = Number.parseInt(ctx.params.customerId);
    const form = await req.formData();
    const customer = deserializeCustomerFull(form);
    await customerClient.updateCustomer(customerId, customer);
    return redirect("/");
  },
};

export default function ShowCustomer({ data, params }: PageProps) {
  const { edit, search, customer, policyList } = data;
  const customerId = Number.parseInt(params.customerId);
  const [tableData, altData] = formatPolicyList(policyList, customerId, search);
  const employmentStatus = (customer as CustomerAllRequired).employmentStatus;
  const id = "edit-customer";
  return (
    <>
      <h1>Kundendetails</h1>
      <EditCustomer
        id={id}
        mode={edit ? "edit" : "display"}
        values={customer}
        allrequired
      />
      <SearchBar value={search}>
        <a
          href={`/customer/${params.customerId}/policy`}
          class={"btn px-10" + disableIfUnemployed(employmentStatus)}
        >
          Neuer Vertrag
        </a>
      </SearchBar>
      <Table tabledata={tableData} class="max-md:hidden" />
      <Table tabledata={altData} class="md:hidden" />
      <ButtonRow>
        <a class="btn px-10" href="/">Zurück</a>
        <input
          form={id}
          type="submit"
          formAction={`/customer/${params.customerId}`}
          formMethod="post"
          class={"btn px-10" + (edit ? "" : " invisible")}
          value="Änderungen bestätigen"
        />
      </ButtonRow>
    </>
  );
}

function disableIfUnemployed(status: EmploymentStatus) {
  if (status === "arbeitslos") {
    return " pointer-events-none btn-disabled";
  }
  return "";
}

function formatPolicyList(
  policyList: PolicyShort[],
  customerId: number,
  search = "",
) {
  const headers = ["ID", "Katze", "Beginn", "Ende", "Jahresdeckung"];
  const altHeaders = ["Beginn", "Ende", "Limit"];
  const displayed = policyList
    .filter(itemSearch(search))
    .sort(compareId);
  const items = displayed
    .map((policy) => policyToTableItem(policy, customerId));
  const altItems = displayed
    .map((policy) => policyToAltItem(policy, customerId));
  return [{ headers, items }, { headers: altHeaders, items: altItems }];
}

function policyToTableItem(policy: PolicyShort, customerId: number) {
  const { id, name, startDate, endDate, coverage, active } = policy;
  const row = [id, name, startDate, endDate, `${coverage}€`];
  const actions = formatActions(customerId, id);
  return { row, actions, active };
}

function policyToAltItem(policy: PolicyShort, customerId: number) {
  const { id, startDate, endDate, coverage, active } = policy;
  const row = [startDate, endDate, coverage + " €"];
  const actions = formatActions(customerId, id);
  return { row, actions, active };
}

function formatActions(customerId: number, policyId: number) {
  return {
    details: `/customer/${customerId}/policy/${policyId}`,
    edit: `/customer/${customerId}/policy/${policyId}?edit`,
  };
}
