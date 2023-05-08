package com.meowmed.rdapolicy.entity;

import java.time.LocalDate;

public class MailPolicyEntity {
    private String firstName;
    private String lastName;
    private String title;
    private String formOfAdress;
    private String maritalStatus;
    private LocalDate cDateOfBirth;
    private String employmentStatus;
    private String city;
    private String street;
    private String postalCode;
    private String phoneNumber;
    private String email;
    private String bankDetails;
    private boolean dogOwner;
    private long pid;
    private long cid;
    private LocalDate startDate;
    private LocalDate endDate;
    private int coverage;
    private double premium;
    private String name;
    private String race;
    private String color;
    private LocalDate oDateOfBirth;
    private boolean castrated;
    private String personality;
    private String environment;
    private int weight;
    public MailPolicyEntity() {
    }
    public MailPolicyEntity(String firstName, String lastName, String title, String formOfAdress, String maritalStatus,
            LocalDate cDateOfBirth, String employmentStatus, String city, String street, String postalCode,
            String phoneNumber, String email, String bankDetails, boolean dogOwner, long pid, long cid, LocalDate startDate,
            LocalDate endDate, int coverage, double premium, String name, String race, String color,
            LocalDate oDateOfBirth, boolean castrated, String personality, String environment, int weight) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.formOfAdress = formOfAdress;
        this.maritalStatus = maritalStatus;
        this.cDateOfBirth = cDateOfBirth;
        this.employmentStatus = employmentStatus;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankDetails = bankDetails;
        this.dogOwner = dogOwner;
        this.pid = pid;
        this.cid = cid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverage = coverage;
        this.premium = premium;
        this.name = name;
        this.race = race;
        this.color = color;
        this.oDateOfBirth = oDateOfBirth;
        this.castrated = castrated;
        this.personality = personality;
        this.environment = environment;
        this.weight = weight;
    }
    public MailPolicyEntity(PolicyEntity entity, CustomerRequest cRequest){
        this.firstName = cRequest.getFirstName();
        this.lastName = cRequest.getLastName();
        this.title = cRequest.getTitle();
        this.formOfAdress = cRequest.getFormOfAdress();
        this.maritalStatus = cRequest.getMaritalStatus();
        this.cDateOfBirth = cRequest.getDateOfBirth();
        this.employmentStatus = cRequest.getEmploymentStatus();
        this.city = cRequest.getAddress().getCity();
        this.street = cRequest.getAddress().getStreet();
        this.postalCode = cRequest.getAddress().getPostalCode();
        this.phoneNumber = cRequest.getPhoneNumber();
        this.email = cRequest.getEmail();
        this.bankDetails = cRequest.getBankDetails();
        this.dogOwner = cRequest.isdogOwner();
        this.pid = entity.getId();
        this.cid = entity.getC_id();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.coverage = entity.getCoverage();
        this.premium = entity.getPremium();
        this.name = entity.getObjectOfInsurance().getName();
        this.race = entity.getObjectOfInsurance().getRace();
        this.color = entity.getObjectOfInsurance().getColor();
        this.oDateOfBirth = entity.getObjectOfInsurance().getDateOfBirth();
        this.castrated = entity.getObjectOfInsurance().isCastrated();
        this.personality = entity.getObjectOfInsurance().getPersonality();
        this.environment = entity.getObjectOfInsurance().getEnvironment();
        this.weight = entity.getObjectOfInsurance().getWeight();
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getFormOfAdress() {
        return formOfAdress;
    }
    public void setFormOfAdress(String formOfAdress) {
        this.formOfAdress = formOfAdress;
    }
    public String getMaritalStatus() {
        return maritalStatus;
    }
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    public LocalDate getcDateOfBirth() {
        return cDateOfBirth;
    }
    public void setcDateOfBirth(LocalDate cDateOfBirth) {
        this.cDateOfBirth = cDateOfBirth;
    }
    public String getEmploymentStatus() {
        return employmentStatus;
    }
    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
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
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
    public boolean isDogOwner() {
        return dogOwner;
    }
    public void setDogOwner(boolean dogOwner) {
        this.dogOwner = dogOwner;
    }
    public long getPid() {
        return pid;
    }
    public void setPid(long pid) {
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
    public LocalDate getoDateOfBirth() {
        return oDateOfBirth;
    }
    public void setoDateOfBirth(LocalDate oDateOfBirth) {
        this.oDateOfBirth = oDateOfBirth;
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
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((formOfAdress == null) ? 0 : formOfAdress.hashCode());
        result = prime * result + ((maritalStatus == null) ? 0 : maritalStatus.hashCode());
        result = prime * result + ((cDateOfBirth == null) ? 0 : cDateOfBirth.hashCode());
        result = prime * result + ((employmentStatus == null) ? 0 : employmentStatus.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((bankDetails == null) ? 0 : bankDetails.hashCode());
        result = prime * result + (dogOwner ? 1231 : 1237);
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
        result = prime * result + ((oDateOfBirth == null) ? 0 : oDateOfBirth.hashCode());
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
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (formOfAdress == null) {
            if (other.formOfAdress != null)
                return false;
        } else if (!formOfAdress.equals(other.formOfAdress))
            return false;
        if (maritalStatus == null) {
            if (other.maritalStatus != null)
                return false;
        } else if (!maritalStatus.equals(other.maritalStatus))
            return false;
        if (cDateOfBirth == null) {
            if (other.cDateOfBirth != null)
                return false;
        } else if (!cDateOfBirth.equals(other.cDateOfBirth))
            return false;
        if (employmentStatus == null) {
            if (other.employmentStatus != null)
                return false;
        } else if (!employmentStatus.equals(other.employmentStatus))
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
        if (dogOwner != other.dogOwner)
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
        if (oDateOfBirth == null) {
            if (other.oDateOfBirth != null)
                return false;
        } else if (!oDateOfBirth.equals(other.oDateOfBirth))
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
        return "MailPolicyEntity [firstName=" + firstName + ", lastName=" + lastName + ", title=" + title
                + ", formOfAdress=" + formOfAdress + ", maritalStatus=" + maritalStatus + ", cDateOfBirth="
                + cDateOfBirth + ", employmentStatus=" + employmentStatus + ", city=" + city + ", street=" + street
                + ", postalCode=" + postalCode + ", phoneNumber=" + phoneNumber + ", email=" + email + ", bankDetails="
                + bankDetails + ", dogOwner=" + dogOwner + ", pid=" + pid + ", cid=" + cid + ", startDate=" + startDate
                + ", endDate=" + endDate + ", coverage=" + coverage + ", premium=" + premium + ", name=" + name
                + ", race=" + race + ", color=" + color + ", oDateOfBirth=" + oDateOfBirth + ", castrated=" + castrated
                + ", personality=" + personality + ", environment=" + environment + ", weight=" + weight + "]";
    }
        
}
