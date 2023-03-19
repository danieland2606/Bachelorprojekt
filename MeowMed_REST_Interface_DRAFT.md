# MeowMed+ REST Interface
## Customer
### GET /customer?fields=[firstName,lastName,address]
Returns customer list with each customer containing UUID and requested fields.  
Response body:
```json
{
  "customerList": [
	{
  	"uuid": 0,
  	"firstName": "John",
  	"lastName": "Doe",
  	"address": {
    	"city": "Hannover",
    	"street": "Berliner Allee 100",
    	"postalCode": "30855"
  	}
	},
	{
  	"uuid": 1,
  	"firstName": "Jane",
  	"lastName": "Eyre",
  	"address": {
    	"city": "Hannover",
    	"street": "Berliner Allee 101",
    	"postalCode": "30855"
  	}
	}
  ]
}
```


### GET /customer/{id}
Returns customer object with UUID={id}.  
Response body:
```json
{
  "firstName":"John",
  "lastName":"Doe",
  "formOfAdress":"Mr",
  "title":"",
  "maritalStatus":"single",
  "dateOfBirth":"2000-12-30",
  "employmentStatus":"employed",
  "address":{
	"city":"Hannover",
	"street":"Berliner Allee 100",
	"postalCode":"30855"
  },
  "phoneNumber":"+4951177488375",
  "email":"example@aol.com",
  "bankDetails":"IE12BOFI90000112345678"
}
```
### POST /customer
Expects empty body. Creates new UUID and returns it.  
Response body:
```json
{
  "uuid": 0
}
```
### PUT /customer/{id}
Inserts Resource for UUID={id}. Expects request body like:
```json
{
  "firstName":"John",
  "lastName":"Doe",
  "formOfAdress":"Mr",
  "title":"",
  "maritalStatus":"single",
  "dateOfBirth":"2000-12-30",
  "employmentStatus":"employed",
  "address":{
	"city":"Hannover",
	"street":"Berliner Allee 100",
	"postalCode":"30855"
  },
  "phoneNumber":"+4951177488375",
  "email":"example@aol.com",
  "bankDetails":"IE12BOFI90000112345678"
}
```
## Policy
### GET /customer/{id}/policy?fields=[objectOfInsurance.name,startDate,endDate,coverage]
Returns all policies for customer with UUID={id}, with each policy containing uuid and requested fields.  
Response body:
```json
{
  "policyList": [
	{
      "uuid": 0,
      "startDate": "1990-01-01",
      "endDate": "2030-12-31",
      "coverage": 50000,
      "objectOfInsurance": {
	    "name": "Tomato"
	  }
	},
	{
      "uuid": 1,
      "startDate": "1992-11-01",
      "endDate": "2024-11-01",
      "coverage": 10000,
      "objectOfInsurance": {
	    "name": "Perry"
	  }
	}
  ]
}
```
### GET /customer/{c_id}/policy/{p_id}
Returns policy object for customer with UUID={c_id} with policy UUID={p_id}.  
Response body:
```json
{
  "startDate": "1990-01-01",
  "endDate": "2030-12-31",
  "coverage": 50000,
  "objectOfInsurance": {
	"name": "Tomato",
	"race": "Bengal",
	"color": "Braun",
	"age": 8,
	"castrated": true,
	"personality": "anhänglich",
	"environment": "drinnen",
	"weight": 4
  }
}
```
### POST /customer/{id}/policy
Expects empty body. Creates new policy UUID for customer with UUID={id} and returns it.  
Response body:
```json
{
  "uuid": 0
}
```
### PUT /customer/{c_id}/policy/{p_id}
Inserts Policy Resource for UUID={p_id} under customer with UUID={c_id}. Expects request body like:
```json
{
  "startDate": "1990-01-01",
  "endDate": "2030-12-31",
  "coverage": 50000,
  "objectOfInsurance": {
	"name": "Tomato",
	"race": "Bengal",
	"color": "Braun",
	"age": 8,
	"castrated": true,
	"personality": "anhänglich",
	"environment": "drinnen",
	"weight": 4
  }
}
```
### GET /policyprice?details=[base64 encoded JSON calculation object]
```json
{
  "postalCode": "30855",
  "coverage": 50000,
  "race": "Bengal",
  "color": "Braun",
  "age": 8,
  "castrated": true,
  "personality": "anhänglich",
  "environment": "drinnen",
  "weight": 4
}
```