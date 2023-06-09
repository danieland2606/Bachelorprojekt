package EDA.NotificationService.Persistence.Entity;

import event.objects.policy.subclasses.ObjectOfInsuranceEvent;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="ObjectOfInsurance")
public class ObjectOfInsurance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "race", nullable = false)
    private String race;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;


    @Column(name = "castrated", nullable = false)
    private boolean castrated;

    @Column(name = "personality", nullable = false)
    private String personality;

    @Column(name = "environment", nullable = false)
    private String environment;


    @Column(name = "weight", nullable = false)
    private double weight;

    public ObjectOfInsurance(String name, String race, String color, LocalDate dateOfBirth, boolean castrated, String personality, String environment, double weight) {
        this.name = name;
        this.race = race;
        this.color = color;
        this.dateOfBirth = dateOfBirth;
        this.castrated = castrated;
        this.personality = personality;
        this.environment = environment;
        this.weight = weight;
    }

    public ObjectOfInsurance(ObjectOfInsurance objectOfInsurance) {
        this.id = objectOfInsurance.getId();
        this.name = objectOfInsurance.getName();
        this.race = objectOfInsurance.getRace();
        this.color = objectOfInsurance.getColor();
        this.dateOfBirth = objectOfInsurance.getDateOfBirth();
        this.castrated = objectOfInsurance.isCastrated();
        this.personality = objectOfInsurance.getPersonality();
        this.environment = objectOfInsurance.getEnvironment();
        this.weight = objectOfInsurance.getWeight();
    }

    public ObjectOfInsurance() {

    }


    public ObjectOfInsurance(ObjectOfInsuranceEvent objectOfInsuranceEvent) {
        this.name = objectOfInsuranceEvent.getName();
        this.race = objectOfInsuranceEvent.getRace();
        this.color = objectOfInsuranceEvent.getColor();
        this.dateOfBirth = objectOfInsuranceEvent.getDateOfBirth();
        this.castrated = objectOfInsuranceEvent.isCastrated();
        this.personality = objectOfInsuranceEvent.getPersonality();
        this.environment = objectOfInsuranceEvent.getEnvironment();
        this.weight = objectOfInsuranceEvent.getWeight();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isCastrated() {
        return castrated;
    }

    public void setCastrated(boolean castrated) {
        this.castrated = castrated;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
