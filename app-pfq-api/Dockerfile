# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine
# Add Maintainer Info
LABEL MAINTAINER="psyfabriqdev@gmail.com"
# Add a volume pointing to /tmp
VOLUME /tmp

# The application's jar file
ARG JAR_FILE=target/app-server-api-0.0.1-SNAPSHOT.jar
ARG PORT=8091
ENV PORT=${PORT}
ENV DEFAULT_ZONE=http://172.17.0.3:8761/eureka/
# Make port 8080 available to the world outside this container
EXPOSE ${PORT}
# Add the application's jar to the container
ADD ${JAR_FILE} app-server-api.jar
# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app-server-api.jar"]