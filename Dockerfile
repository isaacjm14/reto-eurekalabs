FROM openjdk:17-alpine
MAINTAINER "Isaac Jurado"
COPY ./build/libs/retoeurekalabs-1.0.0.jar retoeurekalabs-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/retoeurekalabs-1.0.0.jar"]
