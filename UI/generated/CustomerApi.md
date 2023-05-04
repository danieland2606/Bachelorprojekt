# .CustomerApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createCustomer**](CustomerApi.md#createCustomer) | **POST** /customer | create a new customer
[**getCustomer**](CustomerApi.md#getCustomer) | **GET** /customer/{customerId} | get a single customer
[**getCustomerList**](CustomerApi.md#getCustomerList) | **GET** /customer | get a list of customers
[**updateCustomer**](CustomerApi.md#updateCustomer) | **PUT** /customer/{customerId} | replace a customer


# **createCustomer**
> ID createCustomer(customerAllRequired)


### Example


```typescript
import {  } from '';
import * as fs from 'fs';

const configuration = .createConfiguration();
const apiInstance = new .CustomerApi(configuration);

let body:.CustomerApiCreateCustomerRequest = {
  // CustomerAllRequired
  customerAllRequired: null,
};

apiInstance.createCustomer(body).then((data:any) => {
  console.log('API called successfully. Returned data: ' + data);
}).catch((error:any) => console.error(error));
```


### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customerAllRequired** | **CustomerAllRequired**|  |


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
**201** | customer created |  -  |
**400** | invalid customer data |  -  |
**500** | unexpected error |  -  |

[[Back to top]](#) [[Back to API list]](README.md#documentation-for-api-endpoints) [[Back to Model list]](README.md#documentation-for-models) [[Back to README]](README.md)

# **getCustomer**
> CustomerAllRequired getCustomer()


### Example


```typescript
import {  } from '';
import * as fs from 'fs';

const configuration = .createConfiguration();
const apiInstance = new .CustomerApi(configuration);

let body:.CustomerApiGetCustomerRequest = {
  // number
  customerId: 1,
};

apiInstance.getCustomer(body).then((data:any) => {
  console.log('API called successfully. Returned data: ' + data);
}).catch((error:any) => console.error(error));
```


### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customerId** | [**number**] |  | defaults to undefined


### Return type

**CustomerAllRequired**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | customer found |  -  |
**404** | no customer at this location |  -  |
**500** | unexpected error |  -  |

[[Back to top]](#) [[Back to API list]](README.md#documentation-for-api-endpoints) [[Back to Model list]](README.md#documentation-for-models) [[Back to README]](README.md)

# **getCustomerList**
> Array<GetCustomerList200ResponseInner> getCustomerList()


### Example


```typescript
import {  } from '';
import * as fs from 'fs';

const configuration = .createConfiguration();
const apiInstance = new .CustomerApi(configuration);

let body:.CustomerApiGetCustomerListRequest = {
  // Set<CustomerPropertyNames> | A filter for which properties of Customer should be transmitted. If no fields are specified, only id is transmitted. Using address and one or more of its sub properties in the same query is a semantic error. (optional)
  fields: [
    "firstName",
  ],
};

apiInstance.getCustomerList(body).then((data:any) => {
  console.log('API called successfully. Returned data: ' + data);
}).catch((error:any) => console.error(error));
```


### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **fields** | **Set&lt;CustomerPropertyNames&gt;** | A filter for which properties of Customer should be transmitted. If no fields are specified, only id is transmitted. Using address and one or more of its sub properties in the same query is a semantic error. | (optional) defaults to undefined


### Return type

**Array<GetCustomerList200ResponseInner>**

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

# **updateCustomer**
> void updateCustomer(customerAllRequired)


### Example


```typescript
import {  } from '';
import * as fs from 'fs';

const configuration = .createConfiguration();
const apiInstance = new .CustomerApi(configuration);

let body:.CustomerApiUpdateCustomerRequest = {
  // number
  customerId: 1,
  // CustomerAllRequired
  customerAllRequired: null,
};

apiInstance.updateCustomer(body).then((data:any) => {
  console.log('API called successfully. Returned data: ' + data);
}).catch((error:any) => console.error(error));
```


### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customerAllRequired** | **CustomerAllRequired**|  |
 **customerId** | [**number**] |  | defaults to undefined


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
**200** | customer updated |  -  |
**404** | no customer at this location |  -  |
**400** | invalid customer data |  -  |
**500** | unexpected error |  -  |

[[Back to top]](#) [[Back to API list]](README.md#documentation-for-api-endpoints) [[Back to Model list]](README.md#documentation-for-models) [[Back to README]](README.md)


