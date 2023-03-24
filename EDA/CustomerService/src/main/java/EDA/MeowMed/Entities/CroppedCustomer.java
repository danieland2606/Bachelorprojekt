package EDA.MeowMed.Entities;

import java.util.List;
import java.util.ArrayList;

public class CroppedCustomer {
    private long id;
    private String firstName;
    private String lastName;
    private Address address;

    private CroppedCustomer(){}

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress(Address address) {
        return address;
    }

    public static CroppedCustomer cropCustomer(Customer customer){
        if (customer == null)
            return null;
        CroppedCustomer cropped = new CroppedCustomer();
        cropped.setId(customer.getId());
        cropped.setFirstName(customer.getFirstName());
        cropped.setLastName(customer.getLastName());
        cropped.setAddress(customer.getAddress());
        return cropped;
    }

    public static ArrayList<CroppedCustomer> cropCustomers(List<Customer> customerList){
        if (customerList == null)
            return null;
        ArrayList<CroppedCustomer> croppedList = new ArrayList<>();
        for (Customer customer: customerList) {
            if (customer != null)
                croppedList.add(CroppedCustomer.cropCustomer(customer));
        }
        return croppedList;
    }
}
