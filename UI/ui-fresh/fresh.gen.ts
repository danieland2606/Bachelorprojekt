// DO NOT EDIT. This file is generated by fresh.
// This file SHOULD be checked into source version control.
// This file is automatically updated during development when running `dev.ts`.

import config from "./deno.json" assert { type: "json" };
import * as $0 from "./routes/_app.tsx";
import * as $1 from "./routes/api/premium.tsx";
import * as $2 from "./routes/customer.tsx";
import * as $3 from "./routes/customer/[customerId].tsx";
import * as $4 from "./routes/customer/[customerId]/policy.tsx";
import * as $5 from "./routes/customer/[customerId]/policy/[policyId].tsx";
import * as $6 from "./routes/index.tsx";
import * as $7 from "./routes/test.jsx";

const manifest = {
  routes: {
    "./routes/_app.tsx": $0,
    "./routes/api/premium.tsx": $1,
    "./routes/customer.tsx": $2,
    "./routes/customer/[customerId].tsx": $3,
    "./routes/customer/[customerId]/policy.tsx": $4,
    "./routes/customer/[customerId]/policy/[policyId].tsx": $5,
    "./routes/index.tsx": $6,
    "./routes/test.jsx": $7,
  },
  islands: {},
  baseUrl: import.meta.url,
  config,
};

export default manifest;
