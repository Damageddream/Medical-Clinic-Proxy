FROM openjdk:17-jdk-alpine
COPY target/Medical-Clinic-Proxy-0.0.1-SNAPSHOT.jar Medical-Clinic-Proxy-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/Medical-Clinic-Proxy-0.0.1-SNAPSHOT.jar"]