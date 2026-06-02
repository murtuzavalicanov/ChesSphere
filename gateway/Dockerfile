FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Maven istifadə olunduğu üçün 'target' qovluğuna baxırıq.
# .original faylını kənarda saxlamaq üçün *-SNAPSHOT.jar təyin edirik.
COPY target/*-SNAPSHOT.jar app.jar

EXPOSE 8765

ENTRYPOINT ["java", "-jar", "app.jar"]