import { ErrorPageProps } from "$fresh/server.ts";
import { asset } from "$fresh/runtime.ts";

export default function Error500Page({ error }: ErrorPageProps) {
  const err = error as Error;
  return (
    <>
      <div class="alert alert-error shadow-lg">
        <div>
          <img src={asset("/circle-x.svg")} />
          <span>500: Internal Server Error</span>
        </div>
      </div>
      <div class="mockup-code bg-error text-black mt-2 p-2">
        <pre><code>{err.stack ?? err.message}</code></pre>
      </div>
    </>
  );
}
