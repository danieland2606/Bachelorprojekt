// TODO: better import syntax?
import {BaseAPIRequestFactory, RequiredError, COLLECTION_FORMATS} from './baseapi.ts';
import {Configuration} from '../configuration.ts';
import {RequestContext, HttpMethod, ResponseContext, HttpFile} from '../http/http.ts';
import {ObjectSerializer} from '../models/ObjectSerializer.ts';
import {ApiException} from './exception.ts';
import {canConsumeForm, isCodeInRange} from '../util.ts';
import {SecurityAuthentication} from '../auth/auth.ts';


import { CustomerAllRequired } from '../models/CustomerAllRequired.ts';
import { CustomerPropertyNames } from '../models/CustomerPropertyNames.ts';
import { GetCustomerList200ResponseInner } from '../models/GetCustomerList200ResponseInner.ts';
import { ID } from '../models/ID.ts';

/**
 * no description
 */
export class CustomerApiRequestFactory extends BaseAPIRequestFactory {

    /**
     * create a new customer
     * @param customerAllRequired 
     */
    public async createCustomer(customerAllRequired: CustomerAllRequired, _options?: Configuration): Promise<RequestContext> {
        let _config = _options || this.configuration;

        // verify required parameter 'customerAllRequired' is not null or undefined
        if (customerAllRequired === null || customerAllRequired === undefined) {
            throw new RequiredError("CustomerApi", "createCustomer", "customerAllRequired");
        }


        // Path Params
        const localVarPath = '/customer';

        // Make Request Context
        const requestContext = _config.baseServer.makeRequestContext(localVarPath, HttpMethod.POST);
        requestContext.setHeaderParam("Accept", "application/json, */*;q=0.8")


        // Body Params
        const contentType = ObjectSerializer.getPreferredMediaType([
            "application/json"
        ]);
        requestContext.setHeaderParam("Content-Type", contentType);
        const serializedBody = ObjectSerializer.stringify(
            ObjectSerializer.serialize(customerAllRequired, "CustomerAllRequired", ""),
            contentType
        );
        requestContext.setBody(serializedBody);

        
        const defaultAuth: SecurityAuthentication | undefined = _options?.authMethods?.default || this.configuration?.authMethods?.default
        if (defaultAuth?.applySecurityAuthentication) {
            await defaultAuth?.applySecurityAuthentication(requestContext);
        }

        return requestContext;
    }

    /**
     * get a single customer
     * @param customerId 
     */
    public async getCustomer(customerId: number, _options?: Configuration): Promise<RequestContext> {
        let _config = _options || this.configuration;

        // verify required parameter 'customerId' is not null or undefined
        if (customerId === null || customerId === undefined) {
            throw new RequiredError("CustomerApi", "getCustomer", "customerId");
        }


        // Path Params
        const localVarPath = '/customer/{customerId}'
            .replace('{' + 'customerId' + '}', encodeURIComponent(String(customerId)));

        // Make Request Context
        const requestContext = _config.baseServer.makeRequestContext(localVarPath, HttpMethod.GET);
        requestContext.setHeaderParam("Accept", "application/json, */*;q=0.8")


        
        const defaultAuth: SecurityAuthentication | undefined = _options?.authMethods?.default || this.configuration?.authMethods?.default
        if (defaultAuth?.applySecurityAuthentication) {
            await defaultAuth?.applySecurityAuthentication(requestContext);
        }

        return requestContext;
    }

    /**
     * get a list of customers
     * @param fields A filter for which properties of Customer should be transmitted. If no fields are specified, only id is transmitted. Using address and one or more of its sub properties in the same query is a semantic error.
     */
    public async getCustomerList(fields?: Set<CustomerPropertyNames>, _options?: Configuration): Promise<RequestContext> {
        let _config = _options || this.configuration;


        // Path Params
        const localVarPath = '/customer';

        // Make Request Context
        const requestContext = _config.baseServer.makeRequestContext(localVarPath, HttpMethod.GET);
        requestContext.setHeaderParam("Accept", "application/json, */*;q=0.8")

        // Query Params
        if (fields !== undefined) {
            requestContext.setQueryParam("fields", ObjectSerializer.serialize(fields, "Set<CustomerPropertyNames>", ""));
        }


        
        const defaultAuth: SecurityAuthentication | undefined = _options?.authMethods?.default || this.configuration?.authMethods?.default
        if (defaultAuth?.applySecurityAuthentication) {
            await defaultAuth?.applySecurityAuthentication(requestContext);
        }

        return requestContext;
    }

    /**
     * replace a customer
     * @param customerId 
     * @param customerAllRequired 
     */
    public async updateCustomer(customerId: number, customerAllRequired: CustomerAllRequired, _options?: Configuration): Promise<RequestContext> {
        let _config = _options || this.configuration;

        // verify required parameter 'customerId' is not null or undefined
        if (customerId === null || customerId === undefined) {
            throw new RequiredError("CustomerApi", "updateCustomer", "customerId");
        }


        // verify required parameter 'customerAllRequired' is not null or undefined
        if (customerAllRequired === null || customerAllRequired === undefined) {
            throw new RequiredError("CustomerApi", "updateCustomer", "customerAllRequired");
        }


        // Path Params
        const localVarPath = '/customer/{customerId}'
            .replace('{' + 'customerId' + '}', encodeURIComponent(String(customerId)));

        // Make Request Context
        const requestContext = _config.baseServer.makeRequestContext(localVarPath, HttpMethod.PUT);
        requestContext.setHeaderParam("Accept", "application/json, */*;q=0.8")


        // Body Params
        const contentType = ObjectSerializer.getPreferredMediaType([
            "application/json"
        ]);
        requestContext.setHeaderParam("Content-Type", contentType);
        const serializedBody = ObjectSerializer.stringify(
            ObjectSerializer.serialize(customerAllRequired, "CustomerAllRequired", ""),
            contentType
        );
        requestContext.setBody(serializedBody);

        
        const defaultAuth: SecurityAuthentication | undefined = _options?.authMethods?.default || this.configuration?.authMethods?.default
        if (defaultAuth?.applySecurityAuthentication) {
            await defaultAuth?.applySecurityAuthentication(requestContext);
        }

        return requestContext;
    }

}

export class CustomerApiResponseProcessor {

    /**
     * Unwraps the actual response sent by the server from the response context and deserializes the response content
     * to the expected objects
     *
     * @params response Response returned by the server for a request to createCustomer
     * @throws ApiException if the response code was not in [200, 299]
     */
     public async createCustomer(response: ResponseContext): Promise<ID > {
        const contentType = ObjectSerializer.normalizeMediaType(response.headers["content-type"]);
        if (isCodeInRange("201", response.httpStatusCode)) {
            const body: ID = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "ID", ""
            ) as ID;
            return body;
        }
        if (isCodeInRange("400", response.httpStatusCode)) {
            const body: Error = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "Error", ""
            ) as Error;
            throw new ApiException<Error>(response.httpStatusCode, "invalid customer data", body, response.headers);
        }
        if (isCodeInRange("0", response.httpStatusCode)) {
            const body: { [key: string]: any; } = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "{ [key: string]: any; }", ""
            ) as { [key: string]: any; };
            throw new ApiException<{ [key: string]: any; }>(response.httpStatusCode, "unexpected error", body, response.headers);
        }

        // Work around for missing responses in specification, e.g. for petstore.yaml
        if (response.httpStatusCode >= 200 && response.httpStatusCode <= 299) {
            const body: ID = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "ID", ""
            ) as ID;
            return body;
        }

        throw new ApiException<string | Blob | undefined>(response.httpStatusCode, "Unknown API Status Code!", await response.getBodyAsAny(), response.headers);
    }

    /**
     * Unwraps the actual response sent by the server from the response context and deserializes the response content
     * to the expected objects
     *
     * @params response Response returned by the server for a request to getCustomer
     * @throws ApiException if the response code was not in [200, 299]
     */
     public async getCustomer(response: ResponseContext): Promise<CustomerAllRequired > {
        const contentType = ObjectSerializer.normalizeMediaType(response.headers["content-type"]);
        if (isCodeInRange("200", response.httpStatusCode)) {
            const body: CustomerAllRequired = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "CustomerAllRequired", ""
            ) as CustomerAllRequired;
            return body;
        }
        if (isCodeInRange("404", response.httpStatusCode)) {
            throw new ApiException<undefined>(response.httpStatusCode, "no customer at this location", undefined, response.headers);
        }
        if (isCodeInRange("0", response.httpStatusCode)) {
            const body: { [key: string]: any; } = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "{ [key: string]: any; }", ""
            ) as { [key: string]: any; };
            throw new ApiException<{ [key: string]: any; }>(response.httpStatusCode, "unexpected error", body, response.headers);
        }

        // Work around for missing responses in specification, e.g. for petstore.yaml
        if (response.httpStatusCode >= 200 && response.httpStatusCode <= 299) {
            const body: CustomerAllRequired = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "CustomerAllRequired", ""
            ) as CustomerAllRequired;
            return body;
        }

        throw new ApiException<string | Blob | undefined>(response.httpStatusCode, "Unknown API Status Code!", await response.getBodyAsAny(), response.headers);
    }

    /**
     * Unwraps the actual response sent by the server from the response context and deserializes the response content
     * to the expected objects
     *
     * @params response Response returned by the server for a request to getCustomerList
     * @throws ApiException if the response code was not in [200, 299]
     */
     public async getCustomerList(response: ResponseContext): Promise<Array<GetCustomerList200ResponseInner> > {
        const contentType = ObjectSerializer.normalizeMediaType(response.headers["content-type"]);
        if (isCodeInRange("200", response.httpStatusCode)) {
            const body: Array<GetCustomerList200ResponseInner> = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "Array<GetCustomerList200ResponseInner>", ""
            ) as Array<GetCustomerList200ResponseInner>;
            return body;
        }
        if (isCodeInRange("400", response.httpStatusCode)) {
            const body: Error = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "Error", ""
            ) as Error;
            throw new ApiException<Error>(response.httpStatusCode, "invalid fields parameter", body, response.headers);
        }
        if (isCodeInRange("0", response.httpStatusCode)) {
            const body: { [key: string]: any; } = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "{ [key: string]: any; }", ""
            ) as { [key: string]: any; };
            throw new ApiException<{ [key: string]: any; }>(response.httpStatusCode, "unexpected error", body, response.headers);
        }

        // Work around for missing responses in specification, e.g. for petstore.yaml
        if (response.httpStatusCode >= 200 && response.httpStatusCode <= 299) {
            const body: Array<GetCustomerList200ResponseInner> = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "Array<GetCustomerList200ResponseInner>", ""
            ) as Array<GetCustomerList200ResponseInner>;
            return body;
        }

        throw new ApiException<string | Blob | undefined>(response.httpStatusCode, "Unknown API Status Code!", await response.getBodyAsAny(), response.headers);
    }

    /**
     * Unwraps the actual response sent by the server from the response context and deserializes the response content
     * to the expected objects
     *
     * @params response Response returned by the server for a request to updateCustomer
     * @throws ApiException if the response code was not in [200, 299]
     */
     public async updateCustomer(response: ResponseContext): Promise<void > {
        const contentType = ObjectSerializer.normalizeMediaType(response.headers["content-type"]);
        if (isCodeInRange("200", response.httpStatusCode)) {
            return;
        }
        if (isCodeInRange("404", response.httpStatusCode)) {
            throw new ApiException<undefined>(response.httpStatusCode, "no customer at this location", undefined, response.headers);
        }
        if (isCodeInRange("400", response.httpStatusCode)) {
            const body: Error = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "Error", ""
            ) as Error;
            throw new ApiException<Error>(response.httpStatusCode, "invalid customer data", body, response.headers);
        }
        if (isCodeInRange("0", response.httpStatusCode)) {
            const body: { [key: string]: any; } = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "{ [key: string]: any; }", ""
            ) as { [key: string]: any; };
            throw new ApiException<{ [key: string]: any; }>(response.httpStatusCode, "unexpected error", body, response.headers);
        }

        // Work around for missing responses in specification, e.g. for petstore.yaml
        if (response.httpStatusCode >= 200 && response.httpStatusCode <= 299) {
            const body: void = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "void", ""
            ) as void;
            return body;
        }

        throw new ApiException<string | Blob | undefined>(response.httpStatusCode, "Unknown API Status Code!", await response.getBodyAsAny(), response.headers);
    }

}
