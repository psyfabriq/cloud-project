# Start with a base image containing Java runtime
FROM java:8-jre
# Add Maintainer Info
LABEL MAINTAINER="psyfabriqdev@gmail.com"
# Add a volume pointing to /tmp
VOLUME /tmp

# The application's jar file
ARG JAR_FILE=target/app-pfq-api-0.0.1-SNAPSHOT.jar
ARG PORT=8091
ENV PORT=${PORT}
ENV CONFIG_URI=http://app-config:8888
ENV DATABASE_URI=database-app
ENV DATABASE_PORT=3306
ENV DATABASE_SHEMA=appdb
ENV DATABASE_USER=appuser
ENV DATABASE_PASS=apppassword
ENV DEFAULT_ZONE=http://app-registry:8761/eureka
# Make port 8080 available to the world outside this container
EXPOSE ${PORT}
# Add the application's jar to the container
ADD ${JAR_FILE} pfq-service.jar
# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/pfq-service.jar"]