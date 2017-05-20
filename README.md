# RestStats

A Rest API to calculate realtime statistic from the last 60 seconds. There will be two APIs, one of them is called every time a transaction is made. It is also the sole input of this rest API. The other one returns the statistic based of the transactions of the last 60 seconds.

### Technologies
* Java
* SpringBoot
* Swagger - Documentation
* Spring Boot Actuator & codahale - Metrics

### Usage

#### Build and Run
```sh
mvn clean install spring-boot:run
```
#### POST /transactions
Every Time a new transaction happened, this endpoint will be called.
```sh
curl -X POST \
  http://localhost:8080/transactions \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
	"amount" : 10.0,
	"timestamp": 1495227980159
}'
```

#### GET /statistics
This is the main endpoint of this task, this endpoint have to execute in constant time and memory (O(1)). It returns the statistic based on the transactions which happened in the last 60 seconds.
```sh
curl -X GET \
  http://localhost:8080/statistics \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json'
```

#### Metrics
All the metrics related to REST API can be tracked using the url - http://localhost:8080/metrics
```sh
Transaction API metrics
http://localhost:8080/metrics/Transaction.*
{
  "Transactions.snapshot.98thPercentile": 0,
  "Transactions.snapshot.mean": 0,
  "Transactions.snapshot.99thPercentile": 0,
  "Transactions.snapshot.stdDev": 0,
  "Transactions.snapshot.min": 0,
  "Transactions.snapshot.median": 0,
  "Transactions.count": 3200,
  "Transactions.snapshot.75thPercentile": 0,
  "Transactions.fiveMinuteRate": 5.7082839972753225,
  "Transactions.snapshot.999thPercentile": 0,
  "Transactions.snapshot.max": 0,
  "Transactions.oneMinuteRate": 9.059004636162019,
  "Transactions.meanRate": 2.6409286833623304,
  "Transactions.fifteenMinuteRate": 2.7879681046071902,
  "Transactions.snapshot.95thPercentile": 0
}
Statistics API Metrics
http://localhost:8080/metrics/Statistics.*
{
  "Statistics.snapshot.stdDev": 0,
  "Statistics.snapshot.mean": 0,
  "Statistics.fifteenMinuteRate": 2.8454279899928228,
  "Statistics.count": 3146,
  "Statistics.snapshot.95thPercentile": 0,
  "Statistics.fiveMinuteRate": 6.033136969800327,
  "Statistics.snapshot.min": 0,
  "Statistics.snapshot.max": 0,
  "Statistics.snapshot.999thPercentile": 0,
  "Statistics.snapshot.median": 0,
  "Statistics.snapshot.75thPercentile": 0,
  "Statistics.meanRate": 2.4863196264849114,
  "Statistics.snapshot.98thPercentile": 0,
  "Statistics.oneMinuteRate": 9.250587470549059,
  "Statistics.snapshot.99thPercentile": 0
}
```

#### API Swagger Spec
Swagger specification of the REST API can be accessed using the url - http://localhost:8080/v2/api-docs

