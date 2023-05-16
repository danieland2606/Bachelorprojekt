import { HandlerContext } from "$fresh/server.ts";
import { EditCustomer } from "$this/components/EditCustomer.tsx";
import { customerClient } from "$this/util/client.ts";
import { deserialize } from "$this/util/util.ts";
import { CustomerAllRequired } from "$this/generated/index.ts";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    return await ctx.render();
  },
  async POST(req: Request, _: HandlerContext) {
    const form = await req.formData();
    const customer = deserialize<CustomerAllRequired>(
      form,
      "CustomerAllRequired",
    );
    await customerClient.createCustomer(customer);
    const base = new URL(req.url).origin;
    return Response.redirect(new URL("/", base), 303);
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
