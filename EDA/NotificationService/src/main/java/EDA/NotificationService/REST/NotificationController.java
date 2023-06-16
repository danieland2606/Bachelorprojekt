package EDA.NotificationService.REST;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class NotificationController {
    public NotificationController() {
    }

    public String getRandomCatFact() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity("https://catfact.ninja/fact", String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            return root.path("fact").asText();

        } catch (JsonProcessingException e) {
            return "Cats walk on their toes."; // Use a default Fact if something goes wrong
        }
    }

    public double convertCurrencyFromEURToTargetCurrency(double currency, String targetCurrency) {
        try {
            String baseurl = "https://api.exchangerate.host/convert?from=EUR&to=" + targetCurrency;

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(baseurl, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            return currency * root.path("result").asDouble();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
