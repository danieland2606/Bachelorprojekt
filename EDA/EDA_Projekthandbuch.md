# Projekthandbuch

## Technologien
Spring Boot
Java
Maven

#todo: hinzufügen von versionsnummern.

## Definitionen für Services

### CustomerService

#### CustomerCreatedEvent (json Objekt)
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "formOfAdress": "Mr",
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
#### Datenbank Tupel
Customer(<u>customer_id</u>, first_name, last_name, form_of_adress, title, marital_status, date_of_birth, employment_status, address_id, phone_number, e_mail, bank_details)

Foreign Key (address_id) References Address(address_id)

Address(<u>adress_id</u>, city, street, postal_code)
___
### PolicyService

#### PolicyCreated Event (json Objekt)
```json
```
#### Datenbank Tupel