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
    const calcObject = {};
    calcObject.postalCode = data.postalCode;
    calcObject.coverage = data.policy.coverage;
    calcObject.race = data.policy.objectOfInsurance.race;
    calcObject.color = data.policy.objectOfInsurance.color;
    calcObject.dateOfBirth = data.policy.objectOfInsurance.dateOfBirth;
    calcObject.castrated = data.policy.objectOfInsurance.castrated;
    calcObject.personality = data.policy.objectOfInsurance.personality;
    calcObject.environment = data.policy.objectOfInsurance.environment;
    calcObject.weight = data.policy.objectOfInsurance.weight;
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

  async fetchPolicyNew({id, postalCode}) {
    const data = {};
    data.customerId = id;
    data.postalCode = postalCode;
    data.premium = new PremiumFetcher();
    return data;
  }

  createPolicy(customerId, policy) {
    postPolicy(customerId, policy);
    this.navigate('customer-details', { id: customerId });
  }

  createCustomer(customer) {
    postCustomer(customer);
    this.navigate('dashboard');
  }
}

window.app = new MyApp();