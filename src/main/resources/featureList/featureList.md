### feature/Curd

* Create spring reactive CRUD Operations with Mongo Database.
* Add Junit
* Standard coding practice

### feature/Remove_underscore_class

* Cut a branch from feature/Curd
* Remove _class productEntity from DB by adding config code

### feature/

* Cut a branch from feature/
* Modify default mongodb timeout from 60 secs to 10 secs

### feature/add_exception_handling

* Cut a branch from feature/
* Add Exception Handling
  * Error Cases :-
    1. GET All Product when collection is empty
    2. GET Product using incorrect product ID
    3. GET Product whose prize range product is not present in DB
    4. Update a Product whose product ID is not present in Database
    5. Start the application, and then stop the Mongo DB, default mongodb timeout is around
       60 secs , but we have added the configuration to reduce the timeout to 10 secs along with
       an error response

### feature/add_ID_Generator

* Cut a branch from feature/
* Add an ID generator

### feature/add_kafka_consumer

* Cut a branch from feature/
* Add Kafka Consumer 







