package EDA.MeowMed.Rest;

import java.time.LocalDate;

public class PremiumCalculationData {
    private long customerID;

    private int coverage;

    private String race;

    private String color;

    private LocalDate dateOfBirth;

    private boolean castrated;

    private String personality;

    private String environment;

    private double weight;

    public PremiumCalculationData(long customerID, int coverage, String race, String color, LocalDate dateOfBirth, boolean castrated, String personality, String environment, double weight) {
        this.customerID = customerID;
        this.coverage = coverage;
        this.race = race;
        this.color = color;
        this.dateOfBirth = dateOfBirth;
        this.castrated = castrated;
        this.personality = personality;
        this.environment = environment;
        this.weight = weight;
    }

    public PremiumCalculationData() {

    }

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public int getCoverage() {
        return coverage;
    }

    public void setCoverage(int coverage) {
        this.coverage = coverage;
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
