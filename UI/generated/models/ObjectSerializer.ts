export * from '../models/Address.ts';
export * from '../models/CalcPolicyPrice200Response.ts';
export * from '../models/CatRace.ts';
export * from '../models/Customer.ts';
export * from '../models/CustomerAllRequired.ts';
export * from '../models/CustomerAllRequiredAllOf.ts';
export * from '../models/CustomerPropertyNames.ts';
export * from '../models/EmploymentStatus.ts';
export * from '../models/Environment.ts';
export * from '../models/FormOfAddress.ts';
export * from '../models/FurColor.ts';
export * from '../models/GetCustomerList200ResponseInner.ts';
export * from '../models/GetPolicyList200ResponseInner.ts';
export * from '../models/ID.ts';
export * from '../models/MaritalStatus.ts';
export * from '../models/ModelError.ts';
export * from '../models/ObjectOfInsurance.ts';
export * from '../models/Personality.ts';
export * from '../models/Policy.ts';
export * from '../models/PolicyAllRequired.ts';
export * from '../models/PolicyAllRequiredAllOf.ts';
export * from '../models/PolicyCalc.ts';
export * from '../models/PolicyPropertyNames.ts';
export * from '../models/Title.ts';

import { Address } from '../models/Address.ts';
import { CalcPolicyPrice200Response } from '../models/CalcPolicyPrice200Response.ts';
import { CatRace } from '../models/CatRace.ts';
import { Customer              } from '../models/Customer.ts';
import { CustomerAllRequired              } from '../models/CustomerAllRequired.ts';
import { CustomerAllRequiredAllOf } from '../models/CustomerAllRequiredAllOf.ts';
import { CustomerPropertyNames } from '../models/CustomerPropertyNames.ts';
import { EmploymentStatus } from '../models/EmploymentStatus.ts';
import { Environment } from '../models/Environment.ts';
import { FormOfAddress } from '../models/FormOfAddress.ts';
import { FurColor } from '../models/FurColor.ts';
import { GetCustomerList200ResponseInner              } from '../models/GetCustomerList200ResponseInner.ts';
import { GetPolicyList200ResponseInner } from '../models/GetPolicyList200ResponseInner.ts';
import { ID } from '../models/ID.ts';
import { MaritalStatus } from '../models/MaritalStatus.ts';
import { ModelError } from '../models/ModelError.ts';
import { ObjectOfInsurance         } from '../models/ObjectOfInsurance.ts';
import { Personality } from '../models/Personality.ts';
import { Policy } from '../models/Policy.ts';
import { PolicyAllRequired } from '../models/PolicyAllRequired.ts';
import { PolicyAllRequiredAllOf } from '../models/PolicyAllRequiredAllOf.ts';
import { PolicyCalc } from '../models/PolicyCalc.ts';
import { PolicyPropertyNames } from '../models/PolicyPropertyNames.ts';
import { Title } from '../models/Title.ts';

/* tslint:disable:no-unused-variable */
let primitives = [
                    "string",
                    "boolean",
                    "double",
                    "integer",
                    "long",
                    "float",
                    "number",
                    "any"
                 ];

const supportedMediaTypes: { [mediaType: string]: number } = {
  "application/json": Infinity,
  "application/octet-stream": 0,
  "application/x-www-form-urlencoded": 0
}


let enumsMap: Set<string> = new Set<string>([
    "CatRace",
    "CustomerPropertyNames",
    "EmploymentStatus",
    "Environment",
    "FormOfAddress",
    "FurColor",
    "MaritalStatus",
    "Personality",
    "PolicyPropertyNames",
    "Title",
]);

let typeMap: {[index: string]: any} = {
    "Address": Address,
    "CalcPolicyPrice200Response": CalcPolicyPrice200Response,
    "Customer": Customer,
    "CustomerAllRequired": CustomerAllRequired,
    "CustomerAllRequiredAllOf": CustomerAllRequiredAllOf,
    "GetCustomerList200ResponseInner": GetCustomerList200ResponseInner,
    "GetPolicyList200ResponseInner": GetPolicyList200ResponseInner,
    "ID": ID,
    "ModelError": ModelError,
    "ObjectOfInsurance": ObjectOfInsurance,
    "Policy": Policy,
    "PolicyAllRequired": PolicyAllRequired,
    "PolicyAllRequiredAllOf": PolicyAllRequiredAllOf,
    "PolicyCalc": PolicyCalc,
}

export class ObjectSerializer {
    public static findCorrectType(data: any, expectedType: string) {
        if (data == undefined) {
            return expectedType;
        } else if (primitives.indexOf(expectedType.toLowerCase()) !== -1) {
            return expectedType;
        } else if (expectedType === "Date") {
            return expectedType;
        } else {
            if (enumsMap.has(expectedType)) {
                return expectedType;
            }

            if (!typeMap[expectedType]) {
                return expectedType; // w/e we don't know the type
            }

            // Check the discriminator
            let discriminatorProperty = typeMap[expectedType].discriminator;
            if (discriminatorProperty == null) {
                return expectedType; // the type does not have a discriminator. use it.
            } else {
                if (data[discriminatorProperty]) {
                    var discriminatorType = data[discriminatorProperty];
                    if(typeMap[discriminatorType]){
                        return discriminatorType; // use the type given in the discriminator
                    } else {
                        return expectedType; // discriminator did not map to a type
                    }
                } else {
                    return expectedType; // discriminator was not present (or an empty string)
                }
            }
        }
    }

    public static serialize(data: any, type: string, format: string) {
        if (data == undefined) {
            return data;
        } else if (primitives.indexOf(type.toLowerCase()) !== -1) {
            return data;
        } else if (type.startsWith("Array<")) {
            let subType: string = type.replace("Array<", ""); // Array<Type> => Type>
            subType = subType.substring(0, subType.length - 1); // Type> => Type
            let transformedData: any[] = [];
            for (let date of data) {
                transformedData.push(ObjectSerializer.serialize(date, subType, format));
            }
            return transformedData.join(",");
        } else if (type.startsWith("Set<")) { 
            let subType: string = type.replace("Set<", ""); // Set<Type> => Type>
            subType = subType.substring(0, subType.length - 1); // Type> => Type
            let transformedData: any[] = [];
            for (let date of data) {
                const serializedDate = ObjectSerializer.serialize(date, subType, format);
                if (!transformedData.includes(serializedDate)) {
                    transformedData.push(serializedDate);
                }
            }
            return transformedData.join(",");
        } else if (type === "Date") {
            if (format == "date") {
                let month = data.getMonth()+1
                month = month < 10 ? "0" + month.toString() : month.toString()
                let day = data.getDate();
                day = day < 10 ? "0" + day.toString() : day.toString();

                return data.getFullYear() + "-" + month + "-" + day;
            } else {
                return data.toISOString();
            }
        } else {
            if (enumsMap.has(type)) {
                return data;
            }
            if (!typeMap[type]) { // in case we dont know the type
                return data;
            }

            // Get the actual type of this object
            type = this.findCorrectType(data, type);

            // get the map for the correct type.
            let attributeTypes = typeMap[type].getAttributeTypeMap();
            let instance: {[index: string]: any} = {};
            for (let attributeType of attributeTypes) {
                instance[attributeType.baseName] = ObjectSerializer.serialize(data[attributeType.name], attributeType.type, attributeType.format);
            }
            return instance;
        }
    }

    public static deserialize(data: any, type: string, format: string) {
        // polymorphism may change the actual type.
        type = ObjectSerializer.findCorrectType(data, type);
        if (data == undefined) {
            return data;
        } else if (primitives.indexOf(type.toLowerCase()) !== -1) {
            return data;
        } else if (type.lastIndexOf("Array<", 0) === 0) { // string.startsWith pre es6
            let subType: string = type.replace("Array<", ""); // Array<Type> => Type>
            subType = subType.substring(0, subType.length - 1); // Type> => Type
            let transformedData: any[] = [];
            for (let date of data) {
                transformedData.push(ObjectSerializer.deserialize(date, subType, format));
            }
            return transformedData;
        } else if (type === "Date") {
            return new Date(data);
        } else {
            if (enumsMap.has(type)) {// is Enum
                return data;
            }

            if (!typeMap[type]) { // dont know the type
                return data;
            }
            let instance = new typeMap[type]();
            let attributeTypes = typeMap[type].getAttributeTypeMap();
            for (let attributeType of attributeTypes) {
                let value = ObjectSerializer.deserialize(data[attributeType.baseName], attributeType.type, attributeType.format);
                if (value !== undefined) {
                    instance[attributeType.name] = value;
                }
            }
            return instance;
        }
    }


    /**
     * Normalize media type
     *
     * We currently do not handle any media types attributes, i.e. anything
     * after a semicolon. All content is assumed to be UTF-8 compatible.
     */
    public static normalizeMediaType(mediaType: string | undefined): string | undefined {
        if (mediaType === undefined) {
            return undefined;
        }
        return mediaType.split(";")[0].trim().toLowerCase();
    }

    /**
     * From a list of possible media types, choose the one we can handle best.
     *
     * The order of the given media types does not have any impact on the choice
     * made.
     */
    public static getPreferredMediaType(mediaTypes: Array<string>): string {
        /** According to OAS 3 we should default to json */
        if (!mediaTypes) {
            return "application/json";
        }

        const normalMediaTypes = mediaTypes.map(this.normalizeMediaType);
        let selectedMediaType: string | undefined = undefined;
        let selectedRank: number = -Infinity;
        for (const mediaType of normalMediaTypes) {
            if (supportedMediaTypes[mediaType!] > selectedRank) {
                selectedMediaType = mediaType;
                selectedRank = supportedMediaTypes[mediaType!];
            }
        }

        if (selectedMediaType === undefined) {
            throw new Error("None of the given media types are supported: " + mediaTypes.join(", "));
        }

        return selectedMediaType!;
    }

    /**
     * Convert data to a string according the given media type
     */
    public static stringify(data: any, mediaType: string): string {
        if (mediaType === "text/plain") {
            return String(data);
        }

        if (mediaType === "application/json") {
            return JSON.stringify(data);
        }

        throw new Error("The mediaType " + mediaType + " is not supported by ObjectSerializer.stringify.");
    }

    /**
     * Parse data from a string according to the given media type
     */
    public static parse(rawData: string, mediaType: string | undefined) {
        if (mediaType === undefined) {
            throw new Error("Cannot parse content. No Content-Type defined.");
        }

        if (mediaType === "text/plain") {
            return rawData;
        }

        if (mediaType === "application/json") {
            return JSON.parse(rawData);
        }

        if (mediaType === "text/html") {
            return rawData;
        }

        throw new Error("The mediaType " + mediaType + " is not supported by ObjectSerializer.parse.");
    }
}
