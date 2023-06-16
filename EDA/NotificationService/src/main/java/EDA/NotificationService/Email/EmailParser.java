package EDA.NotificationService.Email;

import EDA.NotificationService.REST.NotificationController;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EmailParser {

    @Autowired
    private NotificationController notificationController;
    private final Map<String, Function<Map<String, Object>, String>> parser;

    /**
     * Constructs a new EmailParser object.
     * Initializes the parser map by dynamically detecting and adding parse methods.
     */
    public EmailParser() {
        parser = new HashMap<>();
        Arrays.stream(EmailParser.class.getDeclaredMethods())
                .filter(method -> method.getName().contains("parse"))
                .forEach(method -> {
                    String name = method.getName().replace("parse", "");
                    name = String.valueOf(name.charAt(0)).toLowerCase() + name.substring(1);
                    parser.put(name, obj -> {
                        try {
                            return (String) method.invoke(this, obj);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    });
                });
    }

    public Map<String, Function<Map<String, Object>, String>> getParser() {
        return parser;
    }

    /* The parse methods
     * Format:
     * private String parse"NameOfElement"(Map<String, Object> properties){
     *     "ClassOfElement" "NameOfElement" = ("Object to cast to") properties.get("NameOfElement");
     *     // Some operation
     *     return "NameOfElement";
     * }
     */

    private String parsePremium(Map<String, Object> properties) {
        double premium = (double) properties.get("premium");
        String displayCurrency = (String) properties.get("displayCurrency");
        premium = notificationController.convertCurrencyFromEURToTargetCurrency(premium, displayCurrency);
        return premium + " " + displayCurrency;
    }

    private String parseCoverage(Map<String, Object> properties) {
        int coverage = (int) properties.get("coverage");
        String displayCurrency = (String) properties.get("displayCurrency");
        coverage = (int) notificationController.convertCurrencyFromEURToTargetCurrency(coverage, displayCurrency);
        return coverage + " " + displayCurrency;
    }

    private String parseFunFact(Map<String, Object> properties) {
        return notificationController.getRandomCatFact();
    }

    private String parseFormOfAddress(Map<String, Object> properties) {
        String formOfAddress = (String) properties.get("formOfAddress");
        if (formOfAddress.equals("herr"))
            return "Sehr geehrter Herr ";
        else
            return "Sehr geehrte Frau ";
    }

    private String parseAddress(Map<String, Object> properties) {
        String city = (String) properties.get("city");
        String street = (String) properties.get("street");
        String postalCode = (String) properties.get("postalCode");
        return postalCode + " " + city + ", " + street;
    }

    private String parseDogOwner(Map<String, Object> properties) {
        return this.boolToString(properties.get("dogOwner"));
    }

    private String parseDateOfBirth(Map<String, Object> properties) {
        return this.formatDate(properties.get("dateOfBirth"));
    }

    private String parseStartDate(Map<String, Object> properties) {
        return this.formatDate(properties.get("startDate"));
    }

    private String parseEndDate(Map<String, Object> properties) {
        return this.formatDate(properties.get("endDate"));
    }

    private String parseCastrated(Map<String, Object> properties) {
        return this.boolToString(properties.get("castrated"));
    }

    private String boolToString(Object bool) {
        if ((boolean) bool)
            return "Ja";
        return "Nein";
    }

    private String formatDate(Object date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return ((LocalDate) date).format(formatter);
    }
}
