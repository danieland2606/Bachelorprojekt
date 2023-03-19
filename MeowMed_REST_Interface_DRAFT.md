# MeowMed+ REST Interface
## Customer
GET /customer?fields=[firstName,lastName,address]  
GET /customer/{id}  
PUT /customer

## Policy
GET /customer/{id}/policy?fields=[objectOfInsurance,startDate,endDate,coverage]  
GET /customer/{id}/policy/{id}  
PUT /customer/{id}/policy

GET /policyprice?details=[base64 encoded JSON calculation object]

### Customer object:
```json
{
  "Id": 0,
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
### Policy object:
```json
{
  "id": 1,
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
### Customer list example:
```json
{
  "customerList": [
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
}
```
### Calculation object:
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