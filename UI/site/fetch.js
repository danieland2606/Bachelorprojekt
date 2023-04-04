export async function fetchCustomerList(fields) {
  const body = await fromGateway('customers.json');
  return body.customerList;
}

export async function fetchCustomer(id) {
  const body = await fromGateway('customer.json');
  return body;
}

export async function fetchPolicyList(fields) {
  const body = await fromGateway('policies.json');
  return body.policyList;
}

async function get(path, args) {

}

async function fromGateway(path, request) {
  const response = await fetch('gateway/' + path, request);
  const json = await response.text();
  return JSON.parse(json);
}