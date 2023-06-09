package EDA.NotificationService.Email;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class EmailFactory {
    @Autowired
    EmailTemplateParser emailTemplateParser;
    @Autowired
    EmailParser emailParser;

    /**
     * Builds an Email object with the specified parameters.
     *
     * @param to       The recipient of the email.
     * @param from     The sender of the email.
     * @param subject  The subject of the email.
     * @param template The template to be used for the email content.
     * @param objects  The objects to extract properties from.
     * @return The built Email object.
     */
    public Email buildEmail(
            String to,
            String from,
            String subject,
            String template,
            Object ... objects
    ) {


        Email email = new Email();
        email.setTo(to);
        email.setFrom(from);
        email.setSubject(subject);
        email.setTemplate(template);
        Map<String, Function<Map<String, Object>, String>> parser = emailParser.getParser();
        Map<String, Object> properties = new HashMap<>();

        for (Object object:objects) {
            properties.putAll(getPropertiesOfObject(object));
        }
        for (String element : emailTemplateParser.getElementsOfTemplate(template)) {
            if (parser.containsKey(element)) {
                properties.put(element, parser.get(element).apply(properties));
            }
        }

        email.setProperties(properties);
        return email;
    }

    /**
     * Transforms the fields of an object and its nested objects recursively,
     * into am Map<"name of field", "value of field"> format, or in other words into its properties.
     *
     * @param object The object to extract properties from.
     * @return The map of properties extracted from the object and its nested objects.
     */
    private Map<String, Object> getPropertiesOfObject(Object object){
        HashMap<String, Object> properties = new HashMap<>();
        try {
            Field[] fields = object.getClass().getDeclaredFields();

            for (Field field:fields) {
                String prefixMethode = "";
                Class<?> fieldType = field.getType();
                if (fieldType.isAssignableFrom(boolean.class)) {
                    prefixMethode="is";
                } else {
                    prefixMethode="get";
                }
                String fieldName = field.getName();
                String getter = prefixMethode + StringUtils.capitalize(fieldName);
                Object value = object.getClass().getMethod(getter).invoke(object);

                if (fieldType.isAssignableFrom(String.class) || fieldType.isAssignableFrom(LocalDate.class)|| fieldType.isAssignableFrom(Long.class) || fieldType.isPrimitive())
                    properties.put(fieldName,value);
                else
                    properties.putAll(getPropertiesOfObject(value));
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
