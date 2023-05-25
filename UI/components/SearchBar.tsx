import { asset } from "$fresh/runtime.ts";
import { JSX } from "preact/jsx-runtime";

export function SearchBar(
  props: JSX.HTMLAttributes<HTMLFormElement> & { value?: string },
) {
  const value = props.value;
  return (
    <div class="pt-5 pb-2 flex max-md:flex-col-reverse justify-between gap-2">
      <form
        rel="search"
        class="input-group flex md:w-fit"
      >
        <button type="submit" class="btn">
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
          class="input input-bordered grow"
        />
      </form>
      {props.children}
    </div>
  );
}
