# MeowMed+ REST Interface
## Customer
### GET /customer?fields={comma_seperated_fields}
Returns customer list with each customer containing id and requested fields. This example has {comma_seperated_fields}==firstName,lastName,address

#### Response body:
```json
[
  {
    "id": 0,
    "firstName": "John",
    "lastName": "Doe",
    "address": {
      "city": "Hannover",
      "street": "Berliner Allee 100",
      "postalCode": "30855"
    }
  },
  {
    "id": 1,
    "firstName": "Jane",
    "lastName": "Eyre",
    "address": {
      "city": "Hannover",
      "street": "Berliner Allee 101",
      "postalCode": "30855"
    }
  }
]

```

### GET /customer/{c_id}
Returns customer resource with id=={c_id}.

#### Response body:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "formOfAddress": "Mr",
  "title": "",
  "maritalStatus": "single",
  "dateOfBirth": "2000-12-30",
  "employmentStatus": "employed",
  "address": {
    "city": "Hannover",
    "street": "Berliner Allee 100",
    "postalCode": "30855"
  },
  "phoneNumber": "+4951177488375",
  "email": "example@aol.com",
  "bankDetails": "IE12BOFI90000112345678"
}
```
### POST /customer
Creates new customer resource.

#### Request body:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "formOfAddress": "Mr",
  "title": "",
  "maritalStatus": "single",
  "dateOfBirth": "2000-12-30",
  "employmentStatus": "employed",
  "address": {
    "city": "Hannover",
    "street": "Berliner Allee 100",
    "postalCode": "30855"
  },
  "phoneNumber": "+4951177488375",
  "email": "example@aol.com",
  "bankDetails": "IE12BOFI90000112345678"
}
```
#### Response body:
```json
{
  "id": 0
}
```
## Policy
### GET /customer/{c_id}/policy?fields={comma_seperated_fields}
Returns all policies for customer with id=={c_id}, with each policy containing policy id and requested fields. This example has {comma_seperated_fields}==startDate,endDate,coverage,objectOfInsurance.name

#### Response body:
```json
[
  {
    "id": 0,
    "startDate": "1990-01-01",
    "endDate": "2030-12-31",
    "coverage": 50000,
    "objectOfInsurance": {
      "name": "Tomato"
    }
  },
  {
    "id": 1,
    "startDate": "1992-11-01",
    "endDate": "2024-11-01",
    "coverage": 10000,
    "objectOfInsurance": {
      "name": "Perry"
    }
  }
]
```
### GET /customer/{c_id}/policy/{p_id}
Returns policy resource for customer with id=={c_id}, with policy id={p_id}.

#### Response body:
```json
{
  "startDate": "1990-01-01",
  "endDate": "2030-12-31",
  "coverage": 50000,
  "premium": 75,
  "objectOfInsurance": {
    "name": "Tomato",
    "race": "Bengal",
    "color": "Braun",
    "dateOfBirth": "2015-07-22",
    "castrated": true,
    "personality": "anhänglich",
    "environment": "drinnen",
    "weight": 4
  }
}
```
### POST /customer/{c_id}/policy
Creates new policy resource for customer with id=={c_id}.

#### Request body:
```json
{
  "startDate": "1990-01-01",
  "endDate": "2030-12-31",
  "coverage": 50000,
  "objectOfInsurance": {
    "name": "Tomato",
    "race": "Bengal",
    "color": "Braun",
    "dateOfBirth": "2015-07-22",
    "castrated": true,
    "personality": "anhänglich",
    "environment": "drinnen",
    "weight": 4
  }
}
```
#### Response body:
```json
{
  "id": 0
}
```
### GET /policyprice?details={base64 encoded JSON calculation object}
#### Calculation object:
```json
{
  "postalCode": "30855",
  "coverage": 50000,
  "race": "Bengal",
  "color": "Braun",
  "dateOfBirth": "2015-07-22",
  "castrated": true,
  "personality": "anhänglich",
  "environment": "drinnen",
  "weight": 4
}
```
#### Response body:
```json
{
  "premium": 75
}
```
## Fields query parameter
Example with Customer:
The request
> GET /customer

produces a response like this:
```json
[
  {
    "id": 0,
  },
  {
    "id": 1,
  }
]
```
> GET /customer?fields=firstName

produces a response like this:
```json
[
  {
    "id": 0,
    "firstName": "John",
  },
  {
    "id": 1,
    "firstName": "Jane",
  }
]
```
> GET /customer?fields=maritalStatus,dateOfBirth,address.street

produces a response like this:
```json
[
  {
    "id": 0,
    "maritalStatus": "single",
    "dateOfBirth": "2000-12-30",
    "address": {
      "street": "Berliner Allee 100"
    }
  },
  {
    "id": 1,
    "maritalStatus": "married",
    "dateOfBirth": "1998-06-22",
    "address": {
      "street": "Berliner Allee 101"
    }
  }
]
```