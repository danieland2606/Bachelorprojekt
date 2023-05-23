import { asset } from "$fresh/runtime.ts";
import { JSX } from "preact/jsx-runtime";

export function Search(
  props: JSX.HTMLAttributes<HTMLFormElement> & { value?: string },
) {
  const value = props.value;
  delete props.value;
  return (
    <form {...props} rel="search" class={`${props.class} input-group`}>
      <button
        type="submit"
        class="input w-fit bg-text"
      >
        <img
          src={asset("/search.svg")}
          class="scale-150"
        />
      </button>
      <input
        name="search"
        type="text"
        value={value}
        placeholder="Suche..."
        class="input input-bordered"
      />
    </form>
  );
}
