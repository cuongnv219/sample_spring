FROM openjdk:8-jdk-alpine
#EXPOSE 3306
#FROM fabric8/tomcat-9
ARG JAR_FILE=build/libs/PassM-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
#ENTRYPOINT ["./gradlew","build",.jar"]
