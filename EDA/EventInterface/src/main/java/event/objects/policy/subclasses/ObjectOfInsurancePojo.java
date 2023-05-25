package event.objects.policy.subclasses;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class ObjectOfInsurancePojo implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    private String name;

    private String race;

    private String color;

    private LocalDate dateOfBirth;

    private boolean castrated;

    private String personality;

    private String environment;

    private double weight;

    public ObjectOfInsurancePojo(String name, String race, String color, LocalDate dateOfBirth, boolean castrated, String personality, String environment, double weight) {
        this.name = name;
        this.race = race;
        this.color = color;
        this.dateOfBirth = dateOfBirth;
        this.castrated = castrated;
        this.personality = personality;
        this.environment = environment;
        this.weight = weight;
    }

    public ObjectOfInsurancePojo() {

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

    public boolean getCastrated() {
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
