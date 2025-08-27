APP_NAME = spring-boot-crud
MVNW = ./mvnw

start-db:
	docker-compose up -d

stop-db:
	docker-compose down

clean-restart-db:
	docker-compose down -v
	docker-compose up -d

check:
	$(MVNW) spotless:check

format:
	$(MVNW) spotless:apply

test:
	$(MVNW) test
