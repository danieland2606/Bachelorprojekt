package EDA.MeowMed.Application;

import EDA.MeowMed.Persistence.Entity.Address;
import EDA.MeowMed.Persistence.Entity.Customer;
import EDA.MeowMed.View.AddressWithoutId;
import EDA.MeowMed.View.CustomerOverview;
import EDA.MeowMed.View.CustomerView;
import EDA.MeowMed.View.NewCustomer;

import java.util.ArrayList;
import java.util.List;

public class ObjectTranslator {
    public static CustomerOverview customerToCustomerOveriew(Customer customer){
        if (customer == null)
            return null;
        CustomerOverview customerOverview = new CustomerOverview();
        customerOverview.setId(customer.getId());
        customerOverview.setFirstName(customer.getFirstName());
        customerOverview.setLastName(customer.getLastName());
        customerOverview.setAddress(ObjectTranslator.addressToAddresswithoutId(customer.getAddress()));
        return customerOverview;
    }

    public static ArrayList<CustomerOverview> customerListToCustomerOverviewList(List<Customer> customerList){
        if (customerList == null)
            return null;
        ArrayList<CustomerOverview> customerOverviewList = new ArrayList<>();
        for (Customer customer: customerList) {
            if (customer != null)
                customerOverviewList.add(ObjectTranslator.customerToCustomerOveriew(customer));
        }
        return customerOverviewList;
    }

    public static AddressWithoutId addressToAddresswithoutId(Address address){
        if (address == null)
            return null;
        AddressWithoutId addressOverview = new AddressWithoutId();
        addressOverview.setCity(address.getCity());
        addressOverview.setStreet(address.getStreet());
        addressOverview.setPostalCode(address.getPostalCode());
        return addressOverview;
    }

    public static CustomerView customerToCustomerView(Customer customer) {
        if (customer == null)
            return null;
        CustomerView customerView = new CustomerView();
        customerView.setFirstName(customer.getFirstName());
        customerView.setLastName(customer.getLastName());
        customerView.setMartialStatus(customer.getMartialStatus());
        customerView.setDateOfBirth(customer.getDateOfBirth());
        customerView.setEmploymentStatus(customer.getEmploymentStatus());
        customerView.setAddress(ObjectTranslator.addressToAddresswithoutId(customer.getAddress()));
        customerView.setPhoneNumber(customer.getPhoneNumber());
        customerView.setEmail(customer.getEmail());
        customerView.setBankDetails(customer.getBankDetails());
        return customerView;
    }
    public static Customer newCustomerToCustomer(NewCustomer newCustomer){
        if (newCustomer==null)
            return null;
        Customer customer = new Customer();
        return customer;
    }
}
