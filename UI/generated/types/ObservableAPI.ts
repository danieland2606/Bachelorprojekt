import { ResponseContext, RequestContext, HttpFile } from '../http/http.ts';
import { Configuration} from '../configuration.ts'
import { Observable, of, from } from '../rxjsStub.ts';
import {mergeMap, map} from  '../rxjsStub.ts';
import { Address } from '../models/Address.ts';
import { CalcPolicyPrice200Response } from '../models/CalcPolicyPrice200Response.ts';
import { CatRace } from '../models/CatRace.ts';
import { CreateCustomer201Response } from '../models/CreateCustomer201Response.ts';
import { Customer } from '../models/Customer.ts';
import { CustomerAllRequired } from '../models/CustomerAllRequired.ts';
import { CustomerAllRequiredAllOf } from '../models/CustomerAllRequiredAllOf.ts';
import { CustomerPropertyNames } from '../models/CustomerPropertyNames.ts';
import { EmploymentStatus } from '../models/EmploymentStatus.ts';
import { Environment } from '../models/Environment.ts';
import { FormOfAddress } from '../models/FormOfAddress.ts';
import { FurColor } from '../models/FurColor.ts';
import { GetCustomerList200ResponseInner } from '../models/GetCustomerList200ResponseInner.ts';
import { GetCustomerList400Response } from '../models/GetCustomerList400Response.ts';
import { GetPolicyList200ResponseInner } from '../models/GetPolicyList200ResponseInner.ts';
import { MaritalStatus } from '../models/MaritalStatus.ts';
import { ObjectOfInsurance } from '../models/ObjectOfInsurance.ts';
import { Personality } from '../models/Personality.ts';
import { Policy } from '../models/Policy.ts';
import { PolicyAllRequired } from '../models/PolicyAllRequired.ts';
import { PolicyAllRequiredAllOf } from '../models/PolicyAllRequiredAllOf.ts';
import { PolicyCalc } from '../models/PolicyCalc.ts';
import { PolicyPropertyNames } from '../models/PolicyPropertyNames.ts';
import { Title } from '../models/Title.ts';

import { CustomerApiRequestFactory, CustomerApiResponseProcessor} from "../apis/CustomerApi.ts";
export class ObservableCustomerApi {
    private requestFactory: CustomerApiRequestFactory;
    private responseProcessor: CustomerApiResponseProcessor;
    private configuration: Configuration;

    public constructor(
        configuration: Configuration,
        requestFactory?: CustomerApiRequestFactory,
        responseProcessor?: CustomerApiResponseProcessor
    ) {
        this.configuration = configuration;
        this.requestFactory = requestFactory || new CustomerApiRequestFactory(configuration);
        this.responseProcessor = responseProcessor || new CustomerApiResponseProcessor();
    }

    /**
     * create a new customer
     * @param customerAllRequired 
     */
    public createCustomer(customerAllRequired: CustomerAllRequired, _options?: Configuration): Observable<CreateCustomer201Response> {
        const requestContextPromise = this.requestFactory.createCustomer(customerAllRequired, _options);

        // build promise chain
        let middlewarePreObservable = from<RequestContext>(requestContextPromise);
        for (let middleware of this.configuration.middleware) {
            middlewarePreObservable = middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => middleware.pre(ctx)));
        }

        return middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => this.configuration.httpApi.send(ctx))).
            pipe(mergeMap((response: ResponseContext) => {
                let middlewarePostObservable = of(response);
                for (let middleware of this.configuration.middleware) {
                    middlewarePostObservable = middlewarePostObservable.pipe(mergeMap((rsp: ResponseContext) => middleware.post(rsp)));
                }
                return middlewarePostObservable.pipe(map((rsp: ResponseContext) => this.responseProcessor.createCustomer(rsp)));
            }));
    }

    /**
     * get a single customer
     * @param customerId 
     */
    public getCustomer(customerId: number, _options?: Configuration): Observable<CustomerAllRequired> {
        const requestContextPromise = this.requestFactory.getCustomer(customerId, _options);

        // build promise chain
        let middlewarePreObservable = from<RequestContext>(requestContextPromise);
        for (let middleware of this.configuration.middleware) {
            middlewarePreObservable = middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => middleware.pre(ctx)));
        }

        return middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => this.configuration.httpApi.send(ctx))).
            pipe(mergeMap((response: ResponseContext) => {
                let middlewarePostObservable = of(response);
                for (let middleware of this.configuration.middleware) {
                    middlewarePostObservable = middlewarePostObservable.pipe(mergeMap((rsp: ResponseContext) => middleware.post(rsp)));
                }
                return middlewarePostObservable.pipe(map((rsp: ResponseContext) => this.responseProcessor.getCustomer(rsp)));
            }));
    }

    /**
     * get a list of customers
     * @param fields A filter for which properties of Customer should be transmitted. If no fields are specified, only id is transmitted.
     */
    public getCustomerList(fields?: Set<CustomerPropertyNames>, _options?: Configuration): Observable<Array<GetCustomerList200ResponseInner>> {
        const requestContextPromise = this.requestFactory.getCustomerList(fields, _options);

        // build promise chain
        let middlewarePreObservable = from<RequestContext>(requestContextPromise);
        for (let middleware of this.configuration.middleware) {
            middlewarePreObservable = middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => middleware.pre(ctx)));
        }

        return middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => this.configuration.httpApi.send(ctx))).
            pipe(mergeMap((response: ResponseContext) => {
                let middlewarePostObservable = of(response);
                for (let middleware of this.configuration.middleware) {
                    middlewarePostObservable = middlewarePostObservable.pipe(mergeMap((rsp: ResponseContext) => middleware.post(rsp)));
                }
                return middlewarePostObservable.pipe(map((rsp: ResponseContext) => this.responseProcessor.getCustomerList(rsp)));
            }));
    }

    /**
     * replace a customer
     * @param customerId 
     * @param customerAllRequired 
     */
    public updateCustomer(customerId: number, customerAllRequired: CustomerAllRequired, _options?: Configuration): Observable<void> {
        const requestContextPromise = this.requestFactory.updateCustomer(customerId, customerAllRequired, _options);

        // build promise chain
        let middlewarePreObservable = from<RequestContext>(requestContextPromise);
        for (let middleware of this.configuration.middleware) {
            middlewarePreObservable = middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => middleware.pre(ctx)));
        }

        return middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => this.configuration.httpApi.send(ctx))).
            pipe(mergeMap((response: ResponseContext) => {
                let middlewarePostObservable = of(response);
                for (let middleware of this.configuration.middleware) {
                    middlewarePostObservable = middlewarePostObservable.pipe(mergeMap((rsp: ResponseContext) => middleware.post(rsp)));
                }
                return middlewarePostObservable.pipe(map((rsp: ResponseContext) => this.responseProcessor.updateCustomer(rsp)));
            }));
    }

}

import { PolicyApiRequestFactory, PolicyApiResponseProcessor} from "../apis/PolicyApi.ts";
export class ObservablePolicyApi {
    private requestFactory: PolicyApiRequestFactory;
    private responseProcessor: PolicyApiResponseProcessor;
    private configuration: Configuration;

    public constructor(
        configuration: Configuration,
        requestFactory?: PolicyApiRequestFactory,
        responseProcessor?: PolicyApiResponseProcessor
    ) {
        this.configuration = configuration;
        this.requestFactory = requestFactory || new PolicyApiRequestFactory(configuration);
        this.responseProcessor = responseProcessor || new PolicyApiResponseProcessor();
    }

    /**
     * calculate policy price
     * @param policyCalc 
     */
    public calcPolicyPrice(policyCalc: PolicyCalc, _options?: Configuration): Observable<CalcPolicyPrice200Response> {
        const requestContextPromise = this.requestFactory.calcPolicyPrice(policyCalc, _options);

        // build promise chain
        let middlewarePreObservable = from<RequestContext>(requestContextPromise);
        for (let middleware of this.configuration.middleware) {
            middlewarePreObservable = middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => middleware.pre(ctx)));
        }

        return middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => this.configuration.httpApi.send(ctx))).
            pipe(mergeMap((response: ResponseContext) => {
                let middlewarePostObservable = of(response);
                for (let middleware of this.configuration.middleware) {
                    middlewarePostObservable = middlewarePostObservable.pipe(mergeMap((rsp: ResponseContext) => middleware.post(rsp)));
                }
                return middlewarePostObservable.pipe(map((rsp: ResponseContext) => this.responseProcessor.calcPolicyPrice(rsp)));
            }));
    }

    /**
     * create new policy
     * @param customerId 
     * @param policyAllRequired 
     */
    public createPolicy(customerId: number, policyAllRequired: PolicyAllRequired, _options?: Configuration): Observable<CreateCustomer201Response> {
        const requestContextPromise = this.requestFactory.createPolicy(customerId, policyAllRequired, _options);

        // build promise chain
        let middlewarePreObservable = from<RequestContext>(requestContextPromise);
        for (let middleware of this.configuration.middleware) {
            middlewarePreObservable = middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => middleware.pre(ctx)));
        }

        return middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => this.configuration.httpApi.send(ctx))).
            pipe(mergeMap((response: ResponseContext) => {
                let middlewarePostObservable = of(response);
                for (let middleware of this.configuration.middleware) {
                    middlewarePostObservable = middlewarePostObservable.pipe(mergeMap((rsp: ResponseContext) => middleware.post(rsp)));
                }
                return middlewarePostObservable.pipe(map((rsp: ResponseContext) => this.responseProcessor.createPolicy(rsp)));
            }));
    }

    /**
     * get a single policy
     * @param customerId 
     * @param policyId 
     */
    public getPolicy(customerId: number, policyId: number, _options?: Configuration): Observable<PolicyAllRequired> {
        const requestContextPromise = this.requestFactory.getPolicy(customerId, policyId, _options);

        // build promise chain
        let middlewarePreObservable = from<RequestContext>(requestContextPromise);
        for (let middleware of this.configuration.middleware) {
            middlewarePreObservable = middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => middleware.pre(ctx)));
        }

        return middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => this.configuration.httpApi.send(ctx))).
            pipe(mergeMap((response: ResponseContext) => {
                let middlewarePostObservable = of(response);
                for (let middleware of this.configuration.middleware) {
                    middlewarePostObservable = middlewarePostObservable.pipe(mergeMap((rsp: ResponseContext) => middleware.post(rsp)));
                }
                return middlewarePostObservable.pipe(map((rsp: ResponseContext) => this.responseProcessor.getPolicy(rsp)));
            }));
    }

    /**
     * get a list of policies
     * @param customerId 
     * @param fields A filter for which properties of Policy should be transmitted. If no fields are specified, only id is transmitted.
     */
    public getPolicyList(customerId: number, fields?: Set<PolicyPropertyNames>, _options?: Configuration): Observable<Array<GetPolicyList200ResponseInner>> {
        const requestContextPromise = this.requestFactory.getPolicyList(customerId, fields, _options);

        // build promise chain
        let middlewarePreObservable = from<RequestContext>(requestContextPromise);
        for (let middleware of this.configuration.middleware) {
            middlewarePreObservable = middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => middleware.pre(ctx)));
        }

        return middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => this.configuration.httpApi.send(ctx))).
            pipe(mergeMap((response: ResponseContext) => {
                let middlewarePostObservable = of(response);
                for (let middleware of this.configuration.middleware) {
                    middlewarePostObservable = middlewarePostObservable.pipe(mergeMap((rsp: ResponseContext) => middleware.post(rsp)));
                }
                return middlewarePostObservable.pipe(map((rsp: ResponseContext) => this.responseProcessor.getPolicyList(rsp)));
            }));
    }

    /**
     * replace a policy
     * @param customerId 
     * @param policyId 
     * @param policyAllRequired 
     */
    public updatePolicy(customerId: number, policyId: number, policyAllRequired: PolicyAllRequired, _options?: Configuration): Observable<void> {
        const requestContextPromise = this.requestFactory.updatePolicy(customerId, policyId, policyAllRequired, _options);

        // build promise chain
        let middlewarePreObservable = from<RequestContext>(requestContextPromise);
        for (let middleware of this.configuration.middleware) {
            middlewarePreObservable = middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => middleware.pre(ctx)));
        }

        return middlewarePreObservable.pipe(mergeMap((ctx: RequestContext) => this.configuration.httpApi.send(ctx))).
            pipe(mergeMap((response: ResponseContext) => {
                let middlewarePostObservable = of(response);
                for (let middleware of this.configuration.middleware) {
                    middlewarePostObservable = middlewarePostObservable.pipe(mergeMap((rsp: ResponseContext) => middleware.post(rsp)));
                }
                return middlewarePostObservable.pipe(map((rsp: ResponseContext) => this.responseProcessor.updatePolicy(rsp)));
            }));
    }

}
