# Start with a base image containing Java runtime
FROM java:8-jre
# Add Maintainer Info
LABEL MAINTAINER="psyfabriqdev@gmail.com"
# Add a volume pointing to /tmp
VOLUME /tmp
# The application's jar file
ARG JAR_FILE=target/app-pfq-eureka-0.0.1-SNAPSHOT.jar
ARG PORT=8761
ENV APP_NAME=app-pfq-eureka
ENV PORT=${PORT}
ENV HOST_NAME=localhost
ENV CONFIG_URI=http://app-config:8888
ENV REG_WITH_EUREKA=false
ENV FETCH_REGISTRY=false 
# Make port 8080 available to the world outside this container
EXPOSE ${PORT}
# Add the application's jar to the container
ADD ${JAR_FILE} pfq-service.jar
# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/pfq-service.jar"]