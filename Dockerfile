FROM openjdk:8-jre-alpine

RUN apk add --update bash && rm -rf /var/cache/apk/*

WORKDIR /home/reclameaqui-test

ADD build/libs/reclameaqui-test-*.jar reclameaqui-test.jar

ADD ./wait-for-it.sh wait-for-it.sh

RUN bash -c "chmod 755 wait-for-it.sh"
