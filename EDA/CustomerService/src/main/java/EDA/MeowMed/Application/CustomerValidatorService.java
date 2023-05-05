package EDA.MeowMed.Application;

import EDA.MeowMed.Persistence.Entity.Customer;
import EDA.MeowMed.Persistence.Entity.Address;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomerValidatorService {
    private final String[] formOfAddressElements = {
            "herr", "frau"
    };
    private final String[] titleElements = {
            "", "doctor", "professor"
    };
    private final String[] maritalStatusElements = {
            "ledig", "verheiratet", "verwitwet"
    };
    private final String[] employmentStatusElements = {
            "selbststaendig", "angestellt", "arbeitslos", "arbeitssuchend", "ausbildung"
    };

    private Set<String> formOfAddress;
    private Set<String> title;
    private Set<String> maritalStatus;
    private Set<String> employmentStatus;

    public CustomerValidatorService() {
        formOfAddress = new HashSet<>(List.of(formOfAddressElements));
        title = new HashSet<>(List.of(titleElements));
        maritalStatus = new HashSet<>(List.of(maritalStatusElements));
        employmentStatus = new HashSet<>(List.of(employmentStatusElements));
    }

    /**
     * TODO: Add comment
     *
     * @param customer
     * @throws IllegalArgumentException
     */
    public void validateCustomer(Customer customer) throws IllegalArgumentException {
        if (!this.checkFormOfAddress(customer.getFormOfAddress())) {
            throw new IllegalArgumentException(customer.getFormOfAddress() + " is not a valid argument for formOfAddress!");
        }
        if (!this.checkTitle(customer.getTitle())) {
            throw new IllegalArgumentException(customer.getTitle() + " is not a valid argument for title!");
        }
        if (!this.checkMaritalStatus(customer.getMaritalStatus())) {
            throw new IllegalArgumentException(customer.getMaritalStatus() + " is not a valid argument for maritalStatus!");
        }
        if (!this.checkDateOfBirth(customer.getDateOfBirth())) {
            throw new IllegalArgumentException(customer.getDateOfBirth() + " is not a valid argument for dateOfBirth!");
        }
        if (!this.checkEmploymentStatus(customer.getEmploymentStatus())) {
            throw new IllegalArgumentException(customer.getEmploymentStatus() + " is not a valid argument for employmentStatus!");
        }
        if (!this.checkDogOwner(customer.getDogOwner())) {
            throw new IllegalArgumentException(customer.getDogOwner() + " is not a valid argument for dogOwner!");
        }
        if (!this.checkAddress(customer.getAddress())) {
            throw new IllegalArgumentException(customer.getAddress() + " is not a valid argument for address!");
        }
        if (!this.checkPhoneNumber(customer.getPhoneNumber())) {
            throw new IllegalArgumentException(customer.getPhoneNumber() + " is not a valid argument for phoneNumber!");
        }
        if (!this.checkEmail(customer.getEmail())) {
            throw new IllegalArgumentException(customer.getEmail() + " is not a valid argument for email!");
        }
        if (!this.checkBankDetails(customer.getBankDetails())) {
            throw new IllegalArgumentException(customer.getBankDetails() + " is not a valid argument for bankDetails!");
        }

    }

    private boolean checkFormOfAddress(String formOfAddress) {
        return this.formOfAddress.contains(formOfAddress);
    }

    private boolean checkTitle(String title) {
        return this.title.contains(title);
    }

    private boolean checkMaritalStatus(String maritalStatus) {
        return this.maritalStatus.contains(maritalStatus);
    }

    private boolean checkDateOfBirth(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears() >= 18;
    }

    private boolean checkEmploymentStatus(String employmentStatus) {
        return this.employmentStatus.contains(employmentStatus);
    }

    private boolean checkDogOwner(boolean dogOwner) {
        return true;
    }

    // TODO: Add validation for Address, PhoneNumber, Email and BankDetails
    private boolean checkAddress(Address address) {
        return true;
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        return true;
    }

    private boolean checkEmail(String email) {
        return true;
    }

    private boolean checkBankDetails(String bankDetails) {
        return true;
    }
}
