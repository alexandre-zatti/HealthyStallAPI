package br.edu.unochapeco.feirinha;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Feirinha Backend API", version = "1.0"))
public class FeirinhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeirinhaApplication.class, args);
	}
}
