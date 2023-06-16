import { HandlerContext } from "$fresh/server.ts";
import { asset } from "$fresh/runtime.ts";
import { render } from "preact-render-to-string";
import { deserializePolicyFull } from "$this/common/deserialize.ts";
import { policyClient } from "$this/common/client.ts";
import { PolicyCalc } from "$this/generated/index.ts";

export const handler = {
  GET(_1: Request, _2: HandlerContext) {
    return respond({ error: "" });
  },
  async POST(req: Request, _: HandlerContext) {
    const form = await req.formData();
    const calcPolicy = deserializePolicyCalc(form);
    try {
      const { premium } = await policyClient.calcPolicyPrice(calcPolicy);
      const [USD, SAR, BTC] = await convertCurrency(premium);
      const converted = { EUR: premium, USD, SAR, BTC };
      return respond({ premium: converted });
    } catch (_) {
      return respond({ error: "error" });
    }
  },
};

function round(val?: number) {
  if (val == null) {
    return NaN;
  }
  const integer = Math.trunc(val);
  const rest = val - integer;
  const rounded = Number.parseFloat(rest.toPrecision(2));
  return integer + rounded;
}

async function convertCurrency(amount: number) {
  const api = `https://api.exchangerate.host/latest` +
    `?base=EUR&symbols=USD,SAR,BTC&amount=${amount}`;
  const response = await fetch(api);
  const { rates: { USD, SAR, BTC } } = await response.json();
  return [round(USD), round(SAR), round(BTC)];
}

function respond(premium: Props) {
  const options = { status: 200, headers: { "Content-Type": "text/html" } };
  const body = PremiumDisplay(premium);
  return new Response(body, options);
}

interface Props {
  premium?: {
    EUR: number;
    USD: number;
    SAR: number;
    BTC: number;
  };
  error?: string;
}

export function PremiumDisplay({ premium, error }: Props) {
  const { EUR, USD, SAR, BTC } = premium ?? {};
  const btnContainer =
    "absolute flex justify-between transform -translate-y-1/2 left-2 right-2 top-1/2";
  const slide = "carousel-item relative w-full";
  const btn = "btn btn-circle btn-xs";
  const html = render(
    <html lang="de" data-theme="retro">
      <head>
        <meta charSet="UTF-8" />
        <link rel="stylesheet" href={asset("/meowmed.css")} />
      </head>
      <body class="absolute inset-0 flex select-none">
        {premium != null
          ? (
            <div class="carousel w-full">
              <div id="slide1" class={slide}>
                <p class="m-auto">{EUR + "€"}</p>
                <div class={btnContainer}>
                  <a href="#slide4" class={btn}>₿</a>
                  <a href="#slide2" class={btn}>$</a>
                </div>
              </div>
              <div id="slide2" class={slide}>
                <p class="m-auto">{"$" + USD}</p>
                <div class={btnContainer}>
                  <a href="#slide1" class={btn}>€</a>
                  <a href="#slide3" class={btn}>﷼</a>
                </div>
              </div>
              <div id="slide3" class={slide}>
                <p class="m-auto">{SAR + "﷼"}</p>
                <div class={btnContainer}>
                  <a href="#slide2" class={btn}>$</a>
                  <a href="#slide4" class={btn}>₿</a>
                </div>
              </div>
              <div id="slide4" class={slide}>
                <p class="m-auto">{"₿" + BTC}</p>
                <div class={btnContainer}>
                  <a href="#slide3" class={btn}>﷼</a>
                  <a href="#slide1" class={btn}>€</a>
                </div>
              </div>
            </div>
          )
          : <p class="m-auto">{error}</p>}
      </body>
    </html>,
  );
  return `<!DOCTYPE html>${html}`;
}

function deserializePolicyCalc(form: FormData) {
  const id = (form.get("customerId") ?? NaN) as number;
  if (isNaN(id)) { //intentionally not using Number.isNaN()
    throw new Error("customerId missing for premium calculation");
  }
  return { customerId: id, policy: deserializePolicyFull(form) } as PolicyCalc;
}
