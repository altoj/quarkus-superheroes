package io.github.hsm7.superheroes.villain;


import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(
                title = "Villain API",
                description = "This API allows CRUD operations on a villain",
                version = "1.0"
        )
)
public class VillainApplication extends Application { }
