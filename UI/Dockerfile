FROM denoland/deno:alpine-1.33.0 AS test
WORKDIR /app
COPY ./components ./components
COPY ./generated ./generated
COPY ./islands ./islands
COPY ./routes ./routes
COPY ./static ./static
COPY ./common ./common
COPY ./main.ts ./fresh.gen.ts ./import_map.json ./deno.json ./
ENV DENO_DEPLOYMENT_ID=acbd0ae640b1b138a253f4133d01f7895012770d
EXPOSE 8000
CMD ["run", "-A", "main.ts"]

FROM test AS prod
RUN mv ./common/customerClient_prod.ts ./common/customerClient.ts
RUN mv ./common/policyClient_prod.ts ./common/policyClient.ts
RUN rm -rf ./static/test
