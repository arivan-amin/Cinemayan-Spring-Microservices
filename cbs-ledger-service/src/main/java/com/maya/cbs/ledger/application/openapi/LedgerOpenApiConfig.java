package com.maya.cbs.ledger.application.openapi;

import com.maya.cbs.core.application.config.OpenApiServerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.maya.cbs.core.application.openapi.OpenApiDetails.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
class LedgerOpenApiConfig {

    private final OpenApiServerProperties serverProperties;

    @Bean
    public OpenAPI myOpenAPI () {
        log.info("Ledger OpenApiServerProperties fetched from config file = {}", serverProperties);
        Server server = new Server();
        server.setUrl(serverProperties.url());
        server.setDescription("Server URL");

        Info info = new Info().title("Ledger Service API")
            .description("Provides all the API related to Ledger service")
            .version("1.0")
            .contact(getOpenApiContactDetails())
            .termsOfService(getOpenApiTermsOfService())
            .license(getOpenApiLicence());

        return new OpenAPI().info(info)
            .servers(List.of(server));
    }
}
