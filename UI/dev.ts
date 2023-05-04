#!/usr/bin/env -S deno run -A --watch=static/,routes/

import dev from "$fresh/dev.ts";

let rev = Deno.readTextFileSync("../.git/HEAD").toString().trim();
if (rev.indexOf(":") !== -1) {
  rev = Deno.readTextFileSync("../.git/" + rev.substring(5)).toString()
    .trim();
}

Deno.writeTextFileSync("ui.env", `DENO_DEPLOYMENT_ID=${rev}`);

await dev(import.meta.url, "./main.ts");
