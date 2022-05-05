# Myretail API
Myretail-api aggregates product information from Target's RedskyAPI along with its price details from a cassandra datastore, and returns a JSON object to the caller. It also allows updating the price for a product that exists in Redsky.

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
CREATE KEYSPACE myretail WITH replication = {'class':'SimpleStrategy', 'replication_factor': 3};

```
Step 2: Create table
```
Use use myretail;
 create table price (
        id int primary key , 
        price double, 
       currency text,
        modified_timestamp  timestamp
        );
```
Step 3: Populate table
``` 
insert into price (id, price, currency, modified_timestamp) 
values(13860428, 13.49, 'USD', '2022-04-28 04:00:00+0000');
insert into price (id, price, currency, modified_timestamp) 
values(54456119, 25.55, 'USD', '2022-04-28 04:00:00+0000');

```
### Running the Application

Step 1:  Clone GIT Repo
``` 
git clone git@github.com:lekshmynair/myretail-api.git
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
- GET - http://localhost:8081/products/v1/13860428

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
Request:
- GET - http://localhost:8081/products/v1/13860423

Response (206):
```
{
	"id": 12954218,
	"name": "Kraft Macaroni &#38; Cheese Dinner Original - 7.25oz",
	"warnings": [
		{
			"code": 206,
			"description": "Price not found"
		}
	]
}
```
Request:
- GET - http://localhost:8081/products/v1/123234

Response (404):
```
{
	"status": 404,
	"title": "product-not-found",
	"type": "https://api.myretail.com/product-not-found",
	"detail": "Product not found"
}
```
Request:
- PUT - http://localhost:8081/products/v1/13860428

Request Body :
```
{
    "value": 109.99,
	"currency_code" : "USD"
}
```
Response (200)
```
{
	"id": 13860428,
	"name": "The Big Lebowski (Blu-ray)",
	"current_price": {
		"value": 109.99,
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
  
