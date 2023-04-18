export function formatAddress(address) {
  return `${address.street}, ${address.postalCode} ${address.city}`;
}

function compareId(a, b) {
  return a.id - b.id;
}

export const customerLabels = ["ID", "Vorname", "Nachname", "Adresse"];

export function formatCustomerList(customerList) {
  return {
    headers: customerLabels,
    items: customerList?.sort(compareId)?.map(
      (customer) => ({
        item: [
          customer.id,
          customer.firstName,
          customer.lastName,
          formatAddress(customer.address),
        ],
        actions: {
          details: `/customer/${customer.id}`,
          edit: "",
          delete: "",
        },
      }),
    ),
  };
}
