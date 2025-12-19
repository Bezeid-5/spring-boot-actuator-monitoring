package com.example.actuator_monitoring.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration pour les métriques Micrometer.
 * Cette configuration permet d'utiliser l'annotation @Timed automatiquement.
 */
@Configuration
public class MetricsConfig {

    /**
     * Active l'aspect @Timed pour mesurer automatiquement les méthodes annotées.
     * Exemple d'utilisation : @Timed(value = "custom.method.duration", description = "Durée d'exécution")
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}

