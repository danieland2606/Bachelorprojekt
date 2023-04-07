package com.meowmed.rdapolicy.entity;

import java.time.LocalDate;

public class MailPolicyEntity {
    private String firstName;
    private String lastName;
    private String martialStatus;
    private LocalDate dateOfBirth;
    private String employmentStatus;
    private String phoneNumber;
    private String email;
    private String bankDetails;
    private String city;
    private String street;
    private int postalCode;
    private long pid;
    private long cid;
    private LocalDate startDate;
    private LocalDate endDate;
    private int coverage;
    private double premium;
    private String name;
    private String race;
    private String color;
    private LocalDate age;
    private boolean castrated;
    private String personality;
    private String environment;
    private int weight;
    public MailPolicyEntity() {
    }
    public MailPolicyEntity(String firstName, String lastName, String martialStatus, LocalDate dateOfBirth,
            String employmentStatus, String phoneNumber, String email, String bankDetails,
            String city, String street, int postalCode, long pid, long cid, LocalDate startDate, LocalDate endDate,
            int coverage, double premium, String name, String race, String color, LocalDate age, boolean castrated,
            String personality, String environment, int weight) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.martialStatus = martialStatus;
        this.dateOfBirth = dateOfBirth;
        this.employmentStatus = employmentStatus;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankDetails = bankDetails;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.pid = pid;
        this.cid = cid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverage = coverage;
        this.premium = premium;
        this.name = name;
        this.race = race;
        this.color = color;
        this.age = age;
        this.castrated = castrated;
        this.personality = personality;
        this.environment = environment;
        this.weight = weight;
    }
    public MailPolicyEntity(PolicyEntity pEntity, CustomerRequest cRequest){
        this.firstName = cRequest.getFirstName();
        this.lastName = cRequest.getLastName();
        this.martialStatus = cRequest.getMartialStatus();
        this.dateOfBirth = cRequest.getDateOfBirth();
        this.employmentStatus = cRequest.getEmploymentStatus();
        this.phoneNumber = cRequest.getPhoneNumber();
        this.email = cRequest.getEmail();
        this.bankDetails = cRequest.getBankDetails();
        this.city = cRequest.getAdress().getCity();
        this.street = cRequest.getAdress().getStreet();
        this.postalCode = cRequest.getAdress().getPostalCode();
        this.pid = pEntity.getId();
        this.cid = pEntity.getC_id();
        this.startDate = pEntity.getStartDate();
        this.endDate = pEntity.getEndDate();
        this.coverage = pEntity.getCoverage();
        this.premium = pEntity.getPremium();
        this.name = pEntity.getObjectOfInsurance().getName();
        this.race = pEntity.getObjectOfInsurance().getRace();
        this.color = pEntity.getObjectOfInsurance().getColor();
        this.age = pEntity.getObjectOfInsurance().getAge();
        this.castrated = pEntity.getObjectOfInsurance().isCastrated();
        this.personality = pEntity.getObjectOfInsurance().getPersonality();
        this.environment = pEntity.getObjectOfInsurance().getEnviroment();
        this.weight = pEntity.getObjectOfInsurance().getWeight();
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
    public String getMartialStatus() {
        return martialStatus;
    }
    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getEmploymentStatus() {
        return employmentStatus;
    }
    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getBankDetails() {
        return bankDetails;
    }
    public void setBankDetails(String bankDetails) {
        this.bankDetails = bankDetails;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public int getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
    public long getId() {
        return pid;
    }
    public void setId(long pid) {
        this.pid = pid;
    }
    public long getCid() {
        return cid;
    }
    public void setCid(long cid) {
        this.cid = cid;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public int getCoverage() {
        return coverage;
    }
    public void setCoverage(int coverage) {
        this.coverage = coverage;
    }
    public double getPremium() {
        return premium;
    }
    public void setPremium(double premium) {
        this.premium = premium;
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
    public LocalDate getAge() {
        return age;
    }
    public void setAge(LocalDate age) {
        this.age = age;
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
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((martialStatus == null) ? 0 : martialStatus.hashCode());
        result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
        result = prime * result + ((employmentStatus == null) ? 0 : employmentStatus.hashCode());
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((bankDetails == null) ? 0 : bankDetails.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + postalCode;
        result = prime * result + (int) (pid ^ (pid >>> 32));
        result = prime * result + (int) (cid ^ (cid >>> 32));
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + coverage;
        long temp;
        temp = Double.doubleToLongBits(premium);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((race == null) ? 0 : race.hashCode());
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((age == null) ? 0 : age.hashCode());
        result = prime * result + (castrated ? 1231 : 1237);
        result = prime * result + ((personality == null) ? 0 : personality.hashCode());
        result = prime * result + ((environment == null) ? 0 : environment.hashCode());
        result = prime * result + weight;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MailPolicyEntity other = (MailPolicyEntity) obj;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (martialStatus == null) {
            if (other.martialStatus != null)
                return false;
        } else if (!martialStatus.equals(other.martialStatus))
            return false;
        if (dateOfBirth == null) {
            if (other.dateOfBirth != null)
                return false;
        } else if (!dateOfBirth.equals(other.dateOfBirth))
            return false;
        if (employmentStatus == null) {
            if (other.employmentStatus != null)
                return false;
        } else if (!employmentStatus.equals(other.employmentStatus))
            return false;
        if (phoneNumber == null) {
            if (other.phoneNumber != null)
                return false;
        } else if (!phoneNumber.equals(other.phoneNumber))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (bankDetails == null) {
            if (other.bankDetails != null)
                return false;
        } else if (!bankDetails.equals(other.bankDetails))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        if (postalCode != other.postalCode)
            return false;
        if (pid != other.pid)
            return false;
        if (cid != other.cid)
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        if (coverage != other.coverage)
            return false;
        if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (race == null) {
            if (other.race != null)
                return false;
        } else if (!race.equals(other.race))
            return false;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        if (age == null) {
            if (other.age != null)
                return false;
        } else if (!age.equals(other.age))
            return false;
        if (castrated != other.castrated)
            return false;
        if (personality == null) {
            if (other.personality != null)
                return false;
        } else if (!personality.equals(other.personality))
            return false;
        if (environment == null) {
            if (other.environment != null)
                return false;
        } else if (!environment.equals(other.environment))
            return false;
        if (weight != other.weight)
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "MailEntity [firstName=" + firstName + ", lastName=" + lastName + ", martialStatus=" + martialStatus
                + ", dateOfBirth=" + dateOfBirth + ", employmentStatus=" + employmentStatus + ", phoneNumber=" 
                + phoneNumber + ", email=" + email + ", bankDetails=" + bankDetails + ", city="
                + city + ", street=" + street + ", postalCode=" + postalCode + ", pid=" + pid + ", cid=" + cid
                + ", startDate=" + startDate + ", endDate=" + endDate + ", coverage=" + coverage + ", premium="
                + premium + ", name=" + name + ", race=" + race + ", color=" + color + ", age=" + age + ", castrated="
                + castrated + ", personality=" + personality + ", environment=" + environment + ", weight=" + weight
                + "]";
    }

    
}
