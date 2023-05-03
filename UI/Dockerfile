ARG mode='prod'

FROM denoland/deno:alpine-1.33.0 AS base
WORKDIR /app
COPY ./components ./components
COPY ./generated ./generated
COPY ./islands ./islands
COPY ./routes ./routes
COPY ./static ./static
COPY ./util ./util
COPY ./main.ts ./fresh.gen.ts ./import_map.json ./deno.json ./

FROM base AS meowmed-ui-test
RUN rm /app/util/client_prod.ts

FROM base AS meowmed-ui-prod
ARG customerService
ARG policyService
RUN mv ./util/client_prod.ts ./util/client.ts
RUN sed -i s~\${customerService}~$customerService~ ./util/client.ts
RUN sed -i s~\${policyService}~$policyService~ ./util/client.ts

FROM meowmed-ui-${mode} AS meowmed-ui
EXPOSE 8000
CMD ["run", "-A", "main.ts"]