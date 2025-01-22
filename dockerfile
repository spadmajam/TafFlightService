FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/Tafflightms.jar flightms.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "flightms.jar"]