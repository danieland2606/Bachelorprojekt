import { fetchCustomerList, fetchCustomer, fetchPolicyList } from "./test_data/fetch.js";
import { Application } from "./framework/Application.js";

const customerFields = ['firstName', 'lastName', 'address'];
const policyFields = ['objectOfInsurance.name', 'startDate', 'endDate', 'coverage'];


export class MyApp extends Application {
  async fetchCustomerDetails(id) {
    const data = {};
    data.customer = await fetchCustomer(id);
    data.customer.id = id;
    data.policyList = await fetchPolicyList(policyFields);
    return data;
  }

  async fetchDashboard() {
    const data = {};
    data.customerList = await fetchCustomerList(customerFields);
    return data;
  }
}

window.app = new MyApp();