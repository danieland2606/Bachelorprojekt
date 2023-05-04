import { ResponseContext, RequestContext, HttpFile } from '../http/http.ts';
import { Configuration} from '../configuration.ts'

import { Address } from '../models/Address.ts';
import { CalcPolicyPrice200Response } from '../models/CalcPolicyPrice200Response.ts';
import { CatRace } from '../models/CatRace.ts';
import { Customer } from '../models/Customer.ts';
import { CustomerAllRequired } from '../models/CustomerAllRequired.ts';
import { CustomerAllRequiredAllOf } from '../models/CustomerAllRequiredAllOf.ts';
import { CustomerPropertyNames } from '../models/CustomerPropertyNames.ts';
import { EmploymentStatus } from '../models/EmploymentStatus.ts';
import { Environment } from '../models/Environment.ts';
import { FormOfAddress } from '../models/FormOfAddress.ts';
import { FurColor } from '../models/FurColor.ts';
import { GetCustomerList200ResponseInner } from '../models/GetCustomerList200ResponseInner.ts';
import { GetPolicyList200ResponseInner } from '../models/GetPolicyList200ResponseInner.ts';
import { ID } from '../models/ID.ts';
import { MaritalStatus } from '../models/MaritalStatus.ts';
import { ModelError } from '../models/ModelError.ts';
import { ObjectOfInsurance } from '../models/ObjectOfInsurance.ts';
import { Personality } from '../models/Personality.ts';
import { Policy } from '../models/Policy.ts';
import { PolicyAllRequired } from '../models/PolicyAllRequired.ts';
import { PolicyAllRequiredAllOf } from '../models/PolicyAllRequiredAllOf.ts';
import { PolicyCalc } from '../models/PolicyCalc.ts';
import { PolicyPropertyNames } from '../models/PolicyPropertyNames.ts';
import { Title } from '../models/Title.ts';
import { ObservableCustomerApi } from './ObservableAPI.ts';

import { CustomerApiRequestFactory, CustomerApiResponseProcessor} from "../apis/CustomerApi.ts";
export class PromiseCustomerApi {
    private api: ObservableCustomerApi

    public constructor(
        configuration: Configuration,
        requestFactory?: CustomerApiRequestFactory,
        responseProcessor?: CustomerApiResponseProcessor
    ) {
        this.api = new ObservableCustomerApi(configuration, requestFactory, responseProcessor);
    }

    /**
     * create a new customer
     * @param customerAllRequired 
     */
    public createCustomer(customerAllRequired: CustomerAllRequired, _options?: Configuration): Promise<ID> {
        const result = this.api.createCustomer(customerAllRequired, _options);
        return result.toPromise();
    }

    /**
     * get a single customer
     * @param customerId 
     */
    public getCustomer(customerId: number, _options?: Configuration): Promise<CustomerAllRequired> {
        const result = this.api.getCustomer(customerId, _options);
        return result.toPromise();
    }

    /**
     * get a list of customers
     * @param fields A filter for which properties of Customer should be transmitted. If no fields are specified, only id is transmitted. Using address and one or more of its sub properties in the same query is a semantic error.
     */
    public getCustomerList(fields?: Set<CustomerPropertyNames>, _options?: Configuration): Promise<Array<GetCustomerList200ResponseInner>> {
        const result = this.api.getCustomerList(fields, _options);
        return result.toPromise();
    }

    /**
     * replace a customer
     * @param customerId 
     * @param customerAllRequired 
     */
    public updateCustomer(customerId: number, customerAllRequired: CustomerAllRequired, _options?: Configuration): Promise<void> {
        const result = this.api.updateCustomer(customerId, customerAllRequired, _options);
        return result.toPromise();
    }


}



import { ObservablePolicyApi } from './ObservableAPI.ts';

import { PolicyApiRequestFactory, PolicyApiResponseProcessor} from "../apis/PolicyApi.ts";
export class PromisePolicyApi {
    private api: ObservablePolicyApi

    public constructor(
        configuration: Configuration,
        requestFactory?: PolicyApiRequestFactory,
        responseProcessor?: PolicyApiResponseProcessor
    ) {
        this.api = new ObservablePolicyApi(configuration, requestFactory, responseProcessor);
    }

    /**
     * calculate policy price
     * @param policyCalc 
     */
    public calcPolicyPrice(policyCalc: PolicyCalc, _options?: Configuration): Promise<CalcPolicyPrice200Response> {
        const result = this.api.calcPolicyPrice(policyCalc, _options);
        return result.toPromise();
    }

    /**
     * create new policy
     * @param customerId 
     * @param policyAllRequired 
     */
    public createPolicy(customerId: number, policyAllRequired: PolicyAllRequired, _options?: Configuration): Promise<ID> {
        const result = this.api.createPolicy(customerId, policyAllRequired, _options);
        return result.toPromise();
    }

    /**
     * get a single policy
     * @param customerId 
     * @param policyId 
     */
    public getPolicy(customerId: number, policyId: number, _options?: Configuration): Promise<PolicyAllRequired> {
        const result = this.api.getPolicy(customerId, policyId, _options);
        return result.toPromise();
    }

    /**
     * get a list of policies
     * @param customerId 
     * @param fields A filter for which properties of Policy should be transmitted. If no fields are specified, only id is transmitted. Using objectOfInsurance and one or more of its sub properties in the same query is a semantic error.
     */
    public getPolicyList(customerId: number, fields?: Set<PolicyPropertyNames>, _options?: Configuration): Promise<Array<GetPolicyList200ResponseInner>> {
        const result = this.api.getPolicyList(customerId, fields, _options);
        return result.toPromise();
    }

    /**
     * replace a policy
     * @param customerId 
     * @param policyId 
     * @param policyAllRequired 
     */
    public updatePolicy(customerId: number, policyId: number, policyAllRequired: PolicyAllRequired, _options?: Configuration): Promise<void> {
        const result = this.api.updatePolicy(customerId, policyId, policyAllRequired, _options);
        return result.toPromise();
    }


}



