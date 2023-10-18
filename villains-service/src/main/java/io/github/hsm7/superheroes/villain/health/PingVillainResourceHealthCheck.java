package io.github.hsm7.superheroes.villain.health;

import io.github.hsm7.superheroes.villain.rest.VillainResource;
import jakarta.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

/**
 * {@link HealthCheck} to ping the Villain service
 */
@Liveness
public class PingVillainResourceHealthCheck implements HealthCheck {
    @Inject
    VillainResource villainResource;

    @Override
    public HealthCheckResponse call() {
        var response = this.villainResource.hello();

        return HealthCheckResponse.named("Ping Villain REST Endpoint")
                .withData("Response", response)
                .up()
                .build();
    }
}
