# Nutzung: Dockerfile ins Mavenprojekt-Verzeichnis legen und letzte Zeile anpassen
# Nutzung: Dockerfile ins Mavenprojekt-Verzeichnis legen und letzte Zeile anpassen
# Nutzung: Dockerfile ins Mavenprojekt-Verzeichnis legen und letzte Zeile anpassen

# Dieser Teil wird benötigt, um das Projekt zu kompilieren und Abhängigkeiten zu Verfügung zu stellen
FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Dieser Teil nimmt das vorher erzeugte Projekt, und baut ein nutzbares Image
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Hier, am Ende, steht der Package- und Klassenname euer Klasse, die main enthält
# Beipiel: komplettes-package.klassenname
ENTRYPOINT ["java","-cp","app:app/lib/*","EDA.MeowMed.MeowMedApplication"]