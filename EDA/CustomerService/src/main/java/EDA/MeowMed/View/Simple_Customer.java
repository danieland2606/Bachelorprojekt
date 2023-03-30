package EDA.MeowMed.View;

public class Simple_Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private NoId_Address address;

    public Simple_Customer(Long id, String firstName, String lastName, NoId_Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Simple_Customer() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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

    public void setAddress(NoId_Address address) {
        this.address = address;
    }

    public NoId_Address getAddressView(NoId_Address address) {
        return address;
    }
}
