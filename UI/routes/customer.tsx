import { HandlerContext } from "$fresh/server.ts";
import { EditCustomer } from "$this/components/EditCustomer.tsx";
import { customerClient } from "$this/util/client.ts";
import { deserializeCustomerFull } from "$this/util/deserialize.ts";
import { origin } from "$this/util/util.ts";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    return await ctx.render();
  },
  async POST(req: Request, _: HandlerContext) {
    const form = await req.formData();
    const customer = deserializeCustomerFull(form);
    await customerClient.createCustomer(customer);
    const index = new URL("/", origin(req));
    return Response.redirect(index, 303);
  },
};

export default function CreateCustomer() {
  const id = "new-customer";
  return (
    <>
      <h1>Neuen Kunden anlegen</h1>
      <EditCustomer
        id={id}
        action="/customer"
        method="post"
        allrequired
        mode="create"
      >
      </EditCustomer>
      <div class="box-row buttons">
        <a class="button" href="/">Abbrechen</a>
        <input
          form={id}
          type="submit"
          class="button"
          value="Anlegen"
        >
        </input>
      </div>
    </>
  );
}
