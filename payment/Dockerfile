FROM gradle:jdk17-alpine

ARG DB_URL_BUILD="jdbc:mysql://localhost:9090/payment?createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC"
ARG DB_USERNAME_BUILD="root"
ARG DB_PASSWORD_BUILD="root"

ENV DB_URL=$DB_URL_BUILD
ENV DB_USERNAME=$DB_USERNAME_BUILD
ENV DB_PASSWORD=$DB_PASSWORD_BUILD

WORKDIR /app

COPY . .
RUN