import { HandlerContext } from "$fresh/server.ts";
import { asset } from "$fresh/runtime.ts";
import { deserializePolicyFull } from "$this/common/deserialize.ts";
import { policyClient } from "$this/common/policyClient.ts";
import { PolicyCalc } from "$this/generated/index.ts";

export const handler = {
  GET(_1: Request, _2: HandlerContext) {
    return respond("");
  },
  async POST(req: Request, _: HandlerContext) {
    const form = await req.formData();
    const calcPolicy = deserializePolicyCalc(form);
    try {
      const { premium } = await policyClient.calcPolicyPrice(calcPolicy);
      return respond(premium.toString());
    } catch (_) {
      return respond("error");
    }
  },
};

function respond(premium: string) {
  const options = { status: 200, headers: { "Content-Type": "text/html" } };
  const body = PremiumDisplay(premium);
  return new Response(body, options);
}

export function PremiumDisplay(premium: string) {
  return (
    `<!DOCTYPE html>` +
    `<html lang="de" data-theme="retro">` +
    `<head>` +
    `<meta charSet="UTF-8" />` +
    `<link rel="stylesheet" href="${asset("/meowmed.css")}"/>` +
    `</head>` +
    `<body class="absolute inset-0 flex select-none">` +
    `<p class="m-auto">${premium}</p>` +
    `</body>` +
    `</html>`
  );
}

function deserializePolicyCalc(form: FormData) {
  const id = (form.get("customerId") ?? NaN) as number;
  if (isNaN(id)) { //intentionally not using Number.isNaN()
    throw new Error("customerId missing for premium calculation");
  }
  return { customerId: id, policy: deserializePolicyFull(form) } as PolicyCalc;
}
