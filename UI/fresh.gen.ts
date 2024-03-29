// DO NOT EDIT. This file is generated by fresh.
// This file SHOULD be checked into source version control.
// This file is automatically updated during development when running `dev.ts`.

import config from "./deno.json" assert { type: "json" };
import * as $0 from "./routes/_404.tsx";
import * as $1 from "./routes/_500.tsx";
import * as $2 from "./routes/_app.tsx";
import * as $3 from "./routes/api/premium.tsx";
import * as $4 from "./routes/customer.tsx";
import * as $5 from "./routes/customer/[customerId].tsx";
import * as $6 from "./routes/customer/[customerId]/policy.tsx";
import * as $7 from "./routes/customer/[customerId]/policy/[policyId].tsx";
import * as $8 from "./routes/index.tsx";
import * as $$0 from "./islands/DateChecker.tsx";
import * as $$1 from "./islands/PremiumCalculator.tsx";

const manifest = {
  routes: {
    "./routes/_404.tsx": $0,
    "./routes/_500.tsx": $1,
    "./routes/_app.tsx": $2,
    "./routes/api/premium.tsx": $3,
    "./routes/customer.tsx": $4,
    "./routes/customer/[customerId].tsx": $5,
    "./routes/customer/[customerId]/policy.tsx": $6,
    "./routes/customer/[customerId]/policy/[policyId].tsx": $7,
    "./routes/index.tsx": $8,
  },
  islands: {
    "./islands/DateChecker.tsx": $$0,
    "./islands/PremiumCalculator.tsx": $$1,
  },
  baseUrl: import.meta.url,
  config,
};

export default manifest;
