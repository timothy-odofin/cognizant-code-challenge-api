package cognizant.com.codechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value="file:C:\\Users\\JIDE\\Documents\\fordsoft\\cognizant\\configs\\application.properties", ignoreResourceNotFound = true)
public class CodeChallengeApplication {
	public static void main(String[] args) {
		SpringApplication.run(CodeChallengeApplication.class, args);
	}

}
