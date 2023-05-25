package event.objects.policy;

import event.objects.policy.subclasses.PolicyPojo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class PolicyCreatedEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    private PolicyPojo policy;

    public PolicyCreatedEvent(PolicyPojo policy) {
        this.policy = policy;
    }

    public PolicyPojo getPolicy() {
        return this.policy;
    }

    public void setPolicy(PolicyPojo policy) {
        this.policy = policy;
    }

    public Long getCid() {
        return policy.getCustomer().getId();
    }

    public void setCid(Long id) {
        policy.getCustomer().setId(id);
    }

    public String getFirstName() {
        return policy.getCustomer().getFirstName();
    }

    public void setFirstName(String firstName) {
        policy.getCustomer().setFirstName(firstName);
    }

    public String getLastName() {
        return policy.getCustomer().getLastName();
    }

    public void setLastName(String lastName) {
        policy.getCustomer().setFirstName(lastName);
    }

    public String getFormOfAddress() {
        return policy.getCustomer().getFormOfAddress();
    }

    public void setFormOfAddress(String formOfAddress) {
        policy.getCustomer().setFirstName(formOfAddress);
    }

    public String getEmail() {
        return policy.getCustomer().getEmail();
    }

    public void setEmail(String email) {
        policy.getCustomer().setFirstName(email);
    }

    public long getPid() {
        return policy.getId();
    }

    public void setPid(long id) {
        policy.setId(id);
    }

    public LocalDate getStartDate() {
        return policy.getStartDate();
    }

    public void setStartDate(LocalDate startDate) {
        policy.setStartDate(startDate);
    }

    public LocalDate getEndDate() {
        return policy.getEndDate();
    }

    public void setEndDate(LocalDate endDate) {
        policy.setEndDate(endDate);
    }

    public int getCoverage() {
        return policy.getCoverage();
    }

    public void setCoverage(int coverage) {
        policy.setCoverage(coverage);
    }

    public double getPremium() {
        return policy.getPremium();
    }

    public void setPremium(double premium) {
        policy.setPremium(premium);
    }

    public String getName() {
        return policy.getObjectOfInsurance().getName();
    }

    public void setName(String name) {
        policy.getObjectOfInsurance().setName(name);
    }

    public String getRace() {
        return policy.getObjectOfInsurance().getRace();
    }

    public void setRace(String race) {
        policy.getObjectOfInsurance().setRace(race);
    }

    public String getColor() {
        return policy.getObjectOfInsurance().getColor();
    }

    public void setColor(String color) {
        policy.getObjectOfInsurance().setColor(color);
    }

    public LocalDate getDateOfBirth() {
        return policy.getObjectOfInsurance().getDateOfBirth();
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        policy.getObjectOfInsurance().setDateOfBirth(dateOfBirth);
    }

    public boolean getCastrated() {
        return policy.getObjectOfInsurance().getCastrated();
    }

    public void setCastrated(boolean castrated) {
        policy.getObjectOfInsurance().setCastrated(castrated);
    }

    public String getPersonality() {
        return policy.getObjectOfInsurance().getPersonality();
    }

    public void setPersonality(String personality) {
        policy.getObjectOfInsurance().setPersonality(personality);
    }

    public String getEnvironment() {
        return policy.getObjectOfInsurance().getEnvironment();
    }

    public void setEnvironment(String environment) {
        policy.getObjectOfInsurance().setEnvironment(environment);
    }

    public double getWeight() {
        return policy.getObjectOfInsurance().getWeight();
    }

    public void setWeight(double weight) {
        policy.getObjectOfInsurance().setWeight(weight);
    }
}
