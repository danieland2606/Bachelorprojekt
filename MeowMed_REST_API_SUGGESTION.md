/customer
 - GET  - returns a list of all customers (with queryparameters for search)
 - POST - creates a new customer

/customer/{c_id}
 - GET  - returns the customer with id "c_id"
 - PUT  - updates or creates the customer with id "c_id"

/customer/{c_id}/policy
 - GET  - return a list of all policies from customer with id "c_id" (with queryparameters for search?)
 - POST - creates a new policy for customer with id "c_id"

/customer/{c_id}/policy/{p_id}
 - GET  - returns the policy with id "p_id" from customer with id "c_id"

/policyprice
 - GET  - returns the price of the given policy (has to be in the request-body)
