FROM openjdk:8-alpine

WORKDIR /home/belykh

COPY build/libs /home/belykh/build/libs/
ADD run_jar.sh .

EXPOSE 8080

CMD ./run_jar.sh