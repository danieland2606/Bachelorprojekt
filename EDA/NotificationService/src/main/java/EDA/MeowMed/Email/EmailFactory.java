package EDA.MeowMed.Email;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
     * ToDo: get sane
     * Author is very tired, he is eepy. Author has had a very long day of writing code
     * and wants to take just a smol sleeb. he eeby and neebies to sleeby.
     * ithir sleepy and need bed-bye time.
     * author is currently experiencing critical levels of being a sleevjy lil guy and needs to go to bedb.
     * he is ver tired and needs to slep. just a little sleejing time as a treat.
     * ithir neebs to slek, ver twired boyo, just a lil guy. ithir needs his beaty sleeb.
     * look at him go! he yawn big cause he skeejy, neebs to falafel asleep. nini time! good night, mister author.
     *
     * @param to
     * @param from
     * @param subject
     * @param template
     * @param object
     * @return
     */
    public Email buildEmail(
            String to,
            String from,
            String subject,
            String template,
            Object object
    ) {
        Email email = new Email();
        email.setTo(to);
        email.setFrom(from);
        email.setSubject(subject);
        email.setTemplate(template);
        Map<String, Function<Object, String>> parser = emailParser.getParser();
        Map<String, Object> properties = new HashMap<>();
        Class<?> classToParse = object.getClass();
        for (String element : emailTemplateParser.getElementsOfTemplate(template)) {
            try {
                String prefixMethode = "";
                Field field = classToParse.getDeclaredField(element);
                // all Methode have get even if bool because of the way Events are build
//                if (field.getGenericType().getTypeName().equals("boolean")) {
//                    prefixMethode = "is";
//                } else {
                    prefixMethode = "get";
//                }
                String getter = prefixMethode + StringUtils.capitalize(element);
                Object value = classToParse.getMethod(getter).invoke(object);
                if (parser.containsKey(element)) {
                    properties.put(element, parser.get(element).apply(value));
                } else {
                    properties.put(element, value);
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        email.setProperties(properties);
        return email;
    }
}
