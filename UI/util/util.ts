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
//https://stackoverflow.com/questions/4244896/accessing-an-object-property-with-a-dynamically-computed-name
export function resolve(path: string, obj?: Obj) {
  return path.split(".").reduce(
    function (prev: Obj | primitive, curr: string) {
      return prev && typeof prev === "object" ? prev[curr] : null;
    },
    obj,
  );
}

// deno-lint-ignore ban-types
export function propMap<T extends object>(object: T, prefix = "") {
  const props = Object.entries(object)
    .map(([key, _]) => [key, `${prefix}${key}`]);
  const propMap = Object.fromEntries(props);
  return propMap as Record<keyof T, string>;
}

export function origin({ url }: Request) {
  return new URL(url).origin;
}
