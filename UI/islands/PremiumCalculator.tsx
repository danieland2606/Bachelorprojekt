import { asset } from "$fresh/runtime.ts";
import { useEffect, useState } from "preact/hooks";

type PremiumProps = { className: string; form: string; api: string };

export default function PremiumCalculator(
  props: PremiumProps,
) {
  const { className, form, api } = props;
  const iframe = "premium-calc";
  const [isLoading, setIsLoading] = useState(false);
  const setLoading = () => setIsLoading(true);
  const setLoaded = () => setTimeout(() => setIsLoading(false), 500);
  useEffect(() => {
    const formNode = document.querySelector(`#${form}`);
    formNode?.addEventListener("submit", setLoading);
    return () => formNode?.removeEventListener("submit", setLoading);
  });

  return (
    <div class={`input-group ${className}`}>
      <button
        type="submit"
        form={form}
        class="btn btn-normal mb-4 w-1/2"
        formTarget={iframe}
        formAction={api}
      >
        <span class="bg-transparent text-inherit">Rate berechnen</span>
        {isLoading && <img src={asset("/ring-resize.svg")} />}
      </button>
      <iframe
        name={iframe}
        class="input input-bordered"
        src={api}
        onLoad={setLoaded}
      />
    </div>
  );
}
