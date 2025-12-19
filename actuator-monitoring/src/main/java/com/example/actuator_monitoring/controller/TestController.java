package com.example.actuator_monitoring.controller;

import com.example.actuator_monitoring.metrics.CustomMetricsService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Timer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Contrôleur REST pour tester les métriques et les endpoints Actuator.
 */
@RestController
@RequestMapping("/api")
public class TestController {

    private final CustomMetricsService metricsService;
    private final Random random = new Random();

    public TestController(CustomMetricsService metricsService) {
        this.metricsService = metricsService;
    }

    /**
     * Endpoint simple pour tester les métriques.
     * Utilise l'annotation @Timed pour mesurer automatiquement le temps d'exécution.
     */
    @GetMapping("/hello")
    @Timed(value = "custom.api.hello.duration", description = "Temps de réponse de /api/hello")
    public ResponseEntity<Map<String, String>> hello() {
        metricsService.incrementRequestCount();
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from Actuator Monitoring!");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint qui simule une opération métier.
     */
    @PostMapping("/operation")
    @Timed(value = "custom.api.operation.duration", description = "Temps d'exécution des opérations")
    public ResponseEntity<Map<String, Object>> performOperation(@RequestParam String type) {
        Timer.Sample sample = metricsService.startTimer();
        
        try {
            metricsService.incrementRequestCount();
            metricsService.incrementBusinessOperation(type);
            
            // Simuler un traitement
            Thread.sleep(50 + random.nextInt(200));
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("operationType", type);
            response.put("message", "Opération effectuée avec succès");
            
            return ResponseEntity.ok(response);
        } catch (InterruptedException e) {
            metricsService.incrementErrorCount();
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError().build();
        } finally {
            metricsService.stopTimer(sample);
        }
    }

    /**
     * Endpoint qui simule une erreur pour tester les métriques d'erreur.
     */
    @GetMapping("/error")
    public ResponseEntity<Map<String, String>> simulateError() {
        metricsService.incrementRequestCount();
        metricsService.incrementErrorCount();
        
        Map<String, String> response = new HashMap<>();
        response.put("error", "Erreur simulée pour tester les métriques");
        
        return ResponseEntity.status(500).body(response);
    }

    /**
     * Endpoint pour mettre à jour le nombre d'utilisateurs actifs.
     */
    @PostMapping("/users/active")
    public ResponseEntity<Map<String, String>> updateActiveUsers(@RequestParam int count) {
        metricsService.setActiveUsers(count);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Nombre d'utilisateurs actifs mis à jour");
        response.put("count", String.valueOf(count));
        
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint d'information sur l'application.
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("application", "Spring Boot Actuator Monitoring");
        info.put("version", "1.0.0");
        info.put("description", "Application de démonstration pour Spring Boot Actuator");
        info.put("endpoints", Map.of(
            "health", "/actuator/health",
            "metrics", "/actuator/metrics",
            "prometheus", "/actuator/prometheus",
            "env", "/actuator/env",
            "info", "/actuator/info"
        ));
        
        return ResponseEntity.ok(info);
    }
}

