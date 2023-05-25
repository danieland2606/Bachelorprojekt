import { HandlerContext } from "$fresh/server.ts";
import { EditCustomer } from "$this/components/EditCustomer.tsx";
import { ButtonRow } from "$this/components/ButtonRow.tsx";
import { customerClient } from "$this/common/customerClient.ts";
import { deserializeCustomerFull } from "$this/common/deserialize.ts";
import { redirect } from "$this/common/util.ts";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    return await ctx.render();
  },
  async POST(req: Request, _: HandlerContext) {
    const form = await req.formData();
    const customer = deserializeCustomerFull(form);
    await customerClient.createCustomer(customer);
    return redirect("/");
  },
};

export default function CreateCustomer() {
  const id = "new-customer";
  return (
    <>
      <h1>Neuer Kunde</h1>
      <EditCustomer
        id={id}
        action="/customer"
        method="post"
        allrequired
        mode="create"
      />
      <ButtonRow>
        <a
          class="btn px-10"
          href="/"
        >
          Abbrechen
        </a>
        <input
          form={id}
          type="submit"
          class="btn px-10"
          value="Anlegen"
        />
      </ButtonRow>
    </>
  );
}
