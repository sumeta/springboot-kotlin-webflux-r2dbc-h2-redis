# springboot-kotlin-webflux-r2dbc-redis
Template Project Springboot kotlin Coroutines API

### Base Project
- gradle (kotlin)
- kotlin
- webflux
- spring-data-r2dbc
- h2 database
- redis cache


### CURL


#### Get By Id
```
curl --location --request GET 'http://localhost:8080/member/get/1'
```


#### Add
```
curl --location --request POST 'http://localhost:8080/member/add' \
--header 'Content-Type: application/json' \
--data-raw '{
        "id": "1",
        "firstName": "FIRST_NAME",
        "lastName": "LAST_NAME",
        "createdBy": "CREATED_BY",
        "createdDate": "2022-02-16T19:30:58.728473"
    }'
```

#### Edit
```
curl --location --request PATCH 'http://localhost:8080/member/edit' \
--header 'Content-Type: application/json' \
--data-raw '{
        "id": "1",
        "firstName": "Sumeta",
        "lastName": "Top",
        "createdBy": "CREATED_BY",
        "createdDate": "2022-02-16T19:30:58.728473"
    }'
```


### Delete
```
curl --location --request PATCH 'http://localhost:8080/member/edit' \
--header 'Content-Type: application/json' \
--data-raw '{
        "id": "1",
        "firstName": "Sumeta",
        "lastName": "Top",
        "createdBy": "CREATED_BY",
        "createdDate": "2022-02-16T19:30:58.728473"
    }'
```
