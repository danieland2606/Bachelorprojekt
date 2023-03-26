package EDA.MeowMed;

import EDA.MeowMed.Policy.*;
import EDA.MeowMed.Policy.View.PolicyView;
import EDA.MeowMed.Policy.View.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
public class MeowMedApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeowMedApplication.class, args);
	}
}
