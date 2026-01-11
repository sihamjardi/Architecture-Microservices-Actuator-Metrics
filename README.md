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
## Autre

### Docker
<img width="960" height="493" alt="Capture d&#39;écran 2026-01-11 215255" src="https://github.com/user-attachments/assets/84b29e56-1a6f-4163-9d74-5e453c6b4632" />

<img width="914" height="491" alt="Capture d&#39;écran 2026-01-11 215129" src="https://github.com/user-attachments/assets/2e86d560-d7c7-4451-9c73-ee3f18eb1b68" />

<img width="957" height="500" alt="Capture d&#39;écran 2026-01-11 215245" src="https://github.com/user-attachments/assets/f7207e45-cb9b-4a22-9f80-1fc662877504" />

<img width="922" height="410" alt="Capture d&#39;écran 2026-01-11 214752" src="https://github.com/user-attachments/assets/011909b8-e696-4d44-9941-7c8ed162b9b2" />

---

## Auteur

**Nom :** JARDI Siham

**Cours :** Architecture Microservices : Conception, Déploiement et Orchestration

**Date :** Janvier 2026

**Encadré par :** Pr.Mohamed LACHGAR


