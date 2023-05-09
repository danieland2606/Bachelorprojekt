package EDA.MeowMed.Application;

import EDA.MeowMed.Persistence.Entity.Customer;
import EDA.MeowMed.Persistence.Entity.Address;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

@Service
public class CustomerValidationService {
    private final String[] formOfAddress = {
            "herr", "frau"
    };
    private final String[] title = {
            "none", "doctor", "professor"
    };
    private final String[] maritalStatus = {
            "ledig", "verheiratet", "verwitwet"
    };
    private final String[] employmentStatus = {
            "selbststaendig", "angestellt", "arbeitslos", "arbeitssuchend", "ausbildung"
    };

    /**
     * TODO: Add comment
     *
     * @param customer
     * @throws IllegalArgumentException
     */
    public void validateCustomer(Customer customer) throws IllegalArgumentException {
        Arrays.stream(CustomerValidationService.class.getDeclaredMethods())
                .filter(method -> method.getName().contains("check"))
                .forEach(method -> {
                    try {
                        method.invoke(this, customer);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw (IllegalArgumentException) e.getTargetException();
                    }
                });
    }

    private void checkFormOfAddress(Customer customer) throws IllegalArgumentException {
        if (!Arrays.asList(formOfAddress).contains(customer.getFormOfAddress())) {
            throw new IllegalArgumentException(customer.getFormOfAddress() + " is not a valid argument for formOfAddress!");
        }
    }

    private void checkTitle(Customer customer) throws IllegalArgumentException {
        if (!Arrays.asList(title).contains(customer.getTitle())) {
            throw new IllegalArgumentException(customer.getTitle() + " is not a valid argument for title!");
        }
    }

    private void checkMaritalStatus(Customer customer) throws IllegalArgumentException {
        if (!Arrays.asList(maritalStatus).contains(customer.getMaritalStatus())) {
            throw new IllegalArgumentException(customer.getMaritalStatus() + " is not a valid argument for maritalStatus!");
        }
    }

    private void checkDateOfBirth(Customer customer) throws IllegalArgumentException {
        if (Period.between(customer.getDateOfBirth(), LocalDate.now()).getYears() < 18) {
            throw new IllegalArgumentException(customer.getDateOfBirth() + " is not a valid argument for dateOfBirth!");
        }
    }

    private void checkEmploymentStatus(Customer customer) throws IllegalArgumentException {
        if (!Arrays.asList(employmentStatus).contains(customer.getEmploymentStatus())) {
            throw new IllegalArgumentException(customer.getEmploymentStatus() + " is not a valid argument for employmentStatus!");
        }
    }

    private void checkDogOwner(Customer customer) throws IllegalArgumentException {
        if (false) { //Stub, currently no use
            throw new IllegalArgumentException(customer.getDogOwner() + " is not a valid argument for dogOwner!");
        }
    }


    private void checkAddress(Customer customer) throws IllegalArgumentException {
        Address address = customer.getAddress();
        if (!address.getPostalCode().matches("[0-9]+") || !(address.getPostalCode().length() == 5)) {
            throw new IllegalArgumentException(address.getPostalCode() + " is not a valid argument for postalcode!");
        }
        if (false) {//Stub, currently no use
            throw new IllegalArgumentException(address.getCity() + " is not a valid argument for city!");
        }
        if (false) {//Stub, currently no use
            throw new IllegalArgumentException(address.getStreet() + " is not a valid argument for street!");
        }
    }

    // TODO: Add validation for PhoneNumber, Email and BankDetails
    private void checkPhoneNumber(Customer customer) throws IllegalArgumentException {
        if (false) {//Stub, currently no use
            throw new IllegalArgumentException(customer.getPhoneNumber() + " is not a valid argument for phoneNumber!");
        }
    }

    private void checkEmail(Customer customer) throws IllegalArgumentException {
        if (false) {//Stub, currently no use
            throw new IllegalArgumentException(customer.getEmail() + " is not a valid argument for email!");
        }
    }

    private void checkBankDetails(Customer customer) throws IllegalArgumentException {
        if (false) {//Stub, currently no use
            throw new IllegalArgumentException(customer.getBankDetails() + " is not a valid argument for bankDetails!");
        }
    }
}