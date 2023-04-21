import { HandlerContext } from "$fresh/server.ts";
import { EditCustomer } from "../../components/EditCustomer.tsx";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    return await ctx.render();
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

export default function CreateCustomer() {
  return (
    <>
      <h1>Neuen Kunden anlegen</h1>
      <EditCustomer id="new-customer" allrequired>
        <div class="box-row buttons">
          <a class="button" href="/">Abbrechen</a>
          <input
            form="new-customer"
            type="submit"
            class="button"
            value="Anlegen"
          >
          </input>
        </div>
      </EditCustomer>
    </>
  );
}
