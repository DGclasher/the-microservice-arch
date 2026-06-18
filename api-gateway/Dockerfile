FROM eclipse-temurin:21-jre-alpine as BUILDER
WORKDIR extracted
COPY target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:21-jre-alpine as RUNNER
WORKDIR application
COPY --from=BUILDER extracted/dependencies/ ./
COPY --from=BUILDER extracted/spring-boot-loader/ ./
COPY --from=BUILDER extracted/snapshot-dependencies/ ./
COPY --from=BUILDER extracted/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]