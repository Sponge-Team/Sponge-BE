FROM bellsoft/liberica-openjdk-alpine:17

WORKDIR /app

COPY . .

# 개행문자 오류 해결
RUN sed -i 's/\r$//' gradlew


RUN chmod +x ./gradlew
RUN ./gradlew clean build

ENV JAR_PATH=/app/build/libs
RUN mv ${JAR_PATH}/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]