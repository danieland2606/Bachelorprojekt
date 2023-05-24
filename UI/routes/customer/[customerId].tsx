import { HandlerContext, PageProps } from "$fresh/server.ts";
import { EditCustomer } from "$this/components/EditCustomer.tsx";
import { itemSearch, Table } from "$this/components/Table.tsx";
import { Search } from "$this/components/Search.tsx";
import { compareId, redirect } from "$this/common/util.ts";
import { PolicyShort } from "$this/common/types.ts";
import { deserializeCustomerFull } from "$this/common/deserialize.ts";
import { customerClient } from "$this/common/customerClient.ts";
import { policyClient } from "$this/common/policyClient.ts";
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
    const [tableData, altData] = formatPolicyList(
      policyList,
      customerId,
      search,
    );
    const customer = await customerClient.getCustomer(customerId);
    return ctx.render({ tableData, altData, customer, edit, search });
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
  const { edit, search, customer, tableData, altData } = data;
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
      <div class="py-2 md:py-4 flex flex-col-reverse md:flex-row md:justify-between">
        <Search
          value={search}
          class="my-2 md:my-0 flex-1 md:flex-none md:w-fit"
        />
        <a
          href={`/customer/${params.customerId}/policy`}
          class={"btn btn-normal flex-1 md:flex-none md:w-fit" +
            disableIfUnemployed(employmentStatus)}
        >
          Neuer Vertrag
        </a>
      </div>
      <Table tabledata={tableData} class="max-md:hidden" />
      <Table tabledata={altData} class="md:hidden" />
      <div class="flex flex-col md:flex-row py-2 md:py-4 md:justify-between">
        <a class="my-2 btn btn-normal md:flex-none md:w-fit" href="/">Zurück</a>
        {edit &&
          (
            <input
              form={id}
              type="submit"
              formAction={`/customer/${params.customerId}`}
              formMethod="post"
              class="btn btn-normal"
              value="Änderungen bestätigen"
            />
          )}
      </div>
    </>
  );
}

function disableIfUnemployed(status: EmploymentStatus) {
  if (status === "arbeitslos") {
    return " pointer-events-none";
  }
  return "";
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

  const altHeaders = ["Beginn", "Ende", "Limit"];
  const altItems = policyList
    .sort(compareId)
    .map((policy) => policyToAltItem(policy, customerId))
    .filter(itemSearch(search));
  return [{ headers, items }, { headers: altHeaders, items: altItems }];
}

function policyToTableItem(policy: PolicyShort, customerId: number) {
  const { id, name, startDate, endDate, coverage, active } = policy;
  const row = [id, name, startDate, endDate, coverage + "€"];
  const actions = {
    details: `/customer/${customerId}/policy/${id}`,
    edit: `/customer/${customerId}/policy/${id}?edit`,
  };
  return { row, actions, active };
}

function policyToAltItem(policy: PolicyShort, customerId: number) {
  const { id, startDate, endDate, coverage, active } = policy;
  const row = [startDate, endDate, coverage + "€"];
  const actions = {
    details: `/customer/${customerId}/policy/${id}`,
    edit: `/customer/${customerId}/policy/${id}?edit`,
  };
  return { row, actions, active };
}
