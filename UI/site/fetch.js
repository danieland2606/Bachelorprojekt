import { encodeCalcObject } from "./framework/util.js";

const customerAddress = 'http://localhost:82';
const policyAddress = 'http://localhost:81';

function customerPath(id) {
  let base = `${customerAddress}/customer`;
  if(id)
    base += `/${id}`;
  return base;
}

function policyPath(customerId, id) {
  let base = `${policyAddress}/customer/${customerId}/policy`;
  if(id)
    base += `/${id}`;
  return base;
}

export async function getCustomerList(args) {
  return get(customerPath(), args);
}

export async function getCustomer(id) {
  return get(customerPath(id));
}

export async function getPolicyList(customerId, args) {
  return get(policyPath(customerId), args);
}

export async function getPolicy(customerId, id) {
  return get(policyPath(customerId, id));
}

export async function postCustomer(customer) {
  return post(customerPath(), customer);
}

export async function postPolicy(customerId, policy) {
  return post(policyPath(customerId), policy);
}

export async function getPremium(calcObject) {
  const args = { details: encodeCalcObject(calcObject) };
  return get(`${policyAddress}/policyprice`, args);
}

async function post(address, payload) {
  const request = {
    method: 'POST',
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  }
  const response = await fetch(address, request);
  return response.json();
}

async function get(address, args) {
  const url = address + encodeArgs(args);
  const response = await fetch(url);
  const json = await response.json();
  return json;
}

function encodeArgs(args) {
  let urlParams = '?';
  if(typeof args === 'object')
    for(const [ name, arg ] of Object.entries(args)) {
      urlParams += `${name}=`;
      if(Array.isArray(arg)) {
        urlParams += arg.join(',');
      } else {
        urlParams += arg;
      }
      urlParams += '&';
    }
  return urlParams.slice(0, -1);
}
