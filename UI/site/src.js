import { getCustomerList, getCustomer, getPolicyList, getPolicy, postCustomer, postPolicy, getPremium } from "./fetch.js";
import { Application } from "./framework/Application.js";
import { Observable } from "./framework/Observable.js";

const customerFields = ['firstName', 'lastName', 'address'];
const policyFields = ['objectOfInsurance.name', 'startDate', 'endDate', 'coverage'];

class PremiumFetcher extends Observable {
  constructor() {
    super(0);
  }

  async update(data) {
    const calcObject = {customerId: data.customerId, policy: data.policy};
    this.value = await getPremium(calcObject);
  }
}

export class MyApp extends Application {
  async fetchCustomerDetails({id}) {
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

  async fetchPolicyNew({id}) {
    const data = {};
    data.customerId = id;
    data.premium = new PremiumFetcher();
    return data;
  }

  async createPolicy(customerId, policy) {
    this.page.querySelector('#create-policy').setAttribute('disabled','');
    await postPolicy(customerId, policy);
    this.navigate('customer-details', { id: customerId });
  }

  async createCustomer(customer) {
    this.page.querySelector('#create-customer').setAttribute('disabled','');
    await postCustomer(customer);
    this.navigate('dashboard');
  }
}

//const test = ['load', 'unload', 'beforeunload', 'popstate', 'hashchange'];
//test.forEach(t => window.addEventListener(t, _ => console.log(t)));

window.app = new MyApp();