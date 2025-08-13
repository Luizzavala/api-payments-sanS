package me.quadradev.config;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.quadradev.common.exception.ErrorResponse;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI baseOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info().title("API Generic").version("v1"));
  }

  @Bean
  public OpenApiCustomizer registerSchemasAndGlobalErrors() {
    return openApi -> {
      // Ensure components
      Components components = openApi.getComponents();
      if (components == null) {
        components = new Components();
        openApi.setComponents(components);
      }
      // 1) Register ErrorResponse schema in components.schemas
      ModelConverters.getInstance().read(ErrorResponse.class)
          .forEach(components::addSchemas);

      // 2) Add global error responses referencing the registered schema
      if (openApi.getPaths() == null) return;
      openApi.getPaths().values().forEach(pathItem ->
          pathItem.readOperations().forEach(op -> {
            ApiResponses rs = op.getResponses();
            addError(rs, "400", "Solicitud inválida");
            addError(rs, "401", "No autorizado");
            addError(rs, "403", "Prohibido");
            addError(rs, "404", "No encontrado");
            addError(rs, "500", "Error interno");
          })
      );
    };
  }

  private void addError(ApiResponses rs, String code, String desc) {
    if (rs.get(code) != null) return;
    Schema<?> ref = new Schema<>().$ref("#/components/schemas/ErrorResponse");
    MediaType mt = new MediaType().schema(ref);
    Content content = new Content()
        .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, mt);
    rs.addApiResponse(code, new ApiResponse().description(desc).content(content));
  }
}
