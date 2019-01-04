FROM openjdk:8-alpine

ADD target/marketme-client-api.jar marketme-client-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "marketme-client-api.jar"]