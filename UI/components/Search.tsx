import { asset } from "$fresh/runtime.ts";
import { JSX } from "preact/jsx-runtime";

export function Search(
  props: JSX.HTMLAttributes<HTMLFormElement> & { value?: string },
) {
  return (
    <form {...props} rel="search">
      <input
        name="search"
        type="text"
        value={props.value}
        placeholder="Suche..."
        class="input"
      />
      <div class="absolute right-2 top-1/2 -translate-y-1/2">
        <input
          type="submit"
          value=""
          class="bg-search bg-no-repeat bg-cover w-8 h-8"
        >
        </input>
      </div>
    </form>
  );
}
