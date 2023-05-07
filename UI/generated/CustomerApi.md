# .CustomerApi

All URIs are relative to *http://localhost:8081*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createCustomer**](CustomerApi.md#createCustomer) | **POST** /customer | create a new customer
[**getCustomer**](CustomerApi.md#getCustomer) | **GET** /customer/{customerId} | get a single customer
[**getCustomerList**](CustomerApi.md#getCustomerList) | **GET** /customer | get a list of customers


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
 - **Accept**: application/json, application/xml, text/plain, text/html


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | customer created |  -  |
**400** | invalid customer data |  -  |
**0** | unexpected error |  -  |

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
 - **Accept**: application/json, application/xml, text/plain, text/html


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | customer found |  -  |
**404** | no customer at this location |  -  |
**0** | unexpected error |  -  |

[[Back to top]](#) [[Back to API list]](README.md#documentation-for-api-endpoints) [[Back to Model list]](README.md#documentation-for-models) [[Back to README]](README.md)

# **getCustomerList**
> void | Array<GetCustomerList200ResponseInner> getCustomerList()


### Example


```typescript
import {  } from '';
import * as fs from 'fs';

const configuration = .createConfiguration();
const apiInstance = new .CustomerApi(configuration);

let body:.CustomerApiGetCustomerListRequest = {
  // Set<CustomerPropertyNames> | A filter for which properties of Customer should be transmitted. If no  fields are specified, only id is transmitted. The value 'address'  indicates that the entire Address object should be transmitted. Using  'address' and one or more of its sub properties in the same query is a  semantic error. (optional)
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
 **fields** | **Set&lt;CustomerPropertyNames&gt;** | A filter for which properties of Customer should be transmitted. If no  fields are specified, only id is transmitted. The value &#39;address&#39;  indicates that the entire Address object should be transmitted. Using  &#39;address&#39; and one or more of its sub properties in the same query is a  semantic error. | (optional) defaults to undefined


### Return type

**void | Array<GetCustomerList200ResponseInner>**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml, text/plain, text/html


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | success |  -  |
**204** | no customers |  -  |
**400** | invalid fields parameter |  -  |
**0** | unexpected error |  -  |

[[Back to top]](#) [[Back to API list]](README.md#documentation-for-api-endpoints) [[Back to Model list]](README.md#documentation-for-models) [[Back to README]](README.md)


