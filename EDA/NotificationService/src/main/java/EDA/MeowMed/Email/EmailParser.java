package EDA.MeowMed.Email;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EmailParser {
    private final Map<String, Function<Object, String>> parser;

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

    public Map<String, Function<Object, String>> getParser() {
        return parser;
    }

    private String parseFunFact(Object ignore){
        //ToDo: Hier aufruf für random Funfact über katzen einfügen
        return "ToDo: Fun Fact";
    }

    private String parseFormOfAddress(Object formOfAddress) {
        if (formOfAddress.equals("herr"))
            return "Sehr geehrter Herr ";
        else
            return "Sehr geehrte Frau ";
    }

    private String parseAddress(Object address) {
        return address.toString();
    }

    private String parseDogOwner(Object bool) {
        return this.boolToString(bool);
    }

    private String parseDateOfBirth(Object date) {
        return this.formatDate(date);
    }

    private String parseStartDate(Object date) {
        return this.formatDate(date);
    }

    private String parseEndDate(Object date) {
        return this.formatDate(date);
    }

    private String parseCastrated(Object bool) {
        return this.boolToString(bool);
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
