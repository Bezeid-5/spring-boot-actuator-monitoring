package com.example.actuator_monitoring.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * HealthIndicator pour surveiller la connexion à une base de données.
 * Cet exemple montre comment vérifier la santé d'une ressource critique.
 */
@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    // Dans un vrai projet, utilisez @Value ou @ConfigurationProperties pour injecter ces valeurs
    private static final String DB_URL = "jdbc:h2:mem:testdb"; // Exemple avec H2 en mémoire
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    @Override
    public Health health() {
        try {
            // Tenter une connexion à la base de données
            // Note: Dans un vrai projet, utilisez un DataSource injecté plutôt que DriverManager
            if (isDatabaseAvailable()) {
                return Health.up()
                        .withDetail("database", "H2 Database")
                        .withDetail("status", "Connected")
                        .withDetail("url", DB_URL)
                        .build();
            } else {
                return Health.down()
                        .withDetail("database", "H2 Database")
                        .withDetail("status", "Connection failed")
                        .withDetail("error", "Unable to establish connection")
                        .build();
            }
        } catch (Exception e) {
            return Health.down()
                    .withDetail("database", "H2 Database")
                    .withDetail("status", "Error")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }

    /**
     * Vérifie si la base de données est disponible.
     * Dans un vrai projet, utilisez un DataSource ou un Repository injecté.
     */
    private boolean isDatabaseAvailable() {
        // Pour cet exemple, on simule simplement
        // Dans un vrai projet, vous feriez une vraie vérification de connexion
        try {
            // Si vous avez une vraie DB, décommentez ceci :
            // Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            // connection.close();
            // return true;
            
            // Pour l'exemple, on retourne toujours true
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

