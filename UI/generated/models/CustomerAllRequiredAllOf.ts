/**
 * Meowmed+
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * OpenAPI spec version: 0.0.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { CustomerAllRequiredAllOfAddress } from '../models/CustomerAllRequiredAllOfAddress.ts';
import { HttpFile } from '../http/http.ts';

export class CustomerAllRequiredAllOf {
    'address': CustomerAllRequiredAllOfAddress = new CustomerAllRequiredAllOfAddress();

    static readonly discriminator: string | undefined = undefined;

    static readonly attributeTypeMap: Array<{name: string, baseName: string, type: string, format: string}> = [
        {
            "name": "address",
            "baseName": "address",
            "type": "CustomerAllRequiredAllOfAddress",
            "format": ""
        }    ];

    static getAttributeTypeMap() {
        return CustomerAllRequiredAllOf.attributeTypeMap;
    }

    public constructor() {
    }
}

