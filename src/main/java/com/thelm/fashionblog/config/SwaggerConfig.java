package com.thelm.fashionblog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "springdoc.swagger-ui.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {
    @Value("${api.info.title: api.info.title}")
    private String title;
    @Value("${api.info.description: api.info.description}")
    private String description;
    @Value("${api.info.version: api.info.version}")
    private String version;
    @Value("${api.info.term-of-service: api.info.terms-of-service}")
    private String termOfService;
    @Value("${api.info.contact.name: api.info.contact.name}")
    private String contactName;
    @Value("${api.info.contact.email: api.info.contact.email}")
    private String contactEmail;
    @Value("${api.info.contact.url: api.info.contact.url}")
    private String contactUrl;
    @Value("${api.info.licence.name: api.info.licence.name}")
    private String licenceName;
    @Value("${api.info.licence.url: api.info.licence.url}")
    private String licenceUrl;
    @Bean
    public OpenAPI productApi() {
        return new OpenAPI()
                .info(getApiInfo());
    }
    private Info getApiInfo() {
        Contact contact = new Contact().name(contactName).email(contactEmail).url(contactUrl);
        License licence = new License().name(licenceName).url(licenceUrl);
        return new Info()
                .title(title)
                .description(description)
                .version(version)
                .contact(contact)
                .license(licence);
    }
}
