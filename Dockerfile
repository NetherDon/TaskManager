FROM maven:3.8.4-openjdk-17-slim

COPY ./ ./
ENTRYPOINT ["mvn", "spring-boot:run"]