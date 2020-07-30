FROM openjdk:8
EXPOSE 8080
ADD target/microservices-monitoring-0.0.1-SNAPSHOT.jar microservices-monitoring-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/microservices-monitoring-0.0.1-SNAPSHOT.jar"]