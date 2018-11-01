# Start with a base image containing Java runtime
FROM java:8-jre

# Add Maintainer Info
LABEL maintainer="psyfabriqdev@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080
# The application's jar file
ARG JAR_FILE=target/app-pfq-gateway-0.0.1-SNAPSHOT.jar
ENV CONFIG_URI=http://app-config:8888
ENV DEFAULT_ZONE=http://app-registry:8761/eureka

# Add the application's jar to the container
ADD ${JAR_FILE} pfq-service.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/pfq-service.jar"]
