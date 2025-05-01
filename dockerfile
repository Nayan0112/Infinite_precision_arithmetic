
FROM maven:3.9.4-eclipse-temurin-21

WORKDIR /app

COPY . .

RUN mvn clean install
RUN javac -cp .:/app/target/classes myinfarith/MyInfArith.java

ENTRYPOINT ["java", "-cp", ".:/app/target/classes", "myinfarith.MyInfArith"]
CMD ["float", "div", "1", "0"]