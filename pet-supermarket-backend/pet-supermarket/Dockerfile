FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/classes/com/laborexport/pet_supermarket/PetSupermarketApplication.class pet_supermarket.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","pet_supermarket.jar"]