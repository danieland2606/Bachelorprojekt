import { ResponseContext, RequestContext, HttpFile } from '../http/http.ts';
import { Configuration} from '../configuration.ts'

import { Address } from '../models/Address.ts';
import { CalcPolicyPrice200Response } from '../models/CalcPolicyPrice200Response.ts';
import { CatRace } from '../models/CatRace.ts';
import { Customer } from '../models/Customer.ts';
import { CustomerAllRequired } from '../models/CustomerAllRequired.ts';
import { CustomerAllRequiredAllOf } from '../models/CustomerAllRequiredAllOf.ts';
import { CustomerAllRequiredAllOfAddress } from '../models/CustomerAllRequiredAllOfAddress.ts';
import { CustomerPropertyNames } from '../models/CustomerPropertyNames.ts';
import { EmploymentStatus } from '../models/EmploymentStatus.ts';
import { Environment } from '../models/Environment.ts';
import { FormOfAddress } from '../models/FormOfAddress.ts';
import { FurColor } from '../models/FurColor.ts';
import { GetCustomerList200ResponseInner } from '../models/GetCustomerList200ResponseInner.ts';
import { GetPolicyList200ResponseInner } from '../models/GetPolicyList200ResponseInner.ts';
import { ID } from '../models/ID.ts';
import { Invoice } from '../models/Invoice.ts';
import { MaritalStatus } from '../models/MaritalStatus.ts';
import { ModelError } from '../models/ModelError.ts';
import { ObjectOfInsurance } from '../models/ObjectOfInsurance.ts';
import { Personality } from '../models/Personality.ts';
import { Policy } from '../models/Policy.ts';
import { PolicyAllRequired } from '../models/PolicyAllRequired.ts';
import { PolicyAllRequiredAllOf } from '../models/PolicyAllRequiredAllOf.ts';
import { PolicyAllRequiredAllOfObjectOfInsurance } from '../models/PolicyAllRequiredAllOfObjectOfInsurance.ts';
import { PolicyCalc } from '../models/PolicyCalc.ts';
import { PolicyPropertyNames } from '../models/PolicyPropertyNames.ts';
import { Title } from '../models/Title.ts';

import { ObservableCustomerApi } from "./ObservableAPI.ts";
import { CustomerApiRequestFactory, CustomerApiResponseProcessor} from "../apis/CustomerApi.ts";

export interface CustomerApiCreateCustomerRequest {
    /**
     * 
     * @type CustomerAllRequired
     * @memberof CustomerApicreateCustomer
     */
    customerAllRequired: CustomerAllRequired
}

export interface CustomerApiGetCustomerRequest {
    /**
     * 
     * @type number
     * @memberof CustomerApigetCustomer
     */
    customerId: number
}

export interface CustomerApiGetCustomerListRequest {
    /**
     * A filter for which properties of Customer should be transmitted. If no  fields are specified, only id is transmitted. The value &#39;address&#39;  indicates that the entire Address object should be transmitted. Using  &#39;address&#39; and one or more of its sub properties in the same query is a  semantic error.
     * @type Set&lt;CustomerPropertyNames&gt;
     * @memberof CustomerApigetCustomerList
     */
    fields?: Set<CustomerPropertyNames>
}

export interface CustomerApiUpdateCustomerRequest {
    /**
     * 
     * @type number
     * @memberof CustomerApiupdateCustomer
     */
    customerId: number
    /**
     * 
     * @type CustomerAllRequired
     * @memberof CustomerApiupdateCustomer
     */
    customerAllRequired: CustomerAllRequired
}

export class ObjectCustomerApi {
    private api: ObservableCustomerApi

    public constructor(configuration: Configuration, requestFactory?: CustomerApiRequestFactory, responseProcessor?: CustomerApiResponseProcessor) {
        this.api = new ObservableCustomerApi(configuration, requestFactory, responseProcessor);
    }

    /**
     * create a new customer
     * @param param the request object
     */
    public createCustomer(param: CustomerApiCreateCustomerRequest, options?: Configuration): Promise<ID> {
        return this.api.createCustomer(param.customerAllRequired,  options).toPromise();
    }

    /**
     * get a single customer
     * @param param the request object
     */
    public getCustomer(param: CustomerApiGetCustomerRequest, options?: Configuration): Promise<CustomerAllRequired> {
        return this.api.getCustomer(param.customerId,  options).toPromise();
    }

    /**
     * get a list of customers
     * @param param the request object
     */
    public getCustomerList(param: CustomerApiGetCustomerListRequest = {}, options?: Configuration): Promise<void | Array<GetCustomerList200ResponseInner>> {
        return this.api.getCustomerList(param.fields,  options).toPromise();
    }

    /**
     * replace a customer
     * @param param the request object
     */
    public updateCustomer(param: CustomerApiUpdateCustomerRequest, options?: Configuration): Promise<void> {
        return this.api.updateCustomer(param.customerId, param.customerAllRequired,  options).toPromise();
    }

}

import { ObservableInvoiceApi } from "./ObservableAPI.ts";
import { InvoiceApiRequestFactory, InvoiceApiResponseProcessor} from "../apis/InvoiceApi.ts";

export interface InvoiceApiGetInvoiceListRequest {
    /**
     * 
     * @type number
     * @memberof InvoiceApigetInvoiceList
     */
    customerId: number
    /**
     * 
     * @type number
     * @memberof InvoiceApigetInvoiceList
     */
    policyId: number
}

export class ObjectInvoiceApi {
    private api: ObservableInvoiceApi

    public constructor(configuration: Configuration, requestFactory?: InvoiceApiRequestFactory, responseProcessor?: InvoiceApiResponseProcessor) {
        this.api = new ObservableInvoiceApi(configuration, requestFactory, responseProcessor);
    }

    /**
     * returns all invoices belonging to selected policy
     * @param param the request object
     */
    public getInvoiceList(param: InvoiceApiGetInvoiceListRequest, options?: Configuration): Promise<void | Array<Invoice>> {
        return this.api.getInvoiceList(param.customerId, param.policyId,  options).toPromise();
    }

}

import { ObservablePolicyApi } from "./ObservableAPI.ts";
import { PolicyApiRequestFactory, PolicyApiResponseProcessor} from "../apis/PolicyApi.ts";

export interface PolicyApiCalcPolicyPriceRequest {
    /**
     * 
     * @type PolicyCalc
     * @memberof PolicyApicalcPolicyPrice
     */
    policyCalc: PolicyCalc
}

export interface PolicyApiCreatePolicyRequest {
    /**
     * 
     * @type number
     * @memberof PolicyApicreatePolicy
     */
    customerId: number
    /**
     * 
     * @type PolicyAllRequired
     * @memberof PolicyApicreatePolicy
     */
    policyAllRequired: PolicyAllRequired
}

export interface PolicyApiGetPolicyRequest {
    /**
     * 
     * @type number
     * @memberof PolicyApigetPolicy
     */
    customerId: number
    /**
     * 
     * @type number
     * @memberof PolicyApigetPolicy
     */
    policyId: number
}

export interface PolicyApiGetPolicyListRequest {
    /**
     * 
     * @type number
     * @memberof PolicyApigetPolicyList
     */
    customerId: number
    /**
     * A filter for which properties of Policy should be transmitted. If no fields are specified, only id is transmitted. The value  &#39;objectOfInsurance&#39; indicates that the entire ObjectOfInsurance object  should be transmitted. Using &#39;objectOfInsurance&#39; and one or more of its  sub properties in the same query is a semantic error.
     * @type Set&lt;PolicyPropertyNames&gt;
     * @memberof PolicyApigetPolicyList
     */
    fields?: Set<PolicyPropertyNames>
}

export interface PolicyApiUpdatePolicyRequest {
    /**
     * 
     * @type number
     * @memberof PolicyApiupdatePolicy
     */
    customerId: number
    /**
     * 
     * @type number
     * @memberof PolicyApiupdatePolicy
     */
    policyId: number
    /**
     * 
     * @type PolicyAllRequired
     * @memberof PolicyApiupdatePolicy
     */
    policyAllRequired: PolicyAllRequired
}

export class ObjectPolicyApi {
    private api: ObservablePolicyApi

    public constructor(configuration: Configuration, requestFactory?: PolicyApiRequestFactory, responseProcessor?: PolicyApiResponseProcessor) {
        this.api = new ObservablePolicyApi(configuration, requestFactory, responseProcessor);
    }

    /**
     * calculate policy price
     * @param param the request object
     */
    public calcPolicyPrice(param: PolicyApiCalcPolicyPriceRequest, options?: Configuration): Promise<CalcPolicyPrice200Response> {
        return this.api.calcPolicyPrice(param.policyCalc,  options).toPromise();
    }

    /**
     * create new policy
     * @param param the request object
     */
    public createPolicy(param: PolicyApiCreatePolicyRequest, options?: Configuration): Promise<ID> {
        return this.api.createPolicy(param.customerId, param.policyAllRequired,  options).toPromise();
    }

    /**
     * get a single policy
     * @param param the request object
     */
    public getPolicy(param: PolicyApiGetPolicyRequest, options?: Configuration): Promise<PolicyAllRequired> {
        return this.api.getPolicy(param.customerId, param.policyId,  options).toPromise();
    }

    /**
     * get a list of policies
     * @param param the request object
     */
    public getPolicyList(param: PolicyApiGetPolicyListRequest, options?: Configuration): Promise<Array<GetPolicyList200ResponseInner> | void> {
        return this.api.getPolicyList(param.customerId, param.fields,  options).toPromise();
    }

    /**
     * replace a policy
     * @param param the request object
     */
    public updatePolicy(param: PolicyApiUpdatePolicyRequest, options?: Configuration): Promise<void> {
        return this.api.updatePolicy(param.customerId, param.policyId, param.policyAllRequired,  options).toPromise();
    }

}
