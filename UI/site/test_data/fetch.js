import { encodeCalcObject } from "./framework/util.js";

export async function getCustomerList(args) {
  const body = await fetchLocal('customers.json');
  return body.customerList;
}

export async function getCustomer(id) {
  const body = await fetchLocal('customer.json');
  return body;
}

export async function getPolicyList(customerId, args) {
  const body = await fetchLocal('policies.json');
  return body.policyList;
}

export async function getPolicy(customerId, id) {
  const body = await fetchLocal('policy.json');
  return body;
}

export async function postCustomer(customer) {
  console.log(JSON.stringify(customer));
}

export async function postPolicy(customerId, policy) {
  console.log(JSON.stringify(policy));
}

let counter = 0;

export async function getPremium(calcObject) {
  console.log(encodeCalcObject(calcObject));
  return counter++;
}

async function fetchLocal(path) {
  const response = await fetch('test_data/' + path);
  return await response.json();
}