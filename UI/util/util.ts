import { ObjectSerializer } from "$this/generated/models/ObjectSerializer.ts";

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

export function deserialize<T>(form: FormData, type: string) {
  const entries = Array.from(form) as Array<[string, string]>;
  const result = entries.reduce(nestProperty, {});
  return ObjectSerializer.deserialize(result, type, "") as T;
}

export function nestProperty(
  target: Record<string, unknown>,
  [key, val]: [string, string],
) {
  const keys = key.split(".");
  const last = keys.pop()!; //keys cannot be empty because of split
  keys.reduce(
    (prev, cur) => (prev[cur] ??= {}) as Record<string, unknown>,
    target,
  )[last] = val;
  return target;
}

// deno-lint-ignore ban-types
export function propMap<T extends object>(object: T, prefix = "") {
  const props = Object.entries(object)
    .map(([key, _]) => [key, `${prefix}${key}`]);
  const propMap = Object.fromEntries(props);
  return propMap as Record<keyof T, string>;
}
