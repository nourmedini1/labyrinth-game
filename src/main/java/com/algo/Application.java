package com.algo;


import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@OpenAPIDefinition(info = @Info(title = "api-labyrinth-server", version = "1.0", contact = @Contact(email = "support@add.tn")), components = @Components(securitySchemes = {
        @SecurityScheme(securitySchemeName = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "jwt"),
        @SecurityScheme(securitySchemeName = "apiKey", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, apiKeyName = "X-Api-Key")
}))
public class Application extends jakarta.ws.rs.core.Application {
}
