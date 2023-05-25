package event.objects.policy.subclasses;

import java.io.Serial;
import java.io.Serializable;

/**
 * Object with select customer information
 * Can be serialized and sent as event payload
 */
public class CustomerPojo implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;

    private Long id;
    private String firstName;
    private String lastName;
    private String formOfAddress;
    private String email;

    public CustomerPojo() {
    }

    public CustomerPojo(Long id, String firstName, String lastName, String formOfAddress, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.formOfAddress = formOfAddress;
        this.email = email;
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

    public String getFormOfAddress() {return formOfAddress;}

    public void setFormOfAddress(String formOfAddress) {this.formOfAddress = formOfAddress;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
