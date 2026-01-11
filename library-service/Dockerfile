############################################
# ÉTAPE 1 : Build du JAR avec Maven
############################################
# Image Maven avec JDK 21 (compile uniquement)
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Dossier de travail dans le conteneur
WORKDIR /app

# Copier le pom.xml en premier (optimisation cache Docker)
COPY pom.xml .

# Télécharger les dépendances
RUN mvn -q -B dependency:go-offline

# Copier le code source
COPY src ./src

# Compiler l’application (sans tests pour aller plus vite)
RUN mvn -q -DskipTests package


############################################
# ÉTAPE 2 : Image runtime (légère)
############################################
# Image Java légère pour exécuter l’application
FROM eclipse-temurin:21-jre-jammy

# Installer curl (nécessaire pour healthcheck Docker)
RUN apt-get update \
 && apt-get install -y curl \
 && rm -rf /var/lib/apt/lists/*

# Dossier de travail
WORKDIR /app

# Copier le JAR depuis l’étape build
COPY --from=build /app/target/*.jar app.jar

# Profil Spring utilisé dans Docker
ENV SPRING_PROFILES_ACTIVE=docker

# Port interne de l’application (toujours 8081)
EXPOSE 8081

# Commande de démarrage
ENTRYPOINT ["java","-jar","/app/app.jar"]
