package messagingServer.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Server",
                description = "Server", version = "1",
                contact = @Contact(
                        name = "Buanova Tatyana"
                )
        )
)
public class OpenApiConfig {
}
