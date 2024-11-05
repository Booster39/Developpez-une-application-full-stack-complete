package com.openclassrooms.mddapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MddApiApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();  // Charge les variables du fichier .env
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());  // Définit chaque variable en tant que propriété système
		});
		SpringApplication.run(MddApiApplication.class, args);
	}
}
