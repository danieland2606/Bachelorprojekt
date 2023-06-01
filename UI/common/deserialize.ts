import { Obj, resolve } from "$this/common/util.ts";
import {
  CustomerAllRequired,
  PolicyAllRequired,
} from "$this/generated/index.ts";

function convert(val: string, type: string) {
  switch (type) {
    case "number":
      return Number.parseFloat(val);
    case "boolean":
      return val.toLowerCase() === "true";
    case "string":
      return val;
    default:
      throw new Error("Type not implemented");
  }
}

export function deserializeCustomerFull(form: FormData) {
  const result = deserialize(form, new CustomerAllRequired() as unknown as Obj);
  return result as unknown as CustomerAllRequired;
}

export function deserializePolicyFull(form: FormData) {
  const result = deserialize(form, new PolicyAllRequired() as unknown as Obj);
  return result as unknown as PolicyAllRequired;
}

export function deserialize<T extends Obj>(form: FormData, target: T) {
  const entries = Array.from(form) as Array<[string, string]>;
  return entries.reduce(nestProperty, target);
}

function nestProperty(target: Obj, [key, val]: [string, string]) {
  const keys = key.split(".");
  const last = keys.pop()!; //keys cannot be empty because of split
  const type = typeof resolve(key, target);
  if (type === "undefined" || isFiltered(last)) {
    return target;
  }
  keys.reduce(
    (prev, cur) => (prev[cur] ??= {}) as Obj,
    target,
  )[last] = convert(val, type);
  return target;
}

function isFiltered(key: string) {
  const filtered = ["premium", "active", "id", "customerId"];
  return filtered.includes(key);
}
