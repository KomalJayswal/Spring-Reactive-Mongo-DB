# Spring Reactive Mongo DB

## Problem Statement

Create spring reactive CRUD Operations with Mongo Database.
* Remove _class productEntity from DB by adding config code
* Add Junit
* Standard coding practice
* Create Exception Handling
* Modify default mongodb timeout from 60 secs to 10 secs
* Create an ID generator


### Docker command to start mongoDB instance in our local

```
docker run --name mongodb -p 27017:27017 -e MONGODB_USERNAME=test_user -e MONGODB_ROOT_PASSWORD=root_password -e MONGODB_PASSWORD=test_password -e MONGODB_DATABASE=test_database -d bitnami/mongodb:latest
```
### CURL Commands
1. Save Product
```
curl --location --request POST 'http://localhost:8080/products' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "mobile",
"quantity": 1,
"price": 20000.0
}'
```
2. GET All Products
```
curl --location --request GET 'http://localhost:8080/products' \
--header 'Content-Type: application/json'
```
3. GET Product of particular Product ID
```
curl --location --request GET 'http://localhost:8080/products/{productId}' \
--header 'Content-Type: application/json'
```
4. GET All Products whose prize range is between 10,000 to 30,000
```
curl --location --request GET 'http://localhost:8080/products/product-range?min=10000&max=30000' \
--header 'Content-Type: application/json'
```
5. Update Product details
```
curl --location --request PUT 'http://localhost:8080/products/{id}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "mobile",
    "quantity": 1,
    "price": 15000.0
}'
```
6. Delete a particular Product Details using product ID
```
curl --location --request DELETE 'http://localhost:8080/products/{id}' \
--header 'Content-Type: application/json'
```

### Error CURL Commands
1. GET All Product when collection is empty
2. GET Product using incorrect product ID
3. GET Product whose prize range product is not present in DB
4. Update a Product whose product ID is not present in Database
5. Start the application, and then stop the Mongo DB, default mongodb timeout is around 
60 secs , but we have added the configuration to reduce the timeout to 10 secs along with
an error response