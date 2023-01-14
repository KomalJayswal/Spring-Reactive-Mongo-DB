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
