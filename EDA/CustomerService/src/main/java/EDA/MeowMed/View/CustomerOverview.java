package EDA.MeowMed.View;

public class CustomerOverview {
    private long id;
    private String firstName;
    private String lastName;
    private AddressWithoutId address;

    public CustomerOverview(long id, String firstName, String lastName, AddressWithoutId address){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public CustomerOverview(){}

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

    public void setAddress(AddressWithoutId address) {
        this.address = address;
    }

    public AddressWithoutId getAddressView(AddressWithoutId address) {
        return address;
    }
}
