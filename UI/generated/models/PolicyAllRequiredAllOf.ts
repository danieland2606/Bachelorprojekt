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

import { PolicyAllRequiredAllOfObjectOfInsurance } from '../models/PolicyAllRequiredAllOfObjectOfInsurance.ts';
import { HttpFile } from '../http/http.ts';

export class PolicyAllRequiredAllOf {
    'objectOfInsurance': PolicyAllRequiredAllOfObjectOfInsurance = new PolicyAllRequiredAllOfObjectOfInsurance();

    static readonly discriminator: string | undefined = undefined;

    static readonly attributeTypeMap: Array<{name: string, baseName: string, type: string, format: string}> = [
        {
            "name": "objectOfInsurance",
            "baseName": "objectOfInsurance",
            "type": "PolicyAllRequiredAllOfObjectOfInsurance",
            "format": ""
        }    ];

    static getAttributeTypeMap() {
        return PolicyAllRequiredAllOf.attributeTypeMap;
    }

    public constructor() {
    }
}

