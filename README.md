# TP27 – Microservice observable & résilient

## Travail demandé

1. Réaliser un test de charge concurrente sur `book-service` avec 50 emprunts en parallèle.
2. Vérifier le stock final de livres via l’API `GET /api/books`.
3. Tester le comportement du service `library-service` avec fallback lorsque `pricing-service` est arrêté.
4. Vérifier les métriques exposées par Spring Boot Actuator et Resilience4j.
5. Rédiger une conclusion expliquant les mécanismes de résilience.

---

## Captures / preuves

### 1. Résultat du test de charge

```bash
./loadtest.sh 1 50
```
<img width="858" height="175" alt="Capture d&#39;écran 2026-01-11 214252" src="https://github.com/user-attachments/assets/55442a80-1ee8-4bdd-b98f-78d0baf69483" />

### 2. Vérification du stock final

```bash
curl -s http://localhost:8081/api/books
```

[
  {
    "id": 1,
    "title": "TP-Fallback",
    "author": "Demo",
    "stock": 0
  }
]

### 3. Test fallback

Arrêter le pricing-service.

{"price":0.0,"bookId":1}

### 4. Vérification des métriques Resilience4j

<img width="932" height="318" alt="Capture d&#39;écran 2026-01-11 215311" src="https://github.com/user-attachments/assets/4be2ac2f-c500-4b82-bbe4-24d887e8a5bd" />

---

## Conclusion

Le test de charge a montré que le stock est correctement géré : les premiers emprunts réussissent et les autres retournent un conflit (409).

Le fallback de library-service permet de continuer à fournir des réponses même si pricing-service est indisponible.

Les métriques exposées permettent de surveiller les circuits et d’identifier les défaillances.

Le verrou sur la base de données est nécessaire pour éviter des emprunts simultanés incorrects sur plusieurs instances.

Le circuit breaker protège le système d’une surcharge et déclenche le fallback pour maintenir la résilience.

---

## Auteur

**Nom :** JARDI Siham

**Cours :** Architecture Microservices : Conception, Déploiement et Orchestration

**Date :** Janvier 2026

**Encadré par :** Pr.Mohamed LACHGAR


