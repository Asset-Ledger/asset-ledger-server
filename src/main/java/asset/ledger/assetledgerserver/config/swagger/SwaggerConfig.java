package asset.ledger.assetledgerserver.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Asset Ledger Server API",
                description = "Asset Ledger Server API 문서 입니다"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Default Local Server Url")
        }
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("Ledger API")
                .pathsToMatch("/ledger/**")
                .build();
    }

//    @Bean
//    public OpenAPI openAPI(){
//        SecurityScheme securityScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
//                .in(SecurityScheme.In.HEADER).name("Authorization");
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Authorization");
//
//        return new OpenAPI()
//                .components(new Components().addSecuritySchemes("Authorization", securityScheme))
//                .security(Arrays.asList(securityRequirement));
//    }
}
