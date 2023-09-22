#
# Build stage
#
FROM maven:latest AS build
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
WORKDIR /stacklite_clone
COPY ./pom.xml /stacklite_clone/pom.xml
COPY ./src/main/java/com/stacklite/dev/stacklite_clone/StackliteCloneApplication.java /stacklite_clone/src/main/java/com/stacklite/dev/stacklite_clone/StackliteCloneApplication.java
RUN mvn -f /stacklite_clone/pom.xml clean package

COPY . /stacklite_clone
RUN mvn -f /stacklite_clone/pom.xml clean package


FROM openjdk:20-ea-4-jdk
LABEL authors="oluwatobilobaaremu"
EXPOSE 3000
COPY --from=build /stacklite_clone/target/*.jar app.jar
ENTRYPOINT ["sh","-c","java -jar /app.jar"]




