import { MiddlewareHandlerContext } from "$fresh/server.ts";

export const handler = [
  async function setTheme(_: Request, ctx: MiddlewareHandlerContext) {
    const response = await ctx.next();
    const options = {
      status: response.status,
      statusText: response.statusText,
      headers: response.headers,
    };
    let body: string | null = await response.text();
    body = body?.replace(
      /^<!DOCTYPE html><html/,
      '<!DOCTYPE html><html data-theme="cupcake"',
    ) || null;
    return new Response(body, options);
  },
];
