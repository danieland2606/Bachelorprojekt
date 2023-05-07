export function capitalize(str: string) {
  return (str.charAt(0).toUpperCase() + str.slice(1)) ?? "";
}

export function compareId({ id: id1 = 0 }, { id: id2 = 0 }) {
  return id1 - id2;
}

type primitive = undefined | null | string | number | boolean | symbol | bigint;

type Rec<T> = Record<string, primitive | T>;

// deno-lint-ignore no-empty-interface
export interface Obj extends Rec<Obj> {}

export function resolve(path: string, obj?: Obj) {
  return path.split(".").reduce(
    function (prev: Obj | primitive, curr: string) {
      return prev && typeof prev === "object" ? prev[curr] : null;
    },
    obj,
  );
}

export function identity<T>(a: T) {
  return a;
}
