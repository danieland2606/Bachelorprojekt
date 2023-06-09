package EDA.CustomerService.REST;

import EDA.CustomerService.Application.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;


@RestController
@RequestMapping
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * Retrieves a customer list based on the specified fields.
     *
     * @param fields The optional fields parameter to filter the customer list. Defaults to null if not provided.
     * @return ResponseEntity<String> The response entity containing the customer list as a JSON string in the response body.
     * If the customer list is empty, a HTTP 204 No Content response is returned.
     * If an illegal argument is passed, a HTTP 400 Bad Request response is returned with a custom error JSON body generated from the error message.
     * If an error occurs during JSON processing, a HTTP 500 Internal Server Error response is returned with the error message in the response body.
     */
    @GetMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomerList(@RequestParam(value = "fields", required = false) String fields) {
        try {
            // Call the customer service to retrieve a customer list, with specified fields as a JSON
            String responseBody = customerService.getCustomerList(fields);

            // Check if the response body is null
            if (responseBody == null) {
                // If the response body is null, return a HTTP 204 No Content response
                return ResponseEntity.noContent().build();
            }
            // If the response body is not null, return an HTTP 200 OK response with the response body
            return ResponseEntity.ok(responseBody);

        } catch (JsonProcessingException e) {
            // If an JsonProcessingException occurs, return a HTTP 500 Internal Server Error,
            // with the exception message in the response body
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // If an IllegalArgumentException occurs, return a HTTP 400 Bad Request response,
            // with the exception message in the response body formatted with the errorJsonBody method.
            return ResponseEntity.badRequest().body(errorJsonBody(e.getMessage()));
        }
    }

    /**
     * Retrieves the customer with the specified ID.
     *
     * @param c_id The ID of the customer.
     * @return A ResponseEntity containing the customer data in the response body, or a 404 status if the customer is not found.
     * If an error occurs during JSON processing, a HTTP 500 Internal Server Error response is returned with the error message in the response body.
     */
    @GetMapping(value = "/customer/{c_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomer(@PathVariable Long c_id) {
        try {
            // Call the customer service to retrieve a customer, with specified id as a JSON
            String responseBody = customerService.getCustomer(c_id);
            // Check if the response body is null, i.e. if a customer with the given id has been found
            if (responseBody == null) {
                // If the response body is null, return a HTTP 204 No Content response
                return ResponseEntity.notFound().build();
            }
            // If the response body is not null, return an HTTP 200 OK response with the response body
            return ResponseEntity.ok(responseBody);

        } catch (JsonProcessingException e) {
            // If an JsonProcessingException occurs, return a HTTP 500 Internal Server Error,
            // with the exception message in the response body
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Adds a new customer.
     *
     * @param customer The JSON representation of the customer to be added.
     * @return A ResponseEntity containing the id of the added customer, or a 400 status if there is an error.
     * If the JSON representation of the customer is invalid, a HTTP 400 Bad Request response is returned with a custom error JSON body generated from the error message.
     * If an error occurs during JSON processing, a HTTP 400 Bad Request response is returned with the error message in the response body.
     */
    @PostMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postCustomer(@RequestBody String customer) {
        try {
            // Add the customer using the customer service and return the customer id
            String responseBody = customerService.addCustomer(customer);
            // Return the customer id as a JSON
            return ResponseEntity.created(URI.create("/customer")).body(responseBody);
        } catch (JsonProcessingException e) {
            // If an JsonProcessingException occurs, return a HTTP 400 Bad Request response
            // with the exception message in the response body
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // If an IllegalArgumentException occurs, return a HTTP 400 Bad Request response
            // with the exception message in the response body formatted with the errorJsonBody method.
            return ResponseEntity.badRequest().body(errorJsonBody(e.getMessage()));
        }
    }

    /**
     * Replaces an existing customer with new data.
     *
     * @param customer The JSON representation of the customer to replace the existing one.
     * @param c_id     The ID of the customer to be replaced.
     * @return A ResponseEntity indicating the success or failure of the operation.
     * If the JSON representation of the customer is invalid, a HTTP 400 Bad Request response is returned with a custom error JSON body generated from the error message.
     * If an error occurs during JSON processing, a HTTP 400 Bad Request response is returned with the error message in the response body.
     * If the customer with the given id hasn't been found, a HTTP 404 Not Found response is returned.
     */
    @PutMapping(value = "/customer/{c_id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putCustomer(@RequestBody String customer, @PathVariable Long c_id) {
        try {
            // replace the customer that has the given id with the given customer using the customer service
            customerService.replaceCustomer(c_id, customer);

            // Return a 204 No Content response indicating success
            return ResponseEntity.noContent().build();
        } catch (JsonProcessingException e) {
            // If an JsonProcessingException occurs, return a HTTP 400 Bad Request response
            // with the exception message in the response body
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // If an IllegalArgumentException occurs, return a HTTP 400 Bad Request response
            // with the exception message in the response body formatted with the errorJsonBody method.
            return ResponseEntity.badRequest().body(errorJsonBody(e.getMessage()));
        } catch (NoSuchElementException e) {
            // If an NoSuchElementException occurs, return a HTTP 404 Not Found response
            return ResponseEntity.notFound().build();
        }
    }

    private String errorJsonBody(String message) {
        return "{\"error\":\"" + message + "\"}";
    }
}
