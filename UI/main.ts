/// <reference no-default-lib="true" />
/// <reference lib="dom" />
/// <reference lib="dom.iterable" />
/// <reference lib="dom.asynciterable" />
/// <reference lib="deno.ns" />

import { InnerRenderFunction, RenderContext, start } from "$fresh/server.ts";
import manifest from "$this/fresh.gen.ts";

function render(ctx: RenderContext, render: InnerRenderFunction) {
  ctx.lang = "de";
  render();
}

await start(manifest, { render });
