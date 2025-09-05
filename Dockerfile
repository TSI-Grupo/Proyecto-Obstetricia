FROM openjdk:24-jdk
ARG JAR_FILE=target/obstetricia-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_obstetricia.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","app_obstetricia.jar" ]