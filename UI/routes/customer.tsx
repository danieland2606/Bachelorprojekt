import { HandlerContext } from "$fresh/server.ts";
import { EditCustomer } from "../components/EditCustomer.tsx";
import { customerClient } from "../util/client.ts";
import { Address, Customer, CustomerAllRequired } from "../generated/index.ts";

export const handler = {
  async GET(_: Request, ctx: HandlerContext) {
    return await ctx.render();
  },
  async POST(req: Request, _: HandlerContext) {
    const form = await req.formData();
    const customer = deserializeCustomer(form);
    await customerClient.createCustomer(customer);
    const base = new URL(req.url).origin;
    return Response.redirect(new URL("/", base), 303);
  },
};

export default function CreateCustomer() {
  return (
    <>
      <h1>Neuen Kunden anlegen</h1>
      <EditCustomer
        id="new-customer"
        action="/customer"
        method="post"
        allrequired
      >
      </EditCustomer>
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
    </>
  );
}

function deserializeCustomer(form: FormData): CustomerAllRequired {
  const customer: Record<string, any> = {};
  const types = Customer.attributeTypeMap.filter(({ name }) => name !== "id");
  for (const prop of types) {
    if (form.has(prop.name)) {
      customer[prop.name] = form.get(prop.name);
    } else if (prop.type === "Address") {
      customer[prop.name] = {};
      for (const subProp of Address.attributeTypeMap) {
        const qualifiedName = `${prop.name}.${subProp.name}`;
        if (form.has(qualifiedName)) {
          customer[prop.name][subProp.name] = form.get(qualifiedName);
        }
      }
    }
  }
  return customer as CustomerAllRequired;
}
