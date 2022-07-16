FROM openjdk:11.0.12
EXPOSE 8080
ADD target/tour_backend-0.0.1-SNAPSHOT.jar tour_backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/tour_backend-0.0.1-SNAPSHOT.jar"]