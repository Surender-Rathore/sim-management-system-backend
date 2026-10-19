package com.example.simmanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "SIM Card Management API",
                version = "1.0.0",
                description = "CRUD API for managing SIM cards (ICCID, IMSI, MSISDN, status, assignment).",
                contact = @Contact(name = "SIM Platform Team", email = "sim-platform@example.com"),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local")
        }
)
public class OpenApiConfig {
}
