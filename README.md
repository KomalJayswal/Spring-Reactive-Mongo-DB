# Spring Reactive Mongo DB

### Docker command to start mongoDB instance in our local

```
docker run --name mongodb -p 27017:27017 -e MONGODB_USERNAME=test_user -e MONGODB_ROOT_PASSWORD=root_password -e MONGODB_PASSWORD=test_password -e MONGODB_DATABASE=test_database -d bitnami/mongodb:latest
```

### Add Profile

Edit configuration -> modify options -> Add VM options 
```
-Dspring.profiles.active=local
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
