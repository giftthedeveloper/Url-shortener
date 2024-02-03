FROM openjdk:17-jdk-alpine

COPY target/UrlShortener-0.0.1-SNAPSHOT.jar app-1.0.0.jar

ENV BASE_URL="https://shorturl-by-gift.onrender.com/"
ENV DB_URL="mongodb+srv://giftthedeveloper:giftisagift@cluster0.qnkmshx.mongodb.net/dbname?retryWrites=true&w=majority"

CMD ["java", "-jar", "app-1.0.0.jar"]
