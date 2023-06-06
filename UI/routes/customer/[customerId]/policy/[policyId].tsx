import { HandlerContext, PageProps } from "$fresh/server.ts";
import { EditPolicy } from "$this/components/EditPolicy.tsx";
import { Table } from "$this/components/Table.tsx";
import { ButtonRow } from "$this/components/ButtonRow.tsx";
import { invoiceClient, policyClient } from "$this/common/client.ts";
import { deserializePolicyFull } from "$this/common/deserialize.ts";
import { redirect } from "$this/common/util.ts";
import { Invoice } from "$this/generated/index.ts";

export const handler = {
  async GET(req: Request, ctx: HandlerContext) {
    const customerId = Number.parseInt(ctx.params.customerId);
    const policyId = Number.parseInt(ctx.params.policyId);
    const edit = new URL(req.url).searchParams.get("edit") != null;
    const policy = await policyClient.getPolicy(customerId, policyId);
    const invoices = await invoiceClient.getInvoiceList(customerId, policyId);
    return await ctx.render({ policy, invoices, edit });
  },
  async POST(req: Request, ctx: HandlerContext) {
    const customerId = Number.parseInt(ctx.params.customerId);
    const policyId = Number.parseInt(ctx.params.policyId);
    const form = await req.formData();
    const policy = deserializePolicyFull(form);
    await policyClient.updatePolicy(customerId, policyId, policy);
    return redirect(`/customer/${customerId}`);
  },
};

export default function ShowPolicy({ params, data }: PageProps) {
  const { edit, policy, invoices } = data;
  const [tableData, altData] = formatInvoiceList(invoices);
  const id = "edit-policy";
  return (
    <>
      <h1>Vertragsdetails</h1>
      <EditPolicy
        id={id}
        values={policy}
        customerId={params.customerId}
        allrequired
        mode={edit ? "edit" : "display"}
      />
      <Table tabledata={tableData} class="mt-6 max-md:hidden" />
      <Table tabledata={altData} class="mt-6 md:hidden" />
      <ButtonRow>
        <a
          class="btn px-10"
          href={`/customer/${params.customerId}`}
        >
          Zurück
        </a>
        <input
          form={id}
          type="submit"
          formAction={`/customer/${params.customerId}/policy/${params.policyId}`}
          formMethod="post"
          class={"btn px-10" + (edit ? "" : " invisible")}
          value="Änderungen bestätigen"
        />
      </ButtonRow>
    </>
  );
}

function formatInvoiceList(invoices: Invoice[]) {
  const headers = ["ID", "Fälligkeit", "Betrag", "Betreff"];
  const altHeaders = ["Fälligkeit", "Betrag"];
  const items = invoices.map(invoiceToTableItem);
  const altItems = invoices.map(invoiceToAltItem);
  return [{ headers, items }, { headers: altHeaders, items: altItems }];
}

function invoiceToTableItem(invoice: Invoice) {
  const { id, dueDate, amount, details } = invoice;
  const row = [id, dueDate, `${amount}€`, details];
  return { row, active: true, actions: {} };
}

function invoiceToAltItem(invoice: Invoice) {
  const { dueDate, amount } = invoice;
  const row = [dueDate, amount];
  return { row, active: true, actions: {} };
}
