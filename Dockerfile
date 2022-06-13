#FROM openjdk:11-jdk-slim as build
#RUN mkdir /app
#COPY . /app
#WORKDIR /app
#RUN chmod +x mvnw
#RUN ./mvnw dependency:go-offline
#RUN ./mvnw -Pprod clean verify -Dmaven.test.skip=true
#
#FROM openjdk:11-slim
#RUN mkdir /app
#COPY . /app
#WORKDIR /app
#COPY --from=build /app/target/*.jar /app/app.jar
#ENTRYPOINT ["java","-jar","/app/app.jar"]
FROM maven:3-jdk-11
WORKDIR .
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run
