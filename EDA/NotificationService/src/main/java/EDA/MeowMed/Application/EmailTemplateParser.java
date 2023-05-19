package EDA.MeowMed.Application;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class EmailTemplateParser {
    private Map<String, Set<String>> emailTemplateObjects;

    /**
     * Build a Map for each template found in MailTemplates and collects all variables in the template.
     */
    private EmailTemplateParser() {
        emailTemplateObjects = new HashMap<>();
        try {
            // Stream of all files within the MailTemplates directory
            Files.walk(Paths.get("./NotificationService/src/main/resources/MailTemplates"))
                    // only files, no directory's
                    .filter(Files::isRegularFile)
                    // only .html files
                    .filter(s -> s.getFileName().toString().endsWith(".html"))
                    // Find all variables in the .html file, variables are designated by ${variable}
                    .forEach(path -> {
                        try {
                            String file = Files.readString(path);
                            emailTemplateObjects.put(
                                    // only filename
                                    path.getFileName().toString().replace(".html", ""),
                                    // set of variables
                                    Set.of(StringUtils.substringsBetween(file, "${", "}")));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public EmailTemplateParser emailTemplateParser() {
        return new EmailTemplateParser();
    }

    public Set<String> getElementsOfTemplate(String template) {
        return emailTemplateObjects.get(template);
    }
}
