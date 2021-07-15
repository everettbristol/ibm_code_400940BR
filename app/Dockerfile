FROM alpine:3.14

LABEL maintainer="Todd Giguere <todd.giguere@gmail.com>"

# get the WAR file
COPY build/libs/cars-api-0.0.1-SNAPSHOT.war ./docker-entrypoint.sh /opt/cars-api/

# install java packages, set shell to execute
RUN mv /opt/cars-api/cars-api-0.0.1-SNAPSHOT.war /opt/cars-api/cars-api.war \
    && apk --no-cache add openjdk11-jre \
    && chmod a+x /opt/cars-api/docker-entrypoint.sh

# expose tomcat port
EXPOSE 8080

# default command to run the war file
ENTRYPOINT ["/opt/cars-api/docker-entrypoint.sh"]