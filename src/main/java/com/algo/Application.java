package com.algo;


import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;

import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "api-labyrinth", version = "1.0", contact = @Contact(email = "support@add.tn")), components = @Components(securitySchemes = {
}))
public class Application extends jakarta.ws.rs.core.Application {
}
