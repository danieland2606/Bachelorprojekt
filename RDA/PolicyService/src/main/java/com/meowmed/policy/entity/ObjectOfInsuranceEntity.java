package com.meowmed.policy.entity;

import java.time.LocalDate;

//@Entity 
public class ObjectOfInsuranceEntity {
    private String name;
    private String race;
    private String color;
    private LocalDate age;
    private boolean castrated;
    private String personality;
    private String enviroment;
    private int weight;

    public ObjectOfInsuranceEntity() {
    }

    public ObjectOfInsuranceEntity(String name, String race, String color, LocalDate age, boolean castrated,
            String personality, String enviroment, int weight) {
        this.name = name;
        this.race = race;
        this.color = color;
        this.age = age;
        this.castrated = castrated;
        this.personality = personality;
        this.enviroment = enviroment;
        this.weight = weight;
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

    public String getEnviroment() {
        return enviroment;
    }

    public void setEnviroment(String enviroment) {
        this.enviroment = enviroment;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ObjectOfInsuranceEntity [name=" + name + ", race=" + race + ", color=" + color + ", age=" + age
                + ", castrated=" + castrated + ", personality=" + personality + ", enviroment=" + enviroment
                + ", weight=" + weight + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((race == null) ? 0 : race.hashCode());
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((age == null) ? 0 : age.hashCode());
        result = prime * result + (castrated ? 1231 : 1237);
        result = prime * result + ((personality == null) ? 0 : personality.hashCode());
        result = prime * result + ((enviroment == null) ? 0 : enviroment.hashCode());
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
        ObjectOfInsuranceEntity other = (ObjectOfInsuranceEntity) obj;
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
        if (enviroment == null) {
            if (other.enviroment != null)
                return false;
        } else if (!enviroment.equals(other.enviroment))
            return false;
        if (weight != other.weight)
            return false;
        return true;
    }


    
}