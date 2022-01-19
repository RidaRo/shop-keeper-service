FROM openjdk:17-alpine

RUN mkdir -p /usr/src/app/

WORKDIR /usr/src/app/
COPY . /usr/src/app/

EXPOSE 8080

ADD target/shop-keeper.jar shop-keeper.jar

ENTRYPOINT ["java","-jar","shop-keeper.jar"]