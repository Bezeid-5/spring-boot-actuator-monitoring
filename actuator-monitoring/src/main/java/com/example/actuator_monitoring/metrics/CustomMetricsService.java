package com.example.actuator_monitoring.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service pour gérer les métriques personnalisées avec Micrometer.
 * Ce service expose des métriques customisées pour surveiller l'application.
 */
@Service
public class CustomMetricsService {

    private final Counter requestCounter;
    private final Counter errorCounter;
    private final Timer requestTimer;
    private final AtomicInteger activeUsers;
    private final MeterRegistry meterRegistry;

    public CustomMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        // Compteur pour le nombre total de requêtes
        this.requestCounter = Counter.builder("custom.requests.total")
                .description("Nombre total de requêtes traitées")
                .tag("application", "actuator-monitoring")
                .register(meterRegistry);

        // Compteur pour les erreurs
        this.errorCounter = Counter.builder("custom.errors.total")
                .description("Nombre total d'erreurs")
                .tag("application", "actuator-monitoring")
                .register(meterRegistry);

        // Timer pour mesurer le temps de traitement des requêtes
        this.requestTimer = Timer.builder("custom.request.duration")
                .description("Durée de traitement des requêtes")
                .tag("application", "actuator-monitoring")
                .register(meterRegistry);

        // Gauge pour le nombre d'utilisateurs actifs
        this.activeUsers = meterRegistry.gauge("custom.users.active", 
                new AtomicInteger(0));
    }

    /**
     * Incrémente le compteur de requêtes.
     */
    public void incrementRequestCount() {
        requestCounter.increment();
    }

    /**
     * Incrémente le compteur d'erreurs.
     */
    public void incrementErrorCount() {
        errorCounter.increment();
    }

    /**
     * Enregistre le temps d'exécution d'une requête.
     */
    public Timer.Sample startTimer() {
        return Timer.start();
    }

    /**
     * Arrête le timer et enregistre la durée.
     */
    public void stopTimer(Timer.Sample sample) {
        sample.stop(requestTimer);
    }

    /**
     * Met à jour le nombre d'utilisateurs actifs.
     */
    public void setActiveUsers(int count) {
        activeUsers.set(count);
    }

    /**
     * Incrémente le compteur d'opérations métier.
     */
    public void incrementBusinessOperation(String operationType) {
        Counter.builder("custom.business.operations")
                .description("Nombre d'opérations métier effectuées")
                .tag("application", "actuator-monitoring")
                .tag("type", operationType)
                .register(meterRegistry)
                .increment();
    }
}

