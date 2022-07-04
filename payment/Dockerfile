FROM openjdk:17.0.2-jdk

ARG PROFILE
ARG ADITIONAL_OPTS
ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD

ENV PROFILE = ${PROFILE}
ENV ADITIONAL_OPTS=${ADITIONAL_OPTS}
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}


COPY /build/libs/payment-0.0.1-SNAPSHOT.jar trace-payment.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

ENTRYPOINT ["/bin/bash","./wait-for-it.sh", "db:3306","-s", "--", "java", "-jar", "trace-payment.jar", "-DDB_URL=${DB_URL}", "-DDB_USERNAME=${DB_USERNAME}", "-DDB_PASSWORD=${DB_PASSWORD}", "--spring.profiles.active=${PROFILE}"]

