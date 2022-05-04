# myretail-api
include an overview

## Tech Stack
- SpringBoot with Java 11
- Cassandra
- Spring AOP for Logging (see @MyRetailLoggable)
- Spring data cache (default implementation)
- Resilience4j for circuit breaker
- Spring actuator for health
- Micrometer for metrics
- SpringDoc OpenAPI for API documentation
- Lombok 

## Running the Application Locally
Follow the steps below to run the API on your local machine.

### Setting up Local DB 
This assumes that you have a Cassandra instance running on your computer and is exposed to port 9042. 

If Cassandra is listening on a different port, update application.yml accordingly.

Once Cassandra is up and running, execute the following CSQLs to setup environment.

Step 1: Setup keyspace
``` 
CREATE KEYSPACE
```
Step 2: Create table
```
????
```
Step 3: Populate table
``` 
???
```
### Running the Application

Step 1:  Clone GIT Repo
``` 
git clone ???
```

Step 2:  Build the app
```
./gradlew clean build 
```

Step 3:  Running the app

Option 1 (run from command line)
```
SPRING_PROFILES_ACTIVE=local ./gradlew clean bootRun
```
Option 2 (from your IDE) after setting the following env var
```
spring.profiles.active=local
```

### Testing the API

Request:
- GET - http://localhost:8081/v1/products/13860428

Response (200):
```
{
	"id": 13860428,
	"name": "The Big Lebowski (Blu-ray)",
	"current_price": {
		"value": 19.99,
		"currency_code": "USD"
	}
}
```

## Future Enhancements for Prod Readiness
- Caching
  - Switch to an implementation like Redis
- Cassandra Config
  - Local doesn't support authentication.  Enhance to support authentication.
- Configurable parameters:
  - redsky_key in env variable (inject as secret)
  - http read/request timeouts
- Logging - provide consistent formatting 
- Test coverage - improve test coverage
- Performance test - test in a PROD like environment to establish the following:
  - Response time (P95, P99)
  - TPS
  - Error rate
- Scalability
  - Scale horizontally 
  - Load balance across regions
  - Use reactive stack to reduce hardware resources
  - 
