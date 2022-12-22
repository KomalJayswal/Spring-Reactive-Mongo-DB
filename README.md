# Spring Reactive Mongo DB

This is a utility tool which can be used in other services for **handling the user-defined or custom exceptions for both checked and unchecked exceptions**

## Problem Statement

Create spring reactive CRUD Operations with Mongo Database.
* Remove _class entity from DB by adding config code


### Docker command to start mongoDB instance in our local

```
docker run --name mongodb -p 27017:27017 -e MONGODB_USERNAME=test_user -e MONGODB_ROOT_PASSWORD=root_password -e MONGODB_PASSWORD=test_password -e MONGODB_DATABASE=test_database -d bitnami/mongodb:latest
```
