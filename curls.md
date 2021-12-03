CURL requests to check Meal API of Topjava project
==================================================

### GET all meals

To request all user's meals, send a GET request to the /rest/meals URL. 
The response body will contain all user's meals in JSON.
- CURL:
```
curl --location --request GET 'http://localhost:8080/topjava/rest/meals'
```
- Response body example:
```JSON
[
    {
        "id": 100008,
        "dateTime": "2020-01-31T20:00:00",
        "description": "Ужин",
        "calories": 510,
        "excess": true
    },
    {
        "id": 100007,
        "dateTime": "2020-01-31T13:00:00",
        "description": "Обед",
        "calories": 1000,
        "excess": true
    },
    {
        "id": 100006,
        "dateTime": "2020-01-31T10:00:00",
        "description": "Завтрак",
        "calories": 500,
        "excess": true
    },
    {
        "id": 100005,
        "dateTime": "2020-01-31T00:00:00",
        "description": "Еда на граничное значение",
        "calories": 100,
        "excess": true
    },
    {
        "id": 100004,
        "dateTime": "2020-01-30T20:00:00",
        "description": "Ужин",
        "calories": 500,
        "excess": false
    },
    {
        "id": 100003,
        "dateTime": "2020-01-30T13:00:00",
        "description": "Обед",
        "calories": 1000,
        "excess": false
    },
    {
        "id": 100002,
        "dateTime": "2020-01-30T10:00:00",
        "description": "Завтрак",
        "calories": 500,
        "excess": false
    }
]
```

### GET specific meal

To request meal, send a GET request to the object specific URL (id). 
The response body will contain specific meal info in JSON. Note that the result of this request is Meal object.
- CURL:
```
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/100003'
```
- Response body example:
```JSON
{
  "id": 100003,
  "dateTime": "2020-01-30T13:00:00",
  "description": "Обед",
  "calories": 1000,
  "user": null
}
```

### DELETE meal

To delete meal, send a DELETE request to the object specific URL. The response will have no content since it is delete 
request. The attempt to delete the same meal twice will produce NotFoundException.
- CURL:
```
curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/100004'
```
- Response will have empty body

### PUT an update for meal

To update meal, send a PUT request to the object specific URL. The request body should contain JSON representation 
of updated fields of Meal object. The response will have no content since it is update request. Any unspecidied field 
of an object will remain the same.


- CURL:
```
curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100002' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 100002,
    "dateTime": "2020-01-30T10:02:00",
    "description": "Обновленный завтрак",
    "calories": 200
}'
```
- Response will have empty body

### POST creation of meal

To create new meal, send a POST request to the application URL. The request body should contain JSON description of 
a new Meal object without id. The response will have body with meal JSON representation. Any unspecified field of an 
object will have null or default value in case it is java primitive value. Any attempt to create object with id will 
produce IllegalArgumentExceptions since object should be new, in other words should have no ID. The id will be generated 
automatically and will be shown in response body.
- CURL:
```
curl --location --request POST 'http://localhost:8080/topjava/rest/meals/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dateTime": "2020-02-01T18:00:00",
    "description": "Созданный ужин",
    "calories": 300,
    "user": null
}'
```
- Response body example:
```JSON
{
  "id": 100011,
  "dateTime": "2020-02-01T18:00:00",
  "description": "Созданный ужин",
  "calories": 300,
  "user": null
}
```

### GET filtered by date and time meals

To get meals filtered by date and time of creation, send a GET request to the application URL with "/filtered" suffix. 
The request should conatin next parameters:  

- startDate - the start date of filtration (inclusive);
- startTime - the start time of filtration (inclusive);
- endDate - the end date of filtration (inclusive);
- endTime - the end time of filtration (not inclusive);  

Filtration is done separately by date and by time. Note: if the start time or date will be after end time or date 
respectively the result is unpredictable. The response body will contain JSON representation of Meal objects, 
satisfying the filtration
- CURL:
```
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filtered?startDate=2020-01-30&startTime=10:45:00&endDate=2020-01-31&endTime=19:33:00'
```
- Response body example:
```JSON
[
  {
    "id": 100007,
    "dateTime": "2020-01-31T13:00:00",
    "description": "Обед",
    "calories": 1000,
    "excess": true
  },
  {
    "id": 100003,
    "dateTime": "2020-01-30T13:00:00",
    "description": "Обед",
    "calories": 1000,
    "excess": false
  }
]
```
