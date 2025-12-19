package com.example.actuator_monitoring.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * HealthIndicator personnalisé pour surveiller un service ou une ressource.
 * Cet exemple simule la vérification de la santé d'un service externe.
 */
@Component
public class CustomHealthIndicator implements HealthIndicator {

    private final Random random = new Random();

    @Override
    public Health health() {
        // Simuler une vérification de santé
        // Dans un vrai projet, vous vérifieriez une base de données, un service
        // externe, etc.
        boolean isHealthy = checkServiceHealth();

        if (isHealthy) {
            return Health.up()
                    .withDetail("service", "Custom Service")
                    .withDetail("status", "Operational")
                    .withDetail("responseTime", getResponseTime() + "ms")
                    .withDetail("lastCheck", System.currentTimeMillis())
                    .build();
        } else {
            return Health.down()
                    .withDetail("service", "Custom Service")
                    .withDetail("status", "Unavailable")
                    .withDetail("error", "Service timeout")
                    .withDetail("lastCheck", System.currentTimeMillis())
                    .build();
        }
    }

    /**
     * Simule la vérification de santé d'un service.
     * Dans un vrai projet, cela pourrait être un appel HTTP, une vérification DB,
     * etc.
     */
    private boolean checkServiceHealth() {
        // Simuler : 80% de chance que le service soit en bonne santé
        return random.nextInt(100) < 80;
    }

    /**
     * Simule le temps de réponse du service.
     */
    private long getResponseTime() {
        return 50 + random.nextInt(200); // Entre 50ms et 250ms
    }
}
