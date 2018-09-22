FROM java:8-jre

MAINTAINER Ilia Podstavkov <psyfabriqdev@gmail.com>

VOLUME /tmp

ENV GIT_URI=https://github.com/psyfabriq/cloud-project.git

ARG JAR_FILE=target/app-pfq-config-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} pfq-service.jar

ADD ./target/app-pfq-config-0.0.1-SNAPSHOT.jar /app/

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/pfq-service.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8888/actuator/health || exit 1

EXPOSE 8888
