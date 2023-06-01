FROM denoland/deno:alpine-1.34.1 AS test
WORKDIR /app
COPY ./components ./components
COPY ./generated ./generated
COPY ./islands ./islands
COPY ./routes ./routes
COPY ./static ./static
COPY ./common ./common
COPY ./main.ts ./fresh.gen.ts ./import_map.json ./deno.json ./
ENV DENO_DEPLOYMENT_ID=2cc7e65bceab82a223b4a8555a874adb6afc1bf8
EXPOSE 8000
CMD ["run", "-A", "main.ts"]

FROM test AS prod
RUN mv ./common/client_prod.ts ./common/client.ts
RUN rm -rf ./static/test
