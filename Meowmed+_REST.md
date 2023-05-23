<!-- Generator: Widdershins v4.0.1 -->

<h1 id="meowmed-">Meowmed+ v0.0.1</h1>

> Scroll down for code samples, example requests and responses.

Base URLs:

* <a href="http://localhost:8081">http://localhost:8081</a>

* <a href="http://localhost:8082">http://localhost:8082</a>

* <a href="http://localhost:8071">http://localhost:8071</a>

* <a href="http://localhost:8072">http://localhost:8072</a>

<h1 id="meowmed--customer">customer</h1>

## getCustomerList

<a id="opIdgetCustomerList"></a>

> Code samples

<details>
<summary>http</summary>

```http
GET /customer HTTP/1.1
Accept: application/json
Host: localhost:8081

```

</details>

<details>
<summary>shell</summary>

```shell
curl --request GET \
  --url http://localhost:8081/customer \
  --header 'Accept: application/json'
```

</details>

<details>
<summary>javascript</summary>

```javascript
fetch("http://localhost:8081/customer", {
  "method": "GET",
  "headers": {
    "Accept": "application/json"
  }
})
.then(response => {
  console.log(response);
})
.catch(err => {
  console.error(err);
});
```

</details>

`GET /customer`

*get a list of customers*

<h3 id="getcustomerlist-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|fields|query|array[string]|false|A filter for which properties of Customer should be transmitted. If no  fields are specified, only id is transmitted. The value 'address'  indicates that the entire Address object should be transmitted. Using  'address' and one or more of its sub properties in the same query is a  semantic error.|

#### Enumerated Values

|Parameter|Value|
|---|---|
|fields|firstName|
|fields|lastName|
|fields|formOfAddress|
|fields|title|
|fields|maritalStatus|
|fields|dateOfBirth|
|fields|employmentStatus|
|fields|dogOwner|
|fields|phoneNumber|
|fields|email|
|fields|bankDetails|
|fields|address|
|fields|address.city|
|fields|address.street|
|fields|address.postalCode|

> Example responses

> 200 Response

```json
[
  {
    "id": 0,
    "firstName": "string",
    "lastName": "string",
    "formOfAddress": "herr",
    "title": "none",
    "maritalStatus": "ledig",
    "dateOfBirth": "2019-08-24",
    "employmentStatus": "selbststaendig",
    "dogOwner": true,
    "address": {
      "city": "string",
      "street": "string",
      "postalCode": "string"
    },
    "phoneNumber": "string",
    "email": "user@example.com",
    "bankDetails": "string"
  }
]
```

> default Response

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Error/>
```

```
"string"
```

<h3 id="getcustomerlist-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|success|Inline|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|no customers|None|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|invalid fields parameter|[Error](#schemaerror)|
|default|Default|unexpected error|string|

<h3 id="getcustomerlist-responseschema">Response Schema</h3>

#### Enumerated Values

|Property|Value|
|---|---|
|formOfAddress|herr|
|formOfAddress|frau|
|title|none|
|title|doctor|
|title|professor|
|maritalStatus|ledig|
|maritalStatus|verheiratet|
|maritalStatus|verwitwet|
|employmentStatus|selbststaendig|
|employmentStatus|angestellt|
|employmentStatus|arbeitslos|
|employmentStatus|arbeitssuchend|
|employmentStatus|ausbildung|

#### Links

**get** => <a href="#opIdgetCustomer">getCustomer</a>

|Parameter|Expression|
|---|---|
|customerId|$response.body#/id|

**put** => <a href="#opIdupdateCustomer">updateCustomer</a>

|Parameter|Expression|
|---|---|
|customerId|$response.body#/id|

<aside class="success">
This operation does not require authentication
</aside>

## createCustomer

<a id="opIdcreateCustomer"></a>

> Code samples

<details>
<summary>http</summary>

```http
POST /customer HTTP/1.1
Content-Type: application/json
Accept: application/json
Host: localhost:8081
Content-Length: 324

{"firstName":"string","lastName":"string","formOfAddress":"herr","title":"none","maritalStatus":"ledig","dateOfBirth":"2019-08-24","employmentStatus":"selbststaendig","dogOwner":true,"address":{"city":"string","street":"string","postalCode":"string"},"phoneNumber":"string","email":"user@example.com","bankDetails":"string"}
```

</details>

<details>
<summary>shell</summary>

```shell
curl --request POST \
  --url http://localhost:8081/customer \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json' \
  --data '{"firstName":"string","lastName":"string","formOfAddress":"herr","title":"none","maritalStatus":"ledig","dateOfBirth":"2019-08-24","employmentStatus":"selbststaendig","dogOwner":true,"address":{"city":"string","street":"string","postalCode":"string"},"phoneNumber":"string","email":"user@example.com","bankDetails":"string"}'
```

</details>

<details>
<summary>javascript</summary>

```javascript
fetch("http://localhost:8081/customer", {
  "method": "POST",
  "headers": {
    "Content-Type": "application/json",
    "Accept": "application/json"
  },
  "body": "{\"firstName\":\"string\",\"lastName\":\"string\",\"formOfAddress\":\"herr\",\"title\":\"none\",\"maritalStatus\":\"ledig\",\"dateOfBirth\":\"2019-08-24\",\"employmentStatus\":\"selbststaendig\",\"dogOwner\":true,\"address\":{\"city\":\"string\",\"street\":\"string\",\"postalCode\":\"string\"},\"phoneNumber\":\"string\",\"email\":\"user@example.com\",\"bankDetails\":\"string\"}"
})
.then(response => {
  console.log(response);
})
.catch(err => {
  console.error(err);
});
```

</details>

`POST /customer`

*create a new customer*

> Body parameter

```json
{
  "firstName": "string",
  "lastName": "string",
  "formOfAddress": "herr",
  "title": "none",
  "maritalStatus": "ledig",
  "dateOfBirth": "2019-08-24",
  "employmentStatus": "selbststaendig",
  "dogOwner": true,
  "address": {
    "city": "string",
    "street": "string",
    "postalCode": "string"
  },
  "phoneNumber": "string",
  "email": "user@example.com",
  "bankDetails": "string"
}
```

<h3 id="createcustomer-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CustomerAllRequired](#schemacustomerallrequired)|true|none|

> Example responses

> 201 Response

```json
{
  "id": 0
}
```

> default Response

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Error/>
```

```
"string"
```

<h3 id="createcustomer-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|customer created|[ID](#schemaid)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|invalid customer data|[Error](#schemaerror)|
|default|Default|unexpected error|string|

<aside class="success">
This operation does not require authentication
</aside>

## getCustomer

<a id="opIdgetCustomer"></a>

> Code samples

<details>
<summary>http</summary>

```http
GET /customer/0 HTTP/1.1
Accept: application/json
Host: localhost:8081

```

</details>

<details>
<summary>shell</summary>

```shell
curl --request GET \
  --url http://localhost:8081/customer/0 \
  --header 'Accept: application/json'
```

</details>

<details>
<summary>javascript</summary>

```javascript
fetch("http://localhost:8081/customer/0", {
  "method": "GET",
  "headers": {
    "Accept": "application/json"
  }
})
.then(response => {
  console.log(response);
})
.catch(err => {
  console.error(err);
});
```

</details>

`GET /customer/{customerId}`

*get a single customer*

<h3 id="getcustomer-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|customerId|path|integer|true|none|

> Example responses

> 200 Response

```json
{
  "id": 0,
  "firstName": "string",
  "lastName": "string",
  "formOfAddress": "herr",
  "title": "none",
  "maritalStatus": "ledig",
  "dateOfBirth": "2019-08-24",
  "employmentStatus": "selbststaendig",
  "dogOwner": true,
  "address": {
    "city": "string",
    "street": "string",
    "postalCode": "string"
  },
  "phoneNumber": "string",
  "email": "user@example.com",
  "bankDetails": "string"
}
```

> default Response

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Error/>
```

```
"string"
```

<h3 id="getcustomer-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|customer found|[CustomerAllRequired](#schemacustomerallrequired)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|no customer at this location|[Error](#schemaerror)|
|default|Default|unexpected error|string|

<aside class="success">
This operation does not require authentication
</aside>

## updateCustomer

<a id="opIdupdateCustomer"></a>

> Code samples

<details>
<summary>http</summary>

```http
PUT /customer/0 HTTP/1.1
Content-Type: application/json
Accept: application/json
Host: localhost:8081
Content-Length: 324

{"firstName":"string","lastName":"string","formOfAddress":"herr","title":"none","maritalStatus":"ledig","dateOfBirth":"2019-08-24","employmentStatus":"selbststaendig","dogOwner":true,"address":{"city":"string","street":"string","postalCode":"string"},"phoneNumber":"string","email":"user@example.com","bankDetails":"string"}
```

</details>

<details>
<summary>shell</summary>

```shell
curl --request PUT \
  --url http://localhost:8081/customer/0 \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json' \
  --data '{"firstName":"string","lastName":"string","formOfAddress":"herr","title":"none","maritalStatus":"ledig","dateOfBirth":"2019-08-24","employmentStatus":"selbststaendig","dogOwner":true,"address":{"city":"string","street":"string","postalCode":"string"},"phoneNumber":"string","email":"user@example.com","bankDetails":"string"}'
```

</details>

<details>
<summary>javascript</summary>

```javascript
fetch("http://localhost:8081/customer/0", {
  "method": "PUT",
  "headers": {
    "Content-Type": "application/json",
    "Accept": "application/json"
  },
  "body": "{\"firstName\":\"string\",\"lastName\":\"string\",\"formOfAddress\":\"herr\",\"title\":\"none\",\"maritalStatus\":\"ledig\",\"dateOfBirth\":\"2019-08-24\",\"employmentStatus\":\"selbststaendig\",\"dogOwner\":true,\"address\":{\"city\":\"string\",\"street\":\"string\",\"postalCode\":\"string\"},\"phoneNumber\":\"string\",\"email\":\"user@example.com\",\"bankDetails\":\"string\"}"
})
.then(response => {
  console.log(response);
})
.catch(err => {
  console.error(err);
});
```

</details>

`PUT /customer/{customerId}`

*replace a customer*

> Body parameter

```json
{
  "firstName": "string",
  "lastName": "string",
  "formOfAddress": "herr",
  "title": "none",
  "maritalStatus": "ledig",
  "dateOfBirth": "2019-08-24",
  "employmentStatus": "selbststaendig",
  "dogOwner": true,
  "address": {
    "city": "string",
    "street": "string",
    "postalCode": "string"
  },
  "phoneNumber": "string",
  "email": "user@example.com",
  "bankDetails": "string"
}
```

<h3 id="updatecustomer-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[CustomerAllRequired](#schemacustomerallrequired)|true|none|
|customerId|path|integer|true|none|

> Example responses

> 400 Response

```json
{
  "error": "string"
}
```

> default Response

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Error/>
```

```
"string"
```

<h3 id="updatecustomer-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|customer updated|None|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|invalid customer data|[Error](#schemaerror)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|no customer at this location|[Error](#schemaerror)|
|default|Default|unexpected error|string|

<aside class="success">
This operation does not require authentication
</aside>

<h1 id="meowmed--policy">policy</h1>

## getPolicyList

<a id="opIdgetPolicyList"></a>

> Code samples

<details>
<summary>http</summary>

```http
GET /customer/0/policy HTTP/1.1
Accept: application/json
Host: localhost:8081

```

</details>

<details>
<summary>shell</summary>

```shell
curl --request GET \
  --url http://localhost:8081/customer/0/policy \
  --header 'Accept: application/json'
```

</details>

<details>
<summary>javascript</summary>

```javascript
fetch("http://localhost:8081/customer/0/policy", {
  "method": "GET",
  "headers": {
    "Accept": "application/json"
  }
})
.then(response => {
  console.log(response);
})
.catch(err => {
  console.error(err);
});
```

</details>

`GET /customer/{customerId}/policy`

*get a list of policies*

<h3 id="getpolicylist-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|fields|query|array[string]|false|A filter for which properties of Policy should be transmitted. If no fields are specified, only id is transmitted. The value  'objectOfInsurance' indicates that the entire ObjectOfInsurance object  should be transmitted. Using 'objectOfInsurance' and one or more of its  sub properties in the same query is a semantic error.|
|customerId|path|integer|true|none|

#### Enumerated Values

|Parameter|Value|
|---|---|
|fields|startDate|
|fields|endDate|
|fields|dueDate|
|fields|coverage|
|fields|premium|
|fields|active|
|fields|objectOfInsurance|
|fields|objectOfInsurance.name|
|fields|objectOfInsurance.race|
|fields|objectOfInsurance.color|
|fields|objectOfInsurance.dateOfBirth|
|fields|objectOfInsurance.castrated|
|fields|objectOfInsurance.personality|
|fields|objectOfInsurance.environment|
|fields|objectOfInsurance.weight|

> Example responses

> 200 Response

```json
[
  {
    "id": 0,
    "startDate": "2019-08-24",
    "endDate": "2019-08-24",
    "dueDate": "2019-08-24",
    "coverage": 0,
    "premium": 0,
    "active": true,
    "objectOfInsurance": {
      "name": "string",
      "race": "siamese",
      "color": "seal",
      "dateOfBirth": "2019-08-24",
      "castrated": true,
      "personality": "anhaenglich",
      "environment": "draussen",
      "weight": 0
    }
  }
]
```

> default Response

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Error/>
```

```
"string"
```

<h3 id="getpolicylist-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|success|Inline|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|no policies for selected customer|None|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|invalid fields parameter|[Error](#schemaerror)|
|default|Default|unexpected error|string|

<h3 id="getpolicylist-responseschema">Response Schema</h3>

#### Enumerated Values

|Property|Value|
|---|---|
|race|siamese|
|race|perser|
|race|bengal|
|race|maine-coon|
|race|sphynx|
|race|scottish-fold|
|race|british-shorthair|
|race|abyssinian|
|race|ragdoll|
|color|seal|
|color|blau|
|color|lilac|
|color|creme|
|color|weiss|
|color|schildpatt|
|color|schwarz|
|color|braun|
|color|marmor|
|color|grau|
|color|rot|
|color|zimt|
|personality|anhaenglich|
|personality|spielerisch|
|environment|draussen|
|environment|drinnen|

#### Links

**get** => <a href="#opIdgetPolicy">getPolicy</a>

|Parameter|Expression|
|---|---|
|policyId|$response.body#/id|

**put** => <a href="#opIdupdatePolicy">updatePolicy</a>

|Parameter|Expression|
|---|---|
|policyId|$response.body#/id|

<aside class="success">
This operation does not require authentication
</aside>

## createPolicy

<a id="opIdcreatePolicy"></a>

> Code samples

<details>
<summary>http</summary>

```http
POST /customer/0/policy HTTP/1.1
Content-Type: application/json
Accept: application/json
Host: localhost:8081
Content-Length: 263

{"startDate":"2019-08-24","endDate":"2019-08-24","dueDate":"2019-08-24","coverage":0,"objectOfInsurance":{"name":"string","race":"siamese","color":"seal","dateOfBirth":"2019-08-24","castrated":true,"personality":"anhaenglich","environment":"draussen","weight":0}}
```

</details>

<details>
<summary>shell</summary>

```shell
curl --request POST \
  --url http://localhost:8081/customer/0/policy \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json' \
  --data '{"startDate":"2019-08-24","endDate":"2019-08-24","dueDate":"2019-08-24","coverage":0,"objectOfInsurance":{"name":"string","race":"siamese","color":"seal","dateOfBirth":"2019-08-24","castrated":true,"personality":"anhaenglich","environment":"draussen","weight":0}}'
```

</details>

<details>
<summary>javascript</summary>

```javascript
fetch("http://localhost:8081/customer/0/policy", {
  "method": "POST",
  "headers": {
    "Content-Type": "application/json",
    "Accept": "application/json"
  },
  "body": "{\"startDate\":\"2019-08-24\",\"endDate\":\"2019-08-24\",\"dueDate\":\"2019-08-24\",\"coverage\":0,\"objectOfInsurance\":{\"name\":\"string\",\"race\":\"siamese\",\"color\":\"seal\",\"dateOfBirth\":\"2019-08-24\",\"castrated\":true,\"personality\":\"anhaenglich\",\"environment\":\"draussen\",\"weight\":0}}"
})
.then(response => {
  console.log(response);
})
.catch(err => {
  console.error(err);
});
```

</details>

`POST /customer/{customerId}/policy`

*create new policy*

> Body parameter

```json
{
  "startDate": "2019-08-24",
  "endDate": "2019-08-24",
  "dueDate": "2019-08-24",
  "coverage": 0,
  "objectOfInsurance": {
    "name": "string",
    "race": "siamese",
    "color": "seal",
    "dateOfBirth": "2019-08-24",
    "castrated": true,
    "personality": "anhaenglich",
    "environment": "draussen",
    "weight": 0
  }
}
```

<h3 id="createpolicy-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[PolicyAllRequired](#schemapolicyallrequired)|true|none|
|customerId|path|integer|true|none|

> Example responses

> 201 Response

```json
{
  "id": 0
}
```

> default Response

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Error/>
```

```
"string"
```

<h3 id="createpolicy-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|policy created|[ID](#schemaid)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|invalid policy data|[Error](#schemaerror)|
|default|Default|unexpected error|string|

<aside class="success">
This operation does not require authentication
</aside>

## getPolicy

<a id="opIdgetPolicy"></a>

> Code samples

<details>
<summary>http</summary>

```http
GET /customer/0/policy/0 HTTP/1.1
Accept: application/json
Host: localhost:8081

```

</details>

<details>
<summary>shell</summary>

```shell
curl --request GET \
  --url http://localhost:8081/customer/0/policy/0 \
  --header 'Accept: application/json'
```

</details>

<details>
<summary>javascript</summary>

```javascript
fetch("http://localhost:8081/customer/0/policy/0", {
  "method": "GET",
  "headers": {
    "Accept": "application/json"
  }
})
.then(response => {
  console.log(response);
})
.catch(err => {
  console.error(err);
});
```

</details>

`GET /customer/{customerId}/policy/{policyId}`

*get a single policy*

<h3 id="getpolicy-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|customerId|path|integer|true|none|
|policyId|path|integer|true|none|

> Example responses

> 200 Response

```json
{
  "id": 0,
  "startDate": "2019-08-24",
  "endDate": "2019-08-24",
  "dueDate": "2019-08-24",
  "coverage": 0,
  "premium": 0,
  "active": true,
  "objectOfInsurance": {
    "name": "string",
    "race": "siamese",
    "color": "seal",
    "dateOfBirth": "2019-08-24",
    "castrated": true,
    "personality": "anhaenglich",
    "environment": "draussen",
    "weight": 0
  }
}
```

> default Response

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Error/>
```

```
"string"
```

<h3 id="getpolicy-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|policy found|[PolicyAllRequired](#schemapolicyallrequired)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|no policy at this location|[Error](#schemaerror)|
|default|Default|unexpected error|string|

<aside class="success">
This operation does not require authentication
</aside>

## updatePolicy

<a id="opIdupdatePolicy"></a>

> Code samples

<details>
<summary>http</summary>

```http
PUT /customer/0/policy/0 HTTP/1.1
Content-Type: application/json
Accept: application/json
Host: localhost:8081
Content-Length: 263

{"startDate":"2019-08-24","endDate":"2019-08-24","dueDate":"2019-08-24","coverage":0,"objectOfInsurance":{"name":"string","race":"siamese","color":"seal","dateOfBirth":"2019-08-24","castrated":true,"personality":"anhaenglich","environment":"draussen","weight":0}}
```

</details>

<details>
<summary>shell</summary>

```shell
curl --request PUT \
  --url http://localhost:8081/customer/0/policy/0 \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json' \
  --data '{"startDate":"2019-08-24","endDate":"2019-08-24","dueDate":"2019-08-24","coverage":0,"objectOfInsurance":{"name":"string","race":"siamese","color":"seal","dateOfBirth":"2019-08-24","castrated":true,"personality":"anhaenglich","environment":"draussen","weight":0}}'
```

</details>

<details>
<summary>javascript</summary>

```javascript
fetch("http://localhost:8081/customer/0/policy/0", {
  "method": "PUT",
  "headers": {
    "Content-Type": "application/json",
    "Accept": "application/json"
  },
  "body": "{\"startDate\":\"2019-08-24\",\"endDate\":\"2019-08-24\",\"dueDate\":\"2019-08-24\",\"coverage\":0,\"objectOfInsurance\":{\"name\":\"string\",\"race\":\"siamese\",\"color\":\"seal\",\"dateOfBirth\":\"2019-08-24\",\"castrated\":true,\"personality\":\"anhaenglich\",\"environment\":\"draussen\",\"weight\":0}}"
})
.then(response => {
  console.log(response);
})
.catch(err => {
  console.error(err);
});
```

</details>

`PUT /customer/{customerId}/policy/{policyId}`

*replace a policy*

> Body parameter

```json
{
  "startDate": "2019-08-24",
  "endDate": "2019-08-24",
  "dueDate": "2019-08-24",
  "coverage": 0,
  "objectOfInsurance": {
    "name": "string",
    "race": "siamese",
    "color": "seal",
    "dateOfBirth": "2019-08-24",
    "castrated": true,
    "personality": "anhaenglich",
    "environment": "draussen",
    "weight": 0
  }
}
```

<h3 id="updatepolicy-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[PolicyAllRequired](#schemapolicyallrequired)|true|none|
|customerId|path|integer|true|none|
|policyId|path|integer|true|none|

> Example responses

> 400 Response

```json
{
  "error": "string"
}
```

> default Response

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Error/>
```

```
"string"
```

<h3 id="updatepolicy-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|204|[No Content](https://tools.ietf.org/html/rfc7231#section-6.3.5)|policy updated|None|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|invalid policy data|[Error](#schemaerror)|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|no policy at this location|[Error](#schemaerror)|
|default|Default|unexpected error|string|

<aside class="success">
This operation does not require authentication
</aside>

## calcPolicyPrice

<a id="opIdcalcPolicyPrice"></a>

> Code samples

<details>
<summary>http</summary>

```http
POST /policyprice HTTP/1.1
Content-Type: application/json
Accept: application/json
Host: localhost:8081
Content-Length: 289

{"customerId":0,"policy":{"startDate":"2019-08-24","endDate":"2019-08-24","dueDate":"2019-08-24","coverage":0,"objectOfInsurance":{"name":"string","race":"siamese","color":"seal","dateOfBirth":"2019-08-24","castrated":true,"personality":"anhaenglich","environment":"draussen","weight":0}}}
```

</details>

<details>
<summary>shell</summary>

```shell
curl --request POST \
  --url http://localhost:8081/policyprice \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json' \
  --data '{"customerId":0,"policy":{"startDate":"2019-08-24","endDate":"2019-08-24","dueDate":"2019-08-24","coverage":0,"objectOfInsurance":{"name":"string","race":"siamese","color":"seal","dateOfBirth":"2019-08-24","castrated":true,"personality":"anhaenglich","environment":"draussen","weight":0}}}'
```

</details>

<details>
<summary>javascript</summary>

```javascript
fetch("http://localhost:8081/policyprice", {
  "method": "POST",
  "headers": {
    "Content-Type": "application/json",
    "Accept": "application/json"
  },
  "body": "{\"customerId\":0,\"policy\":{\"startDate\":\"2019-08-24\",\"endDate\":\"2019-08-24\",\"dueDate\":\"2019-08-24\",\"coverage\":0,\"objectOfInsurance\":{\"name\":\"string\",\"race\":\"siamese\",\"color\":\"seal\",\"dateOfBirth\":\"2019-08-24\",\"castrated\":true,\"personality\":\"anhaenglich\",\"environment\":\"draussen\",\"weight\":0}}}"
})
.then(response => {
  console.log(response);
})
.catch(err => {
  console.error(err);
});
```

</details>

`POST /policyprice`

*calculate policy price*

> Body parameter

```json
{
  "customerId": 0,
  "policy": {
    "startDate": "2019-08-24",
    "endDate": "2019-08-24",
    "dueDate": "2019-08-24",
    "coverage": 0,
    "objectOfInsurance": {
      "name": "string",
      "race": "siamese",
      "color": "seal",
      "dateOfBirth": "2019-08-24",
      "castrated": true,
      "personality": "anhaenglich",
      "environment": "draussen",
      "weight": 0
    }
  }
}
```

<h3 id="calcpolicyprice-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[PolicyCalc](#schemapolicycalc)|true|none|

> Example responses

> 200 Response

```json
{
  "premium": 0
}
```

> default Response

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Error/>
```

```
"string"
```

<h3 id="calcpolicyprice-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|price calculated|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|invalid customerId or policy data|[Error](#schemaerror)|
|default|Default|unexpected error|string|

<h3 id="calcpolicyprice-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|» premium|number|true|none|none|

<aside class="success">
This operation does not require authentication
</aside>

# Schemas

<h2 id="tocS_ID">ID</h2>
<!-- backwards compatibility -->
<a id="schemaid"></a>
<a id="schema_ID"></a>
<a id="tocSid"></a>
<a id="tocsid"></a>

```json
{
  "id": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer|true|none|none|

<h2 id="tocS_Error">Error</h2>
<!-- backwards compatibility -->
<a id="schemaerror"></a>
<a id="schema_Error"></a>
<a id="tocSerror"></a>
<a id="tocserror"></a>

```json
{
  "error": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|error|string|true|none|short description of error condition|

<h2 id="tocS_UnknownError">UnknownError</h2>
<!-- backwards compatibility -->
<a id="schemaunknownerror"></a>
<a id="schema_UnknownError"></a>
<a id="tocSunknownerror"></a>
<a id="tocsunknownerror"></a>

```json
{}

```

### Properties

*None*

<h2 id="tocS_Customer">Customer</h2>
<!-- backwards compatibility -->
<a id="schemacustomer"></a>
<a id="schema_Customer"></a>
<a id="tocScustomer"></a>
<a id="tocscustomer"></a>

```json
{
  "id": 0,
  "firstName": "string",
  "lastName": "string",
  "formOfAddress": "herr",
  "title": "none",
  "maritalStatus": "ledig",
  "dateOfBirth": "2019-08-24",
  "employmentStatus": "selbststaendig",
  "dogOwner": true,
  "address": {
    "city": "string",
    "street": "string",
    "postalCode": "string"
  },
  "phoneNumber": "string",
  "email": "user@example.com",
  "bankDetails": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer|false|read-only|none|
|firstName|string|false|none|none|
|lastName|string|false|none|none|
|formOfAddress|[FormOfAddress](#schemaformofaddress)|false|none|none|
|title|[Title](#schematitle)|false|none|none|
|maritalStatus|[MaritalStatus](#schemamaritalstatus)|false|none|none|
|dateOfBirth|string(date)|false|none|none|
|employmentStatus|[EmploymentStatus](#schemaemploymentstatus)|false|none|none|
|dogOwner|boolean|false|none|none|
|address|[Address](#schemaaddress)|false|none|none|
|phoneNumber|string(phone)|false|none|none|
|email|string(email)|false|none|none|
|bankDetails|string|false|none|none|

<h2 id="tocS_Address">Address</h2>
<!-- backwards compatibility -->
<a id="schemaaddress"></a>
<a id="schema_Address"></a>
<a id="tocSaddress"></a>
<a id="tocsaddress"></a>

```json
{
  "city": "string",
  "street": "string",
  "postalCode": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|city|string|false|none|none|
|street|string|false|none|none|
|postalCode|string|false|none|none|

<h2 id="tocS_CustomerAllRequired">CustomerAllRequired</h2>
<!-- backwards compatibility -->
<a id="schemacustomerallrequired"></a>
<a id="schema_CustomerAllRequired"></a>
<a id="tocScustomerallrequired"></a>
<a id="tocscustomerallrequired"></a>

```json
{
  "id": 0,
  "firstName": "string",
  "lastName": "string",
  "formOfAddress": "herr",
  "title": "none",
  "maritalStatus": "ledig",
  "dateOfBirth": "2019-08-24",
  "employmentStatus": "selbststaendig",
  "dogOwner": true,
  "address": {
    "city": "string",
    "street": "string",
    "postalCode": "string"
  },
  "phoneNumber": "string",
  "email": "user@example.com",
  "bankDetails": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer|false|read-only|none|
|firstName|string|true|none|none|
|lastName|string|true|none|none|
|formOfAddress|[FormOfAddress](#schemaformofaddress)|true|none|none|
|title|[Title](#schematitle)|true|none|none|
|maritalStatus|[MaritalStatus](#schemamaritalstatus)|true|none|none|
|dateOfBirth|string(date)|true|none|none|
|employmentStatus|[EmploymentStatus](#schemaemploymentstatus)|true|none|none|
|dogOwner|boolean|true|none|none|
|address|[Address](#schemaaddress)|true|none|none|
|phoneNumber|string(phone)|true|none|none|
|email|string(email)|true|none|none|
|bankDetails|string|true|none|none|

<h2 id="tocS_CustomerPropertyNames">CustomerPropertyNames</h2>
<!-- backwards compatibility -->
<a id="schemacustomerpropertynames"></a>
<a id="schema_CustomerPropertyNames"></a>
<a id="tocScustomerpropertynames"></a>
<a id="tocscustomerpropertynames"></a>

```json
"firstName"

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|*anonymous*|firstName|
|*anonymous*|lastName|
|*anonymous*|formOfAddress|
|*anonymous*|title|
|*anonymous*|maritalStatus|
|*anonymous*|dateOfBirth|
|*anonymous*|employmentStatus|
|*anonymous*|dogOwner|
|*anonymous*|phoneNumber|
|*anonymous*|email|
|*anonymous*|bankDetails|
|*anonymous*|address|
|*anonymous*|address.city|
|*anonymous*|address.street|
|*anonymous*|address.postalCode|

<h2 id="tocS_Policy">Policy</h2>
<!-- backwards compatibility -->
<a id="schemapolicy"></a>
<a id="schema_Policy"></a>
<a id="tocSpolicy"></a>
<a id="tocspolicy"></a>

```json
{
  "id": 0,
  "startDate": "2019-08-24",
  "endDate": "2019-08-24",
  "dueDate": "2019-08-24",
  "coverage": 0,
  "premium": 0,
  "active": true,
  "objectOfInsurance": {
    "name": "string",
    "race": "siamese",
    "color": "seal",
    "dateOfBirth": "2019-08-24",
    "castrated": true,
    "personality": "anhaenglich",
    "environment": "draussen",
    "weight": 0
  }
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer|false|read-only|none|
|startDate|string(date)|false|none|none|
|endDate|string(date)|false|none|none|
|dueDate|string(date)|false|none|none|
|coverage|number|false|none|none|
|premium|number|false|read-only|none|
|active|boolean|false|read-only|none|
|objectOfInsurance|[ObjectOfInsurance](#schemaobjectofinsurance)|false|none|none|

<h2 id="tocS_ObjectOfInsurance">ObjectOfInsurance</h2>
<!-- backwards compatibility -->
<a id="schemaobjectofinsurance"></a>
<a id="schema_ObjectOfInsurance"></a>
<a id="tocSobjectofinsurance"></a>
<a id="tocsobjectofinsurance"></a>

```json
{
  "name": "string",
  "race": "siamese",
  "color": "seal",
  "dateOfBirth": "2019-08-24",
  "castrated": true,
  "personality": "anhaenglich",
  "environment": "draussen",
  "weight": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|name|string|false|none|none|
|race|[CatRace](#schemacatrace)|false|none|none|
|color|[FurColor](#schemafurcolor)|false|none|none|
|dateOfBirth|string(date)|false|none|none|
|castrated|boolean|false|none|none|
|personality|[Personality](#schemapersonality)|false|none|none|
|environment|[Environment](#schemaenvironment)|false|none|none|
|weight|number|false|none|none|

<h2 id="tocS_PolicyAllRequired">PolicyAllRequired</h2>
<!-- backwards compatibility -->
<a id="schemapolicyallrequired"></a>
<a id="schema_PolicyAllRequired"></a>
<a id="tocSpolicyallrequired"></a>
<a id="tocspolicyallrequired"></a>

```json
{
  "id": 0,
  "startDate": "2019-08-24",
  "endDate": "2019-08-24",
  "dueDate": "2019-08-24",
  "coverage": 0,
  "premium": 0,
  "active": true,
  "objectOfInsurance": {
    "name": "string",
    "race": "siamese",
    "color": "seal",
    "dateOfBirth": "2019-08-24",
    "castrated": true,
    "personality": "anhaenglich",
    "environment": "draussen",
    "weight": 0
  }
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer|false|read-only|none|
|startDate|string(date)|true|none|none|
|endDate|string(date)|true|none|none|
|dueDate|string(date)|true|none|none|
|coverage|number|true|none|none|
|premium|number|true|read-only|none|
|active|boolean|true|read-only|none|
|objectOfInsurance|[ObjectOfInsurance](#schemaobjectofinsurance)|true|none|none|

<h2 id="tocS_PolicyPropertyNames">PolicyPropertyNames</h2>
<!-- backwards compatibility -->
<a id="schemapolicypropertynames"></a>
<a id="schema_PolicyPropertyNames"></a>
<a id="tocSpolicypropertynames"></a>
<a id="tocspolicypropertynames"></a>

```json
"startDate"

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|*anonymous*|startDate|
|*anonymous*|endDate|
|*anonymous*|dueDate|
|*anonymous*|coverage|
|*anonymous*|premium|
|*anonymous*|active|
|*anonymous*|objectOfInsurance|
|*anonymous*|objectOfInsurance.name|
|*anonymous*|objectOfInsurance.race|
|*anonymous*|objectOfInsurance.color|
|*anonymous*|objectOfInsurance.dateOfBirth|
|*anonymous*|objectOfInsurance.castrated|
|*anonymous*|objectOfInsurance.personality|
|*anonymous*|objectOfInsurance.environment|
|*anonymous*|objectOfInsurance.weight|

<h2 id="tocS_PolicyCalc">PolicyCalc</h2>
<!-- backwards compatibility -->
<a id="schemapolicycalc"></a>
<a id="schema_PolicyCalc"></a>
<a id="tocSpolicycalc"></a>
<a id="tocspolicycalc"></a>

```json
{
  "customerId": 0,
  "policy": {
    "id": 0,
    "startDate": "2019-08-24",
    "endDate": "2019-08-24",
    "dueDate": "2019-08-24",
    "coverage": 0,
    "premium": 0,
    "active": true,
    "objectOfInsurance": {
      "name": "string",
      "race": "siamese",
      "color": "seal",
      "dateOfBirth": "2019-08-24",
      "castrated": true,
      "personality": "anhaenglich",
      "environment": "draussen",
      "weight": 0
    }
  }
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|customerId|integer|true|none|none|
|policy|[PolicyAllRequired](#schemapolicyallrequired)|true|none|none|

<h2 id="tocS_FormOfAddress">FormOfAddress</h2>
<!-- backwards compatibility -->
<a id="schemaformofaddress"></a>
<a id="schema_FormOfAddress"></a>
<a id="tocSformofaddress"></a>
<a id="tocsformofaddress"></a>

```json
"herr"

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|*anonymous*|herr|
|*anonymous*|frau|

<h2 id="tocS_Title">Title</h2>
<!-- backwards compatibility -->
<a id="schematitle"></a>
<a id="schema_Title"></a>
<a id="tocStitle"></a>
<a id="tocstitle"></a>

```json
"none"

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|*anonymous*|none|
|*anonymous*|doctor|
|*anonymous*|professor|

<h2 id="tocS_MaritalStatus">MaritalStatus</h2>
<!-- backwards compatibility -->
<a id="schemamaritalstatus"></a>
<a id="schema_MaritalStatus"></a>
<a id="tocSmaritalstatus"></a>
<a id="tocsmaritalstatus"></a>

```json
"ledig"

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|*anonymous*|ledig|
|*anonymous*|verheiratet|
|*anonymous*|verwitwet|

<h2 id="tocS_EmploymentStatus">EmploymentStatus</h2>
<!-- backwards compatibility -->
<a id="schemaemploymentstatus"></a>
<a id="schema_EmploymentStatus"></a>
<a id="tocSemploymentstatus"></a>
<a id="tocsemploymentstatus"></a>

```json
"selbststaendig"

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|*anonymous*|selbststaendig|
|*anonymous*|angestellt|
|*anonymous*|arbeitslos|
|*anonymous*|arbeitssuchend|
|*anonymous*|ausbildung|

<h2 id="tocS_Personality">Personality</h2>
<!-- backwards compatibility -->
<a id="schemapersonality"></a>
<a id="schema_Personality"></a>
<a id="tocSpersonality"></a>
<a id="tocspersonality"></a>

```json
"anhaenglich"

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|*anonymous*|anhaenglich|
|*anonymous*|spielerisch|

<h2 id="tocS_Environment">Environment</h2>
<!-- backwards compatibility -->
<a id="schemaenvironment"></a>
<a id="schema_Environment"></a>
<a id="tocSenvironment"></a>
<a id="tocsenvironment"></a>

```json
"draussen"

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|*anonymous*|draussen|
|*anonymous*|drinnen|

<h2 id="tocS_CatRace">CatRace</h2>
<!-- backwards compatibility -->
<a id="schemacatrace"></a>
<a id="schema_CatRace"></a>
<a id="tocScatrace"></a>
<a id="tocscatrace"></a>

```json
"siamese"

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|*anonymous*|siamese|
|*anonymous*|perser|
|*anonymous*|bengal|
|*anonymous*|maine-coon|
|*anonymous*|sphynx|
|*anonymous*|scottish-fold|
|*anonymous*|british-shorthair|
|*anonymous*|abyssinian|
|*anonymous*|ragdoll|

<h2 id="tocS_FurColor">FurColor</h2>
<!-- backwards compatibility -->
<a id="schemafurcolor"></a>
<a id="schema_FurColor"></a>
<a id="tocSfurcolor"></a>
<a id="tocsfurcolor"></a>

```json
"seal"

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|string|false|none|none|

#### Enumerated Values

|Property|Value|
|---|---|
|*anonymous*|seal|
|*anonymous*|blau|
|*anonymous*|lilac|
|*anonymous*|creme|
|*anonymous*|weiss|
|*anonymous*|schildpatt|
|*anonymous*|schwarz|
|*anonymous*|braun|
|*anonymous*|marmor|
|*anonymous*|grau|
|*anonymous*|rot|
|*anonymous*|zimt|

