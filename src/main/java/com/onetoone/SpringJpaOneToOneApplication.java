package com.onetoone;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "one-to-one-Mapping",
				description = "operations performed on one-one mappings",
				termsOfService="All terms and conditions apply",
				contact=@Contact(
						name = "saikumar",
						url = "https://github.com/saikumar972",
						email = "saikumarkatta971@gmail.com"
				),
				license=@License(
						name="one-one"
				),
				version = "3.4.3"
		),
		servers = {
				@Server(
						url = "http://localhost:9090",
						description = "swagger-doc"
				)
		}

)
public class SpringJpaOneToOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaOneToOneApplication.class, args);
	}

}
