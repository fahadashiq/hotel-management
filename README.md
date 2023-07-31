# Hotel Management

### Overview
A hotel management service to run CRUD operations on accommodations. All the allowed operations all provided
in the [Open Api Spec](openapi-specs.yaml) file. Service also provides a swagger page
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) to run and test all the endpoint.

### Requirements
1. docker
2. docker-compose
3. make (optional)


### How to run
Application uses docker to build and run. Targets in [Makefile](/Makefile) have been provided to build and run the image.
To build the image run: `make build` In case make is not available image can be build using `build -t hotel-management .`

To run the build image run make target `make start` which runs the containers in detach mode. Or it can be simply ran
with `docker-compose up`

### Available make targets
| Name                | Description                             |
|---------------------|-----------------------------------------|
| start               | Start all the containers.               |
| start-database-only | Starts container for only database.     |
| logs                | To check logs for the running container |
| stop                | Stop all the running containers         |


### Authentication
Http basic auth has been implemented to make the entities tenant aware. Two users are hardcoded for now which belongs to
two different tenants and user from one tenant can't access items from another tenant.

| Username | Password | Tenant id |
|----------|----------|-----------|
| user1    | pass123  | tenant1   |
| user2    | pass123  | tenant2   |