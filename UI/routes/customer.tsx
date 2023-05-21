import { HandlerContext } from "$fresh/server.ts";
import { EditCustomer } from "$this/components/EditCustomer.tsx";
import { customerClient } from "$this/common/customerClient.ts";
import { deserializeCustomerFull } from "$this/common/deserialize.ts";
import { origin } from "$this/common/util.ts";

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
      <h1>Neuer Kunde</h1>
      <EditCustomer
        id={id}
        action="/customer"
        method="post"
        allrequired
        mode="create"
      >
      </EditCustomer>
      <div class="box-row buttons sm:flex py-5 justify-between block">
        <a class="button btn btn-normal flex sm:inline-flex mb-4 sm:mb-0" href="/">Abbrechen</a>
        <input
          form={id}
          type="submit"
          class="button btn btn-normal flex sm:inline-flex"
          value="Anlegen"
        >
        </input>
      </div>
    </>
  );
}
