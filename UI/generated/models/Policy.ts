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

import { ObjectOfInsurance } from '../models/ObjectOfInsurance.ts';
import { HttpFile } from '../http/http.ts';

export class Policy {
    'id'?: number;
    'startDate'?: string;
    'endDate'?: string;
    'dueDate'?: string;
    'coverage'?: number;
    'premium'?: number;
    'active'?: boolean;
    'objectOfInsurance'?: ObjectOfInsurance;

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
            "name": "dueDate",
            "baseName": "dueDate",
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
            "type": "ObjectOfInsurance",
            "format": ""
        }    ];

    static getAttributeTypeMap() {
        return Policy.attributeTypeMap;
    }

    public constructor() {
    }
}

