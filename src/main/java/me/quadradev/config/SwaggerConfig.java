package me.quadradev.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .info(new Info().title("API").version("1.0"));
    }

    @Bean
    public OpenApiCustomizer errorResponses() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation -> {
                    ApiResponses responses = operation.getResponses();
                    responses.addApiResponse("400", errorApiResponse("Bad Request"));
                    responses.addApiResponse("401", errorApiResponse("Unauthorized"));
                    responses.addApiResponse("403", errorApiResponse("Forbidden"));
                    responses.addApiResponse("404", errorApiResponse("Not Found"));
                    responses.addApiResponse("500", errorApiResponse("Internal Server Error"));
                }));
    }

    private ApiResponse errorApiResponse(String description) {
        Schema<?> schema = new Schema<>().$ref("#/components/schemas/ErrorResponse");
        MediaType mediaType = new MediaType().schema(schema);
        Content content = new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, mediaType);
        return new ApiResponse().description(description).content(content);
    }
}
