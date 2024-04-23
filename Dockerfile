FROM openjdk:17-ea-4-jdk
LABEL maintainer="bolaji.salau@gmail.com"
ARG JAR_FILE=target/bookstore-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} bookstore.jar
ENTRYPOINT ["java", "-jar", "bookstore.jar"]