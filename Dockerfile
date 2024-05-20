#
# Build stage
#
FROM maven:3.9.6-amazoncorretto-21 AS build

WORKDIR /product

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B package -DskipTests

#
# Package stage
#
FROM amazoncorretto:21-alpine-jdk

WORKDIR /product

COPY --from=build /product/target/*.jar ./product-service.jar

EXPOSE 7077

ENTRYPOINT ["java","-jar","product-service.jar"]