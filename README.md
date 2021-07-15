# IBM Code Challenge - Kubernetes - 400940BR
## GOALS
* Create REST API with GET and POST to retrieve and store data
* persist data
* run web service and database on Kubernetes

## SOLUTION SUMMARY
Created a REST API web application using Spring Boot that has endpoints to both save and retrieve details about cars (make, model, year, color). I have persisted the data into a PostgreSQL database.

Both Spring Boot application and PostgreSQL were implemented as Docker container images, and then added to Kubernetes config files and run on a Kubernetes cluster.

## SOLUTION DETAILS
* REST API developed in Java Spring Boot framework
* Spring data transactions were developed with Spring JPA
* Sample JUnit written for controller
* Java build accomplished with Gradle
* Docker image for Spring Boot application done locally via Dockerfile (not uploaded into public repo)
* PostgreSQL used for database storage
* PostgreSQL docker image pulled from public docker hub (official build image)
* Kubernetes configuration done via YAML files and applied using "kubectl"
* Used "minikube" k8s cluster for testing locally
* Kubernetes service for web API: Two replica pods fronted by LoadBalancer service
* Kubernetes service for PostgreSQL: One replica pod with PersistentVolumeClaim for storage
* Kubernetes secrets used for PostgreSQL password

## API endpoints
To add a car
```
POST http://<loadbalancer>/api/car/add with JSON body {"make":"foo","model":"bar","year":2021,"color":"white"} 
(will return JSON of object persisted, including "id")
```
To get single car
```
GET http://<loadbalancer>/api/car/{id}
({id} is integer corresponding to id of car)
(returns status 200 with JSON if found, status 404 if not)
```
Get all cars
```
GET http://<loadbalancer>/api/car/list
(returns JSON array of all cars)
```

## HOW TO BUILD
Spring Boot web artifact can be built with following command:
```
$ cd app
$ gradlew clean build
$ docker build -t cars-api-web .
```
Containers can then be set up in Kubernetes
```
$ cd kubernetes
$ kubectl apply -f namespace.yaml
$ kubectl apply -f carsapi-postgres-secret.yaml
$ kubectl apply -f carsapi-postgres-deployment.yaml
$ kubectl apply -f carsapi-app-deployment.yaml
```

## DESIGN CONCESSIONS (or things I would not do if production)
Due to time constraints I made the following choices, which I would NOT do in a production or live environment
* k8s secrets YAML should NEVER be checked into source control in current state (password must be better protected)
* Better input validation for all endpoints, most importantly the POST endpoint
* All REST endpoints should supply consumers with more error details
* Used a Spring JPA shortcut CRUD interface to save time, normally would have been proper custom transactional service
* More JUnit tests needed
