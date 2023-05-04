# .PolicyApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**calcPolicyPrice**](PolicyApi.md#calcPolicyPrice) | **POST** /policyprice | calculate policy price
[**createPolicy**](PolicyApi.md#createPolicy) | **POST** /customer/{customerId}/policy | create new policy
[**getPolicy**](PolicyApi.md#getPolicy) | **GET** /customer/{customerId}/policy/{policyId} | get a single policy
[**getPolicyList**](PolicyApi.md#getPolicyList) | **GET** /customer/{customerId}/policy | get a list of policies
[**updatePolicy**](PolicyApi.md#updatePolicy) | **PUT** /customer/{customerId}/policy/{policyId} | replace a policy


# **calcPolicyPrice**
> CalcPolicyPrice200Response calcPolicyPrice(policyCalc)


### Example


```typescript
import {  } from '';
import * as fs from 'fs';

const configuration = .createConfiguration();
const apiInstance = new .PolicyApi(configuration);

let body:.PolicyApiCalcPolicyPriceRequest = {
  // PolicyCalc
  policyCalc: {
    customerId: 1,
    policy: null,
  },
};

apiInstance.calcPolicyPrice(body).then((data:any) => {
  console.log('API called successfully. Returned data: ' + data);
}).catch((error:any) => console.error(error));
```


### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **policyCalc** | **PolicyCalc**|  |


### Return type

**CalcPolicyPrice200Response**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | price calculated |  -  |
**400** | invalid customerId or policy data |  -  |
**500** | unexpected error |  -  |

[[Back to top]](#) [[Back to API list]](README.md#documentation-for-api-endpoints) [[Back to Model list]](README.md#documentation-for-models) [[Back to README]](README.md)

# **createPolicy**
> ID createPolicy(policyAllRequired)


### Example


```typescript
import {  } from '';
import * as fs from 'fs';

const configuration = .createConfiguration();
const apiInstance = new .PolicyApi(configuration);

let body:.PolicyApiCreatePolicyRequest = {
  // number
  customerId: 1,
  // PolicyAllRequired
  policyAllRequired: null,
};

apiInstance.createPolicy(body).then((data:any) => {
  console.log('API called successfully. Returned data: ' + data);
}).catch((error:any) => console.error(error));
```


### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **policyAllRequired** | **PolicyAllRequired**|  |
 **customerId** | [**number**] |  | defaults to undefined


### Return type

**ID**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | policy created |  -  |
**400** | invalid policy data |  -  |
**500** | unexpected error |  -  |

[[Back to top]](#) [[Back to API list]](README.md#documentation-for-api-endpoints) [[Back to Model list]](README.md#documentation-for-models) [[Back to README]](README.md)

# **getPolicy**
> PolicyAllRequired getPolicy()


### Example


```typescript
import {  } from '';
import * as fs from 'fs';

const configuration = .createConfiguration();
const apiInstance = new .PolicyApi(configuration);

let body:.PolicyApiGetPolicyRequest = {
  // number
  customerId: 1,
  // number
  policyId: 1,
};

apiInstance.getPolicy(body).then((data:any) => {
  console.log('API called successfully. Returned data: ' + data);
}).catch((error:any) => console.error(error));
```


### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customerId** | [**number**] |  | defaults to undefined
 **policyId** | [**number**] |  | defaults to undefined


### Return type

**PolicyAllRequired**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | policy found |  -  |
**404** | no policy at this location |  -  |
**500** | unexpected error |  -  |

[[Back to top]](#) [[Back to API list]](README.md#documentation-for-api-endpoints) [[Back to Model list]](README.md#documentation-for-models) [[Back to README]](README.md)

# **getPolicyList**
> Array<GetPolicyList200ResponseInner> getPolicyList()


### Example


```typescript
import {  } from '';
import * as fs from 'fs';

const configuration = .createConfiguration();
const apiInstance = new .PolicyApi(configuration);

let body:.PolicyApiGetPolicyListRequest = {
  // number
  customerId: 1,
  // Set<PolicyPropertyNames> | A filter for which properties of Policy should be transmitted. If no fields are specified, only id is transmitted. Using objectOfInsurance and one or more of its sub properties in the same query is a semantic error. (optional)
  fields: [
    "startDate",
  ],
};

apiInstance.getPolicyList(body).then((data:any) => {
  console.log('API called successfully. Returned data: ' + data);
}).catch((error:any) => console.error(error));
```


### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customerId** | [**number**] |  | defaults to undefined
 **fields** | **Set&lt;PolicyPropertyNames&gt;** | A filter for which properties of Policy should be transmitted. If no fields are specified, only id is transmitted. Using objectOfInsurance and one or more of its sub properties in the same query is a semantic error. | (optional) defaults to undefined


### Return type

**Array<GetPolicyList200ResponseInner>**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | success |  -  |
**400** | invalid fields parameter |  -  |
**500** | unexpected error |  -  |

[[Back to top]](#) [[Back to API list]](README.md#documentation-for-api-endpoints) [[Back to Model list]](README.md#documentation-for-models) [[Back to README]](README.md)

# **updatePolicy**
> void updatePolicy(policyAllRequired)


### Example


```typescript
import {  } from '';
import * as fs from 'fs';

const configuration = .createConfiguration();
const apiInstance = new .PolicyApi(configuration);

let body:.PolicyApiUpdatePolicyRequest = {
  // number
  customerId: 1,
  // number
  policyId: 1,
  // PolicyAllRequired
  policyAllRequired: null,
};

apiInstance.updatePolicy(body).then((data:any) => {
  console.log('API called successfully. Returned data: ' + data);
}).catch((error:any) => console.error(error));
```


### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **policyAllRequired** | **PolicyAllRequired**|  |
 **customerId** | [**number**] |  | defaults to undefined
 **policyId** | [**number**] |  | defaults to undefined


### Return type

**void**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | policy updated |  -  |
**404** | no policy at this location |  -  |
**400** | invalid policy data |  -  |
**500** | unexpected error |  -  |

[[Back to top]](#) [[Back to API list]](README.md#documentation-for-api-endpoints) [[Back to Model list]](README.md#documentation-for-models) [[Back to README]](README.md)


