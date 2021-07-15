#!/bin/ash

# if anything exits with non-zero error status, exit this script
set -e

# start up spring boot jar file
exec java -jar /opt/cars-api/cars-api.war "$@"
