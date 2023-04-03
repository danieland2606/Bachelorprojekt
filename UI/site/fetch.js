export async function fetchCustomerList(fields) {
  const body = await fetchLocal('customers.json');
  return body.customerList;
}

export async function fetchCustomer(id) {
  const body = await fetchLocal('customer.json');
  return body;
}

export async function fetchPolicyList(fields) {
  const body = await fetchLocal('policies.json');
  return body.policyList;
}

async function fetchLocal(path) {
  const response = await fetch('test_data/' + path);
  const json = await response.text();
  return JSON.parse(json);
}