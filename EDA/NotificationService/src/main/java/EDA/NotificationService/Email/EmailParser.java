package EDA.NotificationService.Email;

import EDA.NotificationService.REST.CatFactResponse;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EmailParser {
    private final Map<String, Function<Map<String, Object>, String>> parser;

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

    private String parseFunFact(Map<String, Object> properties) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject("https://catfact.ninja/fact", CatFactResponse.class).getFact();
        } catch (Exception e) {
            return "Cats walk on their toes."; // Use a default Fact if something goes wrong
        }
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
        return postalCode + " "+ city + ", "+ street;
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
