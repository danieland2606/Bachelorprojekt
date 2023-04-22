import { HandlerContext, PageProps } from "$fresh/server.ts";

export const handler = {
  GET(_: Request, ctx: HandlerContext) {
    return ctx.renderNotFound();
  },
  async POST(req: Request, ctx: HandlerContext) {
    const form = await req.formData();
    console.debug("premium");
    for (const [key, val] of form.entries()) {
      console.debug(`key: ${key}, val: ${val}`);
    }
    return ctx.render({ premium: 75 });
  },
};

export default function PremiumDisplay({ data }: PageProps) {
  return (
    <>
      <p>{data.premium}</p>
    </>
  );
}
