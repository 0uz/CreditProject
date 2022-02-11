FROM maven:3.8-openjdk-17

ADD ./target/Credit-0.0.1-SNAPSHOT.jar /payten-credit.jar

ENTRYPOINT ["java", "-jar", "payten-credit.jar"]

EXPOSE 8080