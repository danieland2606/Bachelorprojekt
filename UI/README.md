# Meowmed+ UI

## Setup

Build dependecies are npm and Deno.

[node](https://nodejs.org/en)
[Deno](https://deno.com/manual@v1.34.0/getting_started/installation)

Deno install scripts are provided for [Windows](install-deno.ps1) and
[Linux](install-deno).

## Project structure

The project uses the [fresh](https://fresh.deno.dev/) framework running on a
[deno](https://deno.com/runtime) Server. Fresh uses Reacts jsx format, by
default compiled to html with [Preact](https://preactjs.com/).

### Client

Communication with the backend is handled by the client code generated from the
[OpenAPI 3.0 specification](../Meowmed%2B_REST.yaml). The client is found in
[/generated] and is checked into source control. It shouldn't have to be
regenerated unless the API changes.

### Routes

Fresh uses file system routing. As such, each file under [/routes] not starting
with _ (underscore) is a page of the site. Underscored files have special
meaning to the fresh framework, see the fresh documentation. Files in
[/routes/api] are used internally and only return an empty page on GET.

### Components

The major Components are [Table](/components/Table.tsx),
[Form](/components/Form.tsx), [EditCustomer](/components/EditCustomer.tsx) and
[EditPolicy](/components/EditPolicy.tsx). The content of Table is dynamically
generated based on its interface TableItems. Form contains common functionality
of EditCustomer and EditPolicy. These Components are used to both create,
display and change the properties of Customer and Policy, respectively.

## Islands

Islands ship Javascript to the browser. Currently they are only used to indicate
loading.

## Common Functionality

Functionality that is used by multiple routes, islands or Components is
currently collected in [/common]. customerClient and policyClient only provide
test data to allow the development server to display data. In the docker build
they are replaced by their _prod variants. These pass functionality from the
generated client with some minor processing.

Other files in /common collect functionality that has some internal
relationship. Functionality that is used in multiple places but not yet grouped
with related functionality is collectd in [/common/util.ts], until such a time
when a relationship with other functionality becomes apparent.

## Deno tasks

### start

Start the development server with `deno task start`. This will watch the project
directory and restart as necessary. The server runs on local port 8000.

### generate

After changing the [OpenAPI document](../Meowmed%2B_REST.yaml), re-generate the
client code with `deno task generate`.

### build

Generate minified css with `deno task build-css`.
