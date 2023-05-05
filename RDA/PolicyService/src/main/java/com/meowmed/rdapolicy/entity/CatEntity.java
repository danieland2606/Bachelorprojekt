package com.meowmed.rdapolicy.entity;

import java.util.Arrays;

import org.springframework.stereotype.Repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Repository
@Entity
@Table(name="Cat")
public class CatEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="Cat_ID")
    private long id;

    private String race;
    private int lowerAverageAge;
    private int upperAverageAge;
    private int lowerAverageWeight;
    private int upperAverageWeight;
    private int illnessFactor;
    //TODO: Checken, warum Mustafa hier ein IDE-Fehler hat
    private String[] possibleColors;
    
    public CatEntity() {
    }
    public CatEntity(String race, int lowerAverageAge, int upperAverageAge, int lowerAverageWeight,
            int upperAverageWeight, int illnessFactor, String[] possibleColors) {
        this.race = race;
        this.lowerAverageAge = lowerAverageAge;
        this.upperAverageAge = upperAverageAge;
        this.lowerAverageWeight = lowerAverageWeight;
        this.upperAverageWeight = upperAverageWeight;
        this.illnessFactor = illnessFactor;
        this.possibleColors = possibleColors;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getLowerAverageAge() {
        return lowerAverageAge;
    }

    public void setLowerAverageAge(int lowerAverageAge) {
        this.lowerAverageAge = lowerAverageAge;
    }

    public int getUpperAverageAge() {
        return upperAverageAge;
    }

    public void setUpperAverageAge(int upperAverageAge) {
        this.upperAverageAge = upperAverageAge;
    }

    public int getLowerAverageWeight() {
        return lowerAverageWeight;
    }

    public void setLowerAverageWeight(int lowerAverageWeight) {
        this.lowerAverageWeight = lowerAverageWeight;
    }

    public int getUpperAverageWeight() {
        return upperAverageWeight;
    }

    public void setUpperAverageWeight(int upperAverageWeight) {
        this.upperAverageWeight = upperAverageWeight;
    }

    public int getIllnessFactor() {
        return illnessFactor;
    }

    public void setIllnessFactor(int illnessFactor) {
        this.illnessFactor = illnessFactor;
    }

    public String[] getPossibleColors() {
        return possibleColors;
    }

    public void setPossibleColors(String[] possibleColors) {
        this.possibleColors = possibleColors;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((race == null) ? 0 : race.hashCode());
        result = prime * result + lowerAverageAge;
        result = prime * result + upperAverageAge;
        result = prime * result + lowerAverageWeight;
        result = prime * result + upperAverageWeight;
        result = prime * result + illnessFactor;
        result = prime * result + Arrays.hashCode(possibleColors);
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
        CatEntity other = (CatEntity) obj;
        if (race == null) {
            if (other.race != null)
                return false;
        } else if (!race.equals(other.race))
            return false;
        if (lowerAverageAge != other.lowerAverageAge)
            return false;
        if (upperAverageAge != other.upperAverageAge)
            return false;
        if (lowerAverageWeight != other.lowerAverageWeight)
            return false;
        if (upperAverageWeight != other.upperAverageWeight)
            return false;
        if (illnessFactor != other.illnessFactor)
            return false;
        if (!Arrays.equals(possibleColors, other.possibleColors))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CatEntity [race=" + race + ", lowerAverageAge=" + lowerAverageAge + ", upperAverageAge="
                + upperAverageAge + ", lowerAverageWeight=" + lowerAverageWeight + ", upperAverageWeight="
                + upperAverageWeight + ", illnessFactor=" + illnessFactor + ", possibleColors="
                + Arrays.toString(possibleColors) + "]";
    }
}
