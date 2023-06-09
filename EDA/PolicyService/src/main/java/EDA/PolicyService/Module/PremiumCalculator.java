package EDA.PolicyService.Module;

import EDA.PolicyService.Persistence.Entity.CatRace;
import EDA.PolicyService.Persistence.Entity.Customer;
import EDA.PolicyService.Persistence.Entity.Policy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

//TODO: Exceptions auslösen

/**
 * This class provides methods to calculate the price of a cat insurance
 * policy based on various factors such as the cat's weight, age, breed,
 * postal code, and owner's status as a dog owner.
 */
public class PremiumCalculator {
    /**
     * Calculates the base price of the cat insurance policy based on the coverage
     * amount and color of the cat.
     *
     * @param policy The policy object containing the coverage amount and object of
     *               insurance (i.e., the cat).
     * @return The base price of the cat insurance policy.
     */
    public static double calculateBasePrice(Policy policy) {
        int coverage = policy.getCoverage();
        double basePrice;
        switch (policy.getObjectOfInsurance().getColor()) {
            case "schwarz": // If the cat is black, increase the insurance price by 0.2%.
                basePrice = 0.002 * coverage;
                break;

            default: // Set the base price as 0.15% of the coverage amount.
                basePrice = 0.0015 * coverage;
        }
        return basePrice;
    }

    /**
     * Calculates the extra price for the cat insurance policy based on the weight of the cat.
     *
     * @param weight  The weight of the cat.
     * @param catRace The cat race object containing information on the average weight of the breed.
     * @return The extra price of the cat insurance policy based on the cat's weight.
     */
    public static double calculateWeightPrice(double weight, CatRace catRace) {
        double extraPrice = 0;
        double lowerAvgWeight = catRace.getLowerAverageWeight();
        double upperAvgWeight = catRace.getUpperAverageWeight();

        // Increase the price by €5 for each kilogram that the cat's weight deviates from the average weight of the breed.
        if (weight < lowerAvgWeight) {
            extraPrice += Math.abs((lowerAvgWeight - weight) * 5);
        } else if (weight > upperAvgWeight) {
            extraPrice += Math.abs((weight - upperAvgWeight) * 5);
        }
        return extraPrice;
    }

    /**
     * Calculates the extra price for the cat insurance policy based on the breed's illness factor.
     *
     * @param catRace The cat race object containing information on the illness factor of the breed.
     * @return The extra price of the cat insurance policy based on the breed's illness factor.
     */
    public static double calculateIllnessFactorPrice(CatRace catRace) {
        // Increase the price by the breed's illness factor value. Every one point more, every Euro more.
        return catRace.getIllnessFactor();
    }


    /**
     * Calculates the extra price for the cat insurance policy based on whether the cat lives outdoors or not.
     *
     * @param environment The environment in which the cat lives.
     * @param basePrice   The base price of the cat insurance policy.
     * @return The extra price of the cat insurance policy based on whether the cat lives outdoors or not.
     */
    public static double calculateEnvironmentPrice(String environment, double basePrice) {
        // Increase the price by 1% of the base price if the cat lives outdoors.
        if (environment.equalsIgnoreCase("draussen")) {
            return 0.1 * basePrice;
        }
        return 0;
    }


    /**
     * Calculates the price increase or decrease based on the age of the cat.
     *
     * @param catBirthDate the birth date of the cat
     * @param catRace      the race of the cat
     * @param basePrice    the base price of the insurance premium
     * @return the price increase or decrease based on the age of the cat
     */
    public static double calculateAgePrice(LocalDate catBirthDate, CatRace catRace, double basePrice) {

        double totalPrice = 0;
        LocalDate currentDate = LocalDate.now();
        long catAgeInYears = ChronoUnit.YEARS.between(catBirthDate, currentDate);
        int catUpperAverageAge = catRace.getUpperAverageAge();
        int catLowerAverageAge = catRace.getLowerAverageAge();
        //Calculate the value of the upper life quartile based on the average age range of the cat breed.
        double upperQuartil = (catUpperAverageAge - catLowerAverageAge) * 0.75 + catLowerAverageAge;

        //If the cat is in the upper quartile of the average age interval at the time of contract
        // conclusion, add 20% of the base price.
        if (catAgeInYears > upperQuartil) {
            totalPrice += 0.2 * basePrice;
        }
        //If the cat is <=2 years old at the time of contract conclusion, subtract 10% of the base price.
        else if (catAgeInYears <= 2) {
            totalPrice -= (basePrice * 0.1);
        }
        return totalPrice;
    }

    /**
     * Calculates the price increase if the cat is not castrated.
     *
     * @param isCastrated indicates whether the cat is castrated
     * @return the price increase if the cat is not castrated
     */
    public static double calculateCastrationPrice(boolean isCastrated) {
        //If the cat is not castrated, add 5€ to the premium.
        if (!isCastrated) {
            return 5;
        }
        return 0;
    }


    /**
     * Calculates the price increase if the owner's postal code starts with 0 or 1.
     *
     * @param postalCode the postal code of the customer
     * @param basePrice  the base price of the insurance premium
     * @return the price increase if the owner's postal code starts with 0 or 1
     */
    public static double calculatePostalCodePrice(String postalCode, double basePrice) {
        //If the owner's postal code starts with 0 or 1, add 5% of the base price to the premium.
        if (postalCode.charAt(0) == '0' || postalCode.charAt(0) == '1') {
            return 0.05 * basePrice;
        }
        return 0;
    }


    /**
     * Calculates the price increase if the customer owns a dog.
     *
     * @param customer  the customer who owns the cat
     * @param basePrice the base price of the insurance premium
     * @return the price increase if the customer owns a dog
     */
    public static double applyDogOwnerSurcharge(Customer customer, double basePrice) {
        //If the customer owns a dog, add 30% of the base price to the premium.
        if (customer.isDogOwner()) {
            return basePrice * 0.3;
        }
        return 0;
    }

    /**
     * This method rounds the given total price to two decimal places and returns the result.
     * <p>
     * Multiply the total price by 100 to move the decimal point two places to the right,
     * cast it to an integer to remove any decimal places, then divide it by 100.0 to
     * move the decimal point back two places to the left and get the rounded result as
     * a double value.
     *
     * @param totalPrice The total price to be rounded to two decimal places.
     * @return The rounded total price as a double value.
     */
    public static double roundToTwoDecimal(double totalPrice) {

        return ((int) (totalPrice * 100)) / 100.0;
    }

}


//RESERVE !! BITTE NICHT ENTFERNEN !! WICHTIG ALS ERSATZ !!
//    public double getPremium(Customer customer, Policy policy) {
//        CatRace catRace = catRaceRepository.findByRace(policy.getObjectOfInsurance().getRace());
//        if (catRace == null) return 0;
//
//        int coverage = policy.getCoverage();
//
//        double basePrice = 0;
//        double totalPrice = 0;
//
//        //TODO: Kommentare in Englisch schreiben
//        switch(policy.getObjectOfInsurance().getColor()){
//            case "schwarz":
//                basePrice = 0.002 * coverage;
//                break;
//            default:
//                basePrice = 0.0015 * coverage;
//        }
//
//        totalPrice = basePrice;
//
//        double weight = policy.getObjectOfInsurance().getWeight();
//        double lowerAvgWeight = catRace.getLowerAverageWeight();
//        double upperAvgWeight = catRace.getUpperAverageWeight();
//        if (weight < lowerAvgWeight) {
//            totalPrice += Math.abs((lowerAvgWeight - weight) * 5);
//        } else if (weight > upperAvgWeight) {
//            totalPrice += Math.abs((weight - upperAvgWeight) * 5);
//        }
//
//        totalPrice += catRace.getIllnessFactor();
//
//        // Ist es eine Draußenkatze, steigere den Preis um 1% des Grundwertes
//        boolean outsideCat = (policy.getObjectOfInsurance().getEnvironment().equalsIgnoreCase("draussen"));
//        if (outsideCat) {
//            totalPrice += 0.1 * basePrice;
//        }
//
//        // Falls die Katze im oberen Quantil des Durchschnittsalters oder älter ist, erhöhe den Preis um 20% des Grundpreises
//        // Ist die Katze Jung, erhöhe den Preis um 5€
//        LocalDate catBirthDate = policy.getObjectOfInsurance().getDateOfBirth();
//        LocalDate currentDate = LocalDate.now();
//        long catAgeInYears = ChronoUnit.YEARS.between(catBirthDate, currentDate);
//        int catUpperAverageAge = catRace.getUpperAverageAge();
//        int catLowerAverageAge = catRace.getLowerAverageAge();
//        //Das ergibt der Wert der oberenQuartil basierend auf die durchschnittlichen Alter einer Katzenrasse.
//        double upperQuartil = (catUpperAverageAge - catLowerAverageAge) * 0.75;
//
//        //Ist die Katze (zum Zeitpunkt des Vertragsschlusses) im oberen Quartil
//        // des Durchschnittsalterintervals, werden 20% des Grundpreises draufgeschlagen.
//        if (catAgeInYears > upperQuartil) {
//            totalPrice += 0.2 * basePrice;
//        } // -10% vom Grundwert,wenn das Alter der Katze (zum Zeitpunkt des Vertragsschlusses) <=2 ist.
//        else if (catAgeInYears <= 2) {
//            totalPrice -= (basePrice * 0.1);
//        }
//
//        // Ist die Katze nicht kastriert, erhöhe den Preis um 5€
//        boolean kastriert = policy.getObjectOfInsurance().isCastrated();
//        if (!kastriert) {
//            totalPrice += 5;
//        }
//
//        // Hat der Besitzer eine PLZ die mit 0 oder 1 startet, erhöhe den Preis um 5% des Grundpreises
//        if (customer.getAddress().getPostalCode() < 20000) {
//            totalPrice += 0.05 * basePrice;
//        }
//
//        // Wenn der besitzer ein Hund hat(Muss noch hinzugefügt werden), muss er 30% des Grundpreises aufgerechnet werden
//        if(customer.isDogOwner()) {
//            totalPrice += 0.3 * basePrice;
//        }
//
//        // Auf 2 Nachkommastellen runden
//        totalPrice = ((int)(totalPrice * 100)) / 100.0;
//        return totalPrice;
//    }