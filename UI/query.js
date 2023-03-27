export async function fetchCustomer() {
	let response = await fetch('customers.json');
	return response.text();
}
