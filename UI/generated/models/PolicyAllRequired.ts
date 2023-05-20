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

export class PolicyAllRequired {
    'id'?: number;
    'startDate': string = new Date('1970-01-01').toISOString().split('T')[0];;
    'endDate': string = new Date('1970-01-01').toISOString().split('T')[0];;
    'coverage': number = 3.14;
    'premium': number;
    'active': boolean;
    'objectOfInsurance': PolicyAllRequiredAllOfObjectOfInsurance = new PolicyAllRequiredAllOfObjectOfInsurance();

    static readonly discriminator: string | undefined = undefined;

    static readonly attributeTypeMap: Array<{name: string, baseName: string, type: string, format: string}> = [
        {
            "name": "id",
            "baseName": "id",
            "type": "number",
            "format": ""
        },
        {
            "name": "startDate",
            "baseName": "startDate",
            "type": "string",
            "format": "date"
        },
        {
            "name": "endDate",
            "baseName": "endDate",
            "type": "string",
            "format": "date"
        },
        {
            "name": "coverage",
            "baseName": "coverage",
            "type": "number",
            "format": ""
        },
        {
            "name": "premium",
            "baseName": "premium",
            "type": "number",
            "format": ""
        },
        {
            "name": "active",
            "baseName": "active",
            "type": "boolean",
            "format": ""
        },
        {
            "name": "objectOfInsurance",
            "baseName": "objectOfInsurance",
            "type": "PolicyAllRequiredAllOfObjectOfInsurance",
            "format": ""
        }    ];

    static getAttributeTypeMap() {
        return PolicyAllRequired.attributeTypeMap;
    }

    public constructor() {
    }
}

