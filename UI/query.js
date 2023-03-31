export async function fetchCustomerList() {
	const response = await fetch('customers.json');
	const json = await response.text();
	return JSON.parse(json).customerList;
}
