export async function fetchCustomerList() {
  const body = await fetchLocal('customers.json');
  return body.customerList;
}

export async function fetchCustomer() {
  const body = await fetchLocal('customer.json');
  return body;
}

export async function fetchPolicyList() {
  const body = await fetchLocal('policies.json');
  return body.policyList;
}

async function fetchLocal(path) {
  const response = await fetch(path);
  const json = await response.text();
  return JSON.parse(json);
}