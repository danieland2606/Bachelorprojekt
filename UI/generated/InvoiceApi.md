# .InvoiceApi

All URIs are relative to *http://localhost:8081*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getInvoiceList**](InvoiceApi.md#getInvoiceList) | **GET** /customer/{customerId}/policy/{policyId}/invoice | returns all invoices belonging to selected policy


# **getInvoiceList**
> void | Array<Invoice> getInvoiceList()


### Example


```typescript
import {  } from '';
import * as fs from 'fs';

const configuration = .createConfiguration();
const apiInstance = new .InvoiceApi(configuration);

let body:.InvoiceApiGetInvoiceListRequest = {
  // number
  customerId: 1,
  // number
  policyId: 1,
};

apiInstance.getInvoiceList(body).then((data:any) => {
  console.log('API called successfully. Returned data: ' + data);
}).catch((error:any) => console.error(error));
```


### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **customerId** | [**number**] |  | defaults to undefined
 **policyId** | [**number**] |  | defaults to undefined


### Return type

**void | Array<Invoice>**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml, text/plain, text/html


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | success |  -  |
**204** | policy has no invoices |  -  |
**0** | unexpected error |  -  |

[[Back to top]](#) [[Back to API list]](README.md#documentation-for-api-endpoints) [[Back to Model list]](README.md#documentation-for-models) [[Back to README]](README.md)


