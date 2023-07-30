APPLICATION_NAME = hotel-management

build:
	docker build -t ${APPLICATION_NAME} .

start:
	docker-compose up -d

start-database-only:
	docker-compose up postgres-database

logs:
	docker-compose -p ${APPLICATION_NAME} logs -f -t

stop:
	docker-compose -p ${APPLICATION_NAME} down