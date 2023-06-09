package EDA.CustomerService.Application;

import EDA.CustomerService.Persistence.Entity.Customer;
import EDA.CustomerService.Persistence.Entity.Address;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

@Service
public class CustomerValidationService {
    public final String cancelStateEmploymentStatus = "arbeitslos";
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
            cancelStateEmploymentStatus, "selbststaendig", "angestellt", "arbeitssuchend", "ausbildung"
    };

    /**
     * Validates the given customer by invoking all the check methods present in the CustomerValidationService class.
     * If any check method throws an IllegalArgumentException, it will be re-thrown.
     *
     * @param customer The customer to be validated.
     * @throws IllegalArgumentException If any check method throws this exception during validation.
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

    private void checkBankDetails(Customer customer) throws IllegalArgumentException {
        if (!IBANCheckDigit.IBAN_CHECK_DIGIT.isValid(customer.getBankDetails())) {
            throw new IllegalArgumentException(customer.getBankDetails() + " is not a valid argument for bankDetails!");
        }
    }

    private void checkPhoneNumber(Customer customer) throws IllegalArgumentException {
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            phoneNumber = PhoneNumberUtil.getInstance().parse(customer.getPhoneNumber(),"DE");
        } catch (NumberParseException e) {
            throw new IllegalArgumentException(customer.getPhoneNumber() + " is not a valid argument for phoneNumber!");
        }
        if (!PhoneNumberUtil.getInstance().isValidNumberForRegion(phoneNumber, "DE")) {
            throw new IllegalArgumentException(customer.getPhoneNumber() + " is not a valid argument for phoneNumber!");
        }
    }

    private void checkEmail(Customer customer) throws IllegalArgumentException {
        if (!EmailValidator.getInstance().isValid(customer.getEmail())) {
            throw new IllegalArgumentException(customer.getEmail() + " is not a valid argument for email!");
        }
    }
}
