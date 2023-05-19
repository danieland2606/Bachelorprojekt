import { ObjectSerializer } from "$this/generated/models/ObjectSerializer.ts";
import {
  CustomerAllRequired,
  PolicyAllRequired,
} from "$this/generated/index.ts";

export function deserializeCustomerFull(form: FormData) {
  return deserialize<CustomerAllRequired>(form, "CustomerAllRequired");
}

export function deserializePolicyFull(form: FormData) {
  return deserialize<PolicyAllRequired>(form, "PolicyAllRequired");
}

export function deserialize<T>(form: FormData, type: string) {
  const entries = Array.from(form) as Array<[string, string]>;
  const result = entries.reduce(nestProperty, {});
  return ObjectSerializer.deserialize(result, type, "") as T;
}

function nestProperty(
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
