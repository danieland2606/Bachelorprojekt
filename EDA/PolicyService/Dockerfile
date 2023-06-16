# Nutzung: Dockerfile ins Mavenprojekt-Verzeichnis legen und letzte Zeile anpassen

# Dieser Teil wird benötigt, um das Projekt zu kompilieren und Abhängigkeiten zu Verfügung zu stellen
FROM eclipse-temurin:19-jdk-alpine as build
WORKDIR /workspace/app
ARG service

#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#COPY src src

COPY /${service}/mvnw .
COPY /${service}/.mvn .mvn
COPY /${service}/src ./$service/src
COPY /${service}/pom.xml ./$service/

COPY pom.xml .

COPY /EventInterface/pom.xml ./EventInterface/
COPY /EventInterface/src ./EventInterface/src

RUN ./mvnw install -P ${service} -DskipTests
RUN mkdir -p ${service}/target/dependency && (cd ${service}/target/dependency; jar -xf ../*.jar)

# Dieser Teil nimmt das vorher erzeugte Projekt, und baut ein nutzbares Image
FROM eclipse-temurin:19-jre-alpine
VOLUME /tmp
ARG service
ARG DEPENDENCY=/workspace/app/${service}/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Hier, am Ende, steht der Package- und Klassenname euer Klasse, die main enthält
# Beipiel: komplettes-package.klassenname
ENTRYPOINT ["java","-cp","app:app/lib/*","EDA.PolicyService.MeowMedApplication"]