FROM thrively/jdk8-maven
MAINTAINER paggarwal

RUN mkdir /build

COPY ./build/libs/docker-scheduler-1.0-SNAPSHOT-all.jar build/

WORKDIR /build

EXPOSE 8080

CMD /usr/bin/java $JAVA_OPTS $JAVA_SSL_OPTS -XX:OnOutOfMemoryError="kill -9 %p" -jar docker-scheduler-1.0-SNAPSHOT-all.jar
