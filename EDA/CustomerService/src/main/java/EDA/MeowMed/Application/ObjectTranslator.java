package EDA.MeowMed.Application;

import EDA.MeowMed.Persistence.Entity.Address;
import EDA.MeowMed.Persistence.Entity.Customer;
import EDA.MeowMed.View.NoId_Address;
import EDA.MeowMed.View.Simple_Customer;
import EDA.MeowMed.View.NoId_Customer;

import java.util.ArrayList;
import java.util.List;

public class ObjectTranslator {
    public Simple_Customer customerToSimple_Customer(Customer customer) {
        if (customer == null)
            return null;
        Simple_Customer simpleCustomer = new Simple_Customer();
        simpleCustomer.setId(customer.getId());
        simpleCustomer.setFirstName(customer.getFirstName());
        simpleCustomer.setLastName(customer.getLastName());
        simpleCustomer.setAddress(this.addressToNoId_Address(customer.getAddress()));
        return simpleCustomer;
    }

    public ArrayList<Simple_Customer> customerListToSimple_CustomerList(List<Customer> customerList) {
        if (customerList == null)
            return null;
        ArrayList<Simple_Customer> simpleCustomerList = new ArrayList<>();
        for (Customer customer : customerList) {
            if (customer != null)
                simpleCustomerList.add(this.customerToSimple_Customer(customer));
        }
        return simpleCustomerList;
    }

    public NoId_Address addressToNoId_Address(Address address) {
        if (address == null)
            return null;
        NoId_Address noIdAddress = new NoId_Address();
        noIdAddress.setCity(address.getCity());
        noIdAddress.setStreet(address.getStreet());
        noIdAddress.setPostalCode(address.getPostalCode());
        return noIdAddress;
    }

    public Address noId_AddressToAddress(NoId_Address noIdAddress) {
        if (noIdAddress == null)
            return null;
        Address address = new Address();
        address.setCity(noIdAddress.getCity());
        address.setStreet(noIdAddress.getStreet());
        address.setPostalCode(noIdAddress.getPostalCode());
        return address;
    }

    public NoId_Customer customerToCustomerView(Customer customer) {
        if (customer == null)
            return null;
        NoId_Customer noIdCustomer = new NoId_Customer();
        noIdCustomer.setFirstName(customer.getFirstName());
        noIdCustomer.setLastName(customer.getLastName());
        noIdCustomer.setMartialStatus(customer.getMartialStatus());
        noIdCustomer.setDateOfBirth(customer.getDateOfBirth());
        noIdCustomer.setEmploymentStatus(customer.getEmploymentStatus());
        noIdCustomer.setAddress(this.addressToNoId_Address(customer.getAddress()));
        noIdCustomer.setPhoneNumber(customer.getPhoneNumber());
        noIdCustomer.setEmail(customer.getEmail());
        noIdCustomer.setBankDetails(customer.getBankDetails());
        return noIdCustomer;
    }

    public Customer noId_CustomerToCustomer(NoId_Customer noIdCustomer) {
        if (noIdCustomer == null)
            return null;
        Customer customer = new Customer();
        customer.setFirstName(noIdCustomer.getFirstName());
        customer.setLastName(noIdCustomer.getLastName());
        customer.setMartialStatus(noIdCustomer.getMartialStatus());
        customer.setDateOfBirth(noIdCustomer.getDateOfBirth());
        customer.setEmploymentStatus(noIdCustomer.getEmploymentStatus());
        customer.setAddress(this.noId_AddressToAddress(noIdCustomer.getAddress()));
        customer.setPhoneNumber(noIdCustomer.getPhoneNumber());
        customer.setEmail(noIdCustomer.getEmail());
        customer.setBankDetails(noIdCustomer.getBankDetails());
        return customer;
    }
}
