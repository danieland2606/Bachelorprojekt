import {fetchCustomer} from "./query.js";

function ready(fn) {
  if (document.readyState !== 'loading') {
    fn();
    return;
  }
  document.addEventListener('DOMContentLoaded', fn);
}

function handleCustomer(c) {
	return [c.id, c.firstName, c.lastName, `${c.address.street}, ${c.address.postalCode} ${c.address.city}`]
}

async function render() {
	const table = document.getElementById('customer-table');
	let customers = await fetchCustomer();
	let array = JSON.parse(customers).customerList;
	array.forEach(function(customer) {
		var tr = table.insertRow();
		handleCustomer(customer).forEach(function(column) {
			var td = tr.insertCell();
			td.innerText = column;
		});
	});
}

ready(render);