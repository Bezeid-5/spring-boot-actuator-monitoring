# Spring Boot Actuator Monitoring

Projet de démonstration pour Spring Boot Actuator avec indicateurs personnalisés et métriques.

## Configuration des Endpoints Actuator

Les endpoints Actuator sont configurés dans `application.yaml` :

- **Health** : `/actuator/health` - État de santé de l'application
- **Metrics** : `/actuator/metrics` - Métriques de l'application
- **Prometheus** : `/actuator/prometheus` - Métriques au format Prometheus
- **Environment** : `/actuator/env` - Variables d'environnement
- **Info** : `/actuator/info` - Informations sur l'application

Tous les endpoints sont exposés via la configuration `management.endpoints.web.exposure.include: "*"`.

## Indicateurs Personnalisés (HealthIndicators)

### 1. CustomHealthIndicator
- **Fichier** : `com.example.actuator_monitoring.health.CustomHealthIndicator`
- **Description** : Simule la vérification de santé d'un service externe
- **Détails** : Affiche le statut, le temps de réponse et la date de dernière vérification

### 2. DatabaseHealthIndicator
- **Fichier** : `com.example.actuator_monitoring.health.DatabaseHealthIndicator`
- **Description** : Surveille la connexion à une base de données
- **Détails** : Vérifie la disponibilité de la base de données

## Métriques Personnalisées (Micrometer)

### CustomMetricsService
- **Fichier** : `com.example.actuator_monitoring.metrics.CustomMetricsService`
- **Métriques disponibles** :
  - `custom.requests.total` : Nombre total de requêtes
  - `custom.errors.total` : Nombre total d'erreurs
  - `custom.request.duration` : Durée de traitement des requêtes
  - `custom.users.active` : Nombre d'utilisateurs actifs
  - `custom.business.operations` : Nombre d'opérations métier

## Endpoints de Test

Le contrôleur `TestController` expose plusieurs endpoints pour tester les métriques :

- `GET /api/hello` - Endpoint simple avec métriques
- `POST /api/operation?type=<type>` - Simule une opération métier
- `GET /api/error` - Simule une erreur pour tester les métriques d'erreur
- `POST /api/users/active?count=<nombre>` - Met à jour le nombre d'utilisateurs actifs
- `GET /api/info` - Informations sur l'application et les endpoints disponibles

## Utilisation

1. Démarrer l'application :
```bash
mvn spring-boot:run Ou 
.\mvnw.cmd spring-boot:run
```

2. Accéder aux endpoints Actuator :
- Health : http://localhost:8080/actuator/health
- Metrics : http://localhost:8080/actuator/metrics
- Prometheus : http://localhost:8080/actuator/prometheus
- Environment : http://localhost:8080/actuator/env
- Info : http://localhost:8080/actuator/info

3. Tester les métriques personnalisées :
```bash
# Appeler plusieurs fois pour générer des métriques
curl http://localhost:8080/api/hello
curl -X POST "http://localhost:8080/api/operation?type=create"
curl -X POST "http://localhost:8080/api/users/active?count=10"
```

## Structure du Projet

```
src/main/java/com/example/actuator_monitoring/
├── ActuatorMonitoringApplication.java
├── config/
│   └── MetricsConfig.java          # Configuration Micrometer
├── controller/
│   └── TestController.java         # Endpoints de test
├── health/
│   ├── CustomHealthIndicator.java  # HealthIndicator personnalisé
│   └── DatabaseHealthIndicator.java # HealthIndicator pour DB
└── metrics/
    └── CustomMetricsService.java   # Service de métriques personnalisées
```

## Technologies Utilisées

- Spring Boot 3.3.0
- Spring Boot Actuator
- Micrometer
- Micrometer Prometheus Registry
- Spring AOP (pour l'annotation @Timed)
