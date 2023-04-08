import { Application, Router } from "https://deno.land/x/oak/mod.ts";
import { oakCors } from "https://deno.land/x/cors/mod.ts";
import customer from "./customer.json" assert { type: "json" };
import customerList from "./customers.json" assert { type: "json" };
import policyList from "./policies.json" assert { type: "json" };

const router = new Router();
router
  .get("/", (context) => {
    context.response.body = "Welcome to dinosaur API!";
  })
  .get("/customers", (context) => {
    context.response.body = customerList;
  })
  .get("/customers/:id", (context) => {
    context.response.body = customer;
  });

const app = new Application();
app.use(oakCors()); // Enable CORS for All Routes
app.use(router.routes());
app.use(router.allowedMethods());

await app.listen({ port: 8000 });