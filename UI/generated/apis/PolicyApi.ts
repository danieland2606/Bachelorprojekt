// TODO: better import syntax?
import {BaseAPIRequestFactory, RequiredError, COLLECTION_FORMATS} from './baseapi.ts';
import {Configuration} from '../configuration.ts';
import {RequestContext, HttpMethod, ResponseContext, HttpFile} from '../http/http.ts';
import {ObjectSerializer} from '../models/ObjectSerializer.ts';
import {ApiException} from './exception.ts';
import {canConsumeForm, isCodeInRange} from '../util.ts';
import {SecurityAuthentication} from '../auth/auth.ts';


import { CalcPolicyPrice200Response } from '../models/CalcPolicyPrice200Response.ts';
import { GetPolicyList200ResponseInner } from '../models/GetPolicyList200ResponseInner.ts';
import { ID } from '../models/ID.ts';
import { PolicyAllRequired } from '../models/PolicyAllRequired.ts';
import { PolicyCalc } from '../models/PolicyCalc.ts';
import { PolicyPropertyNames } from '../models/PolicyPropertyNames.ts';

/**
 * no description
 */
export class PolicyApiRequestFactory extends BaseAPIRequestFactory {

    /**
     * calculate policy price
     * @param policyCalc 
     */
    public async calcPolicyPrice(policyCalc: PolicyCalc, _options?: Configuration): Promise<RequestContext> {
        let _config = _options || this.configuration;

        // verify required parameter 'policyCalc' is not null or undefined
        if (policyCalc === null || policyCalc === undefined) {
            throw new RequiredError("PolicyApi", "calcPolicyPrice", "policyCalc");
        }


        // Path Params
        const localVarPath = '/policyprice';

        // Make Request Context
        const requestContext = _config.baseServer.makeRequestContext(localVarPath, HttpMethod.POST);
        requestContext.setHeaderParam("Accept", "application/json, */*;q=0.8")


        // Body Params
        const contentType = ObjectSerializer.getPreferredMediaType([
            "application/json"
        ]);
        requestContext.setHeaderParam("Content-Type", contentType);
        const serializedBody = ObjectSerializer.stringify(
            ObjectSerializer.serialize(policyCalc, "PolicyCalc", ""),
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
     * create new policy
     * @param customerId 
     * @param policyAllRequired 
     */
    public async createPolicy(customerId: number, policyAllRequired: PolicyAllRequired, _options?: Configuration): Promise<RequestContext> {
        let _config = _options || this.configuration;

        // verify required parameter 'customerId' is not null or undefined
        if (customerId === null || customerId === undefined) {
            throw new RequiredError("PolicyApi", "createPolicy", "customerId");
        }


        // verify required parameter 'policyAllRequired' is not null or undefined
        if (policyAllRequired === null || policyAllRequired === undefined) {
            throw new RequiredError("PolicyApi", "createPolicy", "policyAllRequired");
        }


        // Path Params
        const localVarPath = '/customer/{customerId}/policy'
            .replace('{' + 'customerId' + '}', encodeURIComponent(String(customerId)));

        // Make Request Context
        const requestContext = _config.baseServer.makeRequestContext(localVarPath, HttpMethod.POST);
        requestContext.setHeaderParam("Accept", "application/json, */*;q=0.8")


        // Body Params
        const contentType = ObjectSerializer.getPreferredMediaType([
            "application/json"
        ]);
        requestContext.setHeaderParam("Content-Type", contentType);
        const serializedBody = ObjectSerializer.stringify(
            ObjectSerializer.serialize(policyAllRequired, "PolicyAllRequired", ""),
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
     * get a single policy
     * @param customerId 
     * @param policyId 
     */
    public async getPolicy(customerId: number, policyId: number, _options?: Configuration): Promise<RequestContext> {
        let _config = _options || this.configuration;

        // verify required parameter 'customerId' is not null or undefined
        if (customerId === null || customerId === undefined) {
            throw new RequiredError("PolicyApi", "getPolicy", "customerId");
        }


        // verify required parameter 'policyId' is not null or undefined
        if (policyId === null || policyId === undefined) {
            throw new RequiredError("PolicyApi", "getPolicy", "policyId");
        }


        // Path Params
        const localVarPath = '/customer/{customerId}/policy/{policyId}'
            .replace('{' + 'customerId' + '}', encodeURIComponent(String(customerId)))
            .replace('{' + 'policyId' + '}', encodeURIComponent(String(policyId)));

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
     * get a list of policies
     * @param customerId 
     * @param fields A filter for which properties of Policy should be transmitted. If no fields are specified, only id is transmitted. Using objectOfInsurance and one or more of its sub properties in the same query is a semantic error.
     */
    public async getPolicyList(customerId: number, fields?: Set<PolicyPropertyNames>, _options?: Configuration): Promise<RequestContext> {
        let _config = _options || this.configuration;

        // verify required parameter 'customerId' is not null or undefined
        if (customerId === null || customerId === undefined) {
            throw new RequiredError("PolicyApi", "getPolicyList", "customerId");
        }



        // Path Params
        const localVarPath = '/customer/{customerId}/policy'
            .replace('{' + 'customerId' + '}', encodeURIComponent(String(customerId)));

        // Make Request Context
        const requestContext = _config.baseServer.makeRequestContext(localVarPath, HttpMethod.GET);
        requestContext.setHeaderParam("Accept", "application/json, */*;q=0.8")

        // Query Params
        if (fields !== undefined) {
            requestContext.setQueryParam("fields", ObjectSerializer.serialize(fields, "Set<PolicyPropertyNames>", ""));
        }


        
        const defaultAuth: SecurityAuthentication | undefined = _options?.authMethods?.default || this.configuration?.authMethods?.default
        if (defaultAuth?.applySecurityAuthentication) {
            await defaultAuth?.applySecurityAuthentication(requestContext);
        }

        return requestContext;
    }

    /**
     * replace a policy
     * @param customerId 
     * @param policyId 
     * @param policyAllRequired 
     */
    public async updatePolicy(customerId: number, policyId: number, policyAllRequired: PolicyAllRequired, _options?: Configuration): Promise<RequestContext> {
        let _config = _options || this.configuration;

        // verify required parameter 'customerId' is not null or undefined
        if (customerId === null || customerId === undefined) {
            throw new RequiredError("PolicyApi", "updatePolicy", "customerId");
        }


        // verify required parameter 'policyId' is not null or undefined
        if (policyId === null || policyId === undefined) {
            throw new RequiredError("PolicyApi", "updatePolicy", "policyId");
        }


        // verify required parameter 'policyAllRequired' is not null or undefined
        if (policyAllRequired === null || policyAllRequired === undefined) {
            throw new RequiredError("PolicyApi", "updatePolicy", "policyAllRequired");
        }


        // Path Params
        const localVarPath = '/customer/{customerId}/policy/{policyId}'
            .replace('{' + 'customerId' + '}', encodeURIComponent(String(customerId)))
            .replace('{' + 'policyId' + '}', encodeURIComponent(String(policyId)));

        // Make Request Context
        const requestContext = _config.baseServer.makeRequestContext(localVarPath, HttpMethod.PUT);
        requestContext.setHeaderParam("Accept", "application/json, */*;q=0.8")


        // Body Params
        const contentType = ObjectSerializer.getPreferredMediaType([
            "application/json"
        ]);
        requestContext.setHeaderParam("Content-Type", contentType);
        const serializedBody = ObjectSerializer.stringify(
            ObjectSerializer.serialize(policyAllRequired, "PolicyAllRequired", ""),
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

export class PolicyApiResponseProcessor {

    /**
     * Unwraps the actual response sent by the server from the response context and deserializes the response content
     * to the expected objects
     *
     * @params response Response returned by the server for a request to calcPolicyPrice
     * @throws ApiException if the response code was not in [200, 299]
     */
     public async calcPolicyPrice(response: ResponseContext): Promise<CalcPolicyPrice200Response > {
        const contentType = ObjectSerializer.normalizeMediaType(response.headers["content-type"]);
        if (isCodeInRange("200", response.httpStatusCode)) {
            const body: CalcPolicyPrice200Response = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "CalcPolicyPrice200Response", ""
            ) as CalcPolicyPrice200Response;
            return body;
        }
        if (isCodeInRange("400", response.httpStatusCode)) {
            const body: Error = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "Error", ""
            ) as Error;
            throw new ApiException<Error>(response.httpStatusCode, "invalid customerId or policy data", body, response.headers);
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
            const body: CalcPolicyPrice200Response = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "CalcPolicyPrice200Response", ""
            ) as CalcPolicyPrice200Response;
            return body;
        }

        throw new ApiException<string | Blob | undefined>(response.httpStatusCode, "Unknown API Status Code!", await response.getBodyAsAny(), response.headers);
    }

    /**
     * Unwraps the actual response sent by the server from the response context and deserializes the response content
     * to the expected objects
     *
     * @params response Response returned by the server for a request to createPolicy
     * @throws ApiException if the response code was not in [200, 299]
     */
     public async createPolicy(response: ResponseContext): Promise<ID > {
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
            throw new ApiException<Error>(response.httpStatusCode, "invalid policy data", body, response.headers);
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
     * @params response Response returned by the server for a request to getPolicy
     * @throws ApiException if the response code was not in [200, 299]
     */
     public async getPolicy(response: ResponseContext): Promise<PolicyAllRequired > {
        const contentType = ObjectSerializer.normalizeMediaType(response.headers["content-type"]);
        if (isCodeInRange("200", response.httpStatusCode)) {
            const body: PolicyAllRequired = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "PolicyAllRequired", ""
            ) as PolicyAllRequired;
            return body;
        }
        if (isCodeInRange("404", response.httpStatusCode)) {
            throw new ApiException<undefined>(response.httpStatusCode, "no policy at this location", undefined, response.headers);
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
            const body: PolicyAllRequired = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "PolicyAllRequired", ""
            ) as PolicyAllRequired;
            return body;
        }

        throw new ApiException<string | Blob | undefined>(response.httpStatusCode, "Unknown API Status Code!", await response.getBodyAsAny(), response.headers);
    }

    /**
     * Unwraps the actual response sent by the server from the response context and deserializes the response content
     * to the expected objects
     *
     * @params response Response returned by the server for a request to getPolicyList
     * @throws ApiException if the response code was not in [200, 299]
     */
     public async getPolicyList(response: ResponseContext): Promise<Array<GetPolicyList200ResponseInner> > {
        const contentType = ObjectSerializer.normalizeMediaType(response.headers["content-type"]);
        if (isCodeInRange("200", response.httpStatusCode)) {
            const body: Array<GetPolicyList200ResponseInner> = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "Array<GetPolicyList200ResponseInner>", ""
            ) as Array<GetPolicyList200ResponseInner>;
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
            const body: Array<GetPolicyList200ResponseInner> = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "Array<GetPolicyList200ResponseInner>", ""
            ) as Array<GetPolicyList200ResponseInner>;
            return body;
        }

        throw new ApiException<string | Blob | undefined>(response.httpStatusCode, "Unknown API Status Code!", await response.getBodyAsAny(), response.headers);
    }

    /**
     * Unwraps the actual response sent by the server from the response context and deserializes the response content
     * to the expected objects
     *
     * @params response Response returned by the server for a request to updatePolicy
     * @throws ApiException if the response code was not in [200, 299]
     */
     public async updatePolicy(response: ResponseContext): Promise<void > {
        const contentType = ObjectSerializer.normalizeMediaType(response.headers["content-type"]);
        if (isCodeInRange("200", response.httpStatusCode)) {
            return;
        }
        if (isCodeInRange("404", response.httpStatusCode)) {
            throw new ApiException<undefined>(response.httpStatusCode, "no policy at this location", undefined, response.headers);
        }
        if (isCodeInRange("400", response.httpStatusCode)) {
            const body: Error = ObjectSerializer.deserialize(
                ObjectSerializer.parse(await response.body.text(), contentType),
                "Error", ""
            ) as Error;
            throw new ApiException<Error>(response.httpStatusCode, "invalid policy data", body, response.headers);
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
