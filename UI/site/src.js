import { getCustomerList, getCustomer, getPolicyList } from "./fetch.js";
import { Application } from "./framework/Application.js";

const customerFields = ['firstName', 'lastName', 'address'];
const policyFields = ['objectOfInsurance.name', 'startDate', 'endDate', 'coverage'];


export class MyApp extends Application {
  async fetchCustomerDetails(id) {
    const data = {};
    data.customer = await getCustomer(id);
    data.customer.id = id;
    data.policyList = await getPolicyList(id, { fields: policyFields });
    return data;
  }

  async fetchDashboard() {
    const data = {};
    data.customerList = await getCustomerList({ fields: customerFields });
    return data;
  }

  async fetchPolicyNew() {
    const data = {};
    class PremiumFetcher {
      #value = 0;
      get value() {
        return this.#value;
      }

      bind(input) {
        input.value = this.#value;
        this.subscribe(() => input.value = this.#value);
        input.oninput = () => this.#value = input.value;
      }

      async update() {

      }
    }
    data.premium = new PremiumFetcher();
    return data;
  }
}

window.app = new MyApp();