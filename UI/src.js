import { fetchCustomerList, fetchCustomer, fetchPolicyList } from "./fetch.js";
import { Application } from "./framework.js";

export class MyApp extends Application {
  async fetchCustomerDetails(id) {
    const data = {};
    console.log(`fetching Customer data for id: ${id}`);
    data.customer = await fetchCustomer();
    data.customer.id = id;
    data.policyList = await fetchPolicyList();
    return data;
  }

  async fetchDashboard() {
    const data = {};
    data.customerList = await fetchCustomerList();
    return data;
  }
}

window.app = new MyApp();