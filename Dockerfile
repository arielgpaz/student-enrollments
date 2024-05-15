FROM amazoncorretto:21.0.3

ENV APP_NAME=student-enrollments
WORKDIR /app

COPY ./target/${APP_NAME}.jar  ${APP_NAME}.jar

CMD java -jar ${APP_NAME}.jar
EXPOSE 8080
