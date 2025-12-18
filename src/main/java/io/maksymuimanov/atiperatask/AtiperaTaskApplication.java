package io.maksymuimanov.atiperatask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AtiperaTaskApplication {
	static void main(String[] args) {
		SpringApplication.run(AtiperaTaskApplication.class, args);
	}
}
