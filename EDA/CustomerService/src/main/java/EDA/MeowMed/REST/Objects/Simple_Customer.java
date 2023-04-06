package EDA.MeowMed.REST.Objects;

import EDA.MeowMed.Persistence.Entity.Customer;

public class Simple_Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private View_Address address;

    public Simple_Customer() {
    }

    public Simple_Customer(Long id, String firstName, String lastName, View_Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Simple_Customer(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.address = new View_Address(customer.getAddress());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public View_Address getAddress() {
        return address;
    }

    public void setAddress(View_Address address) {
        this.address = address;
    }
}

