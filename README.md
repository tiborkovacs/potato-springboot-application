# Potato application

### What is this?
This is a Spring Boot based application, which represents a  "Potato Market".

It handles suppliers and their products "a bag of potato".

Some dummy data is populated during the startup time:
* 5 types of suppliers
* Each supplier has 1-5 bag of potatoes on the market

### How to use?
* Clone the repository
* Execute `./gradlew bootRun`
  * This will compile and start the Spring Boot application
* Open http://localhost:8080/swagger-ui.html and you can start hacking

If you prefer to use Postman or any other REST tools, you can access the application via the following URLs:
* http://localhost:8080/api/suppliers
  * GET: Retrieve all of the suppliers

  * POST: Requires a valid `Supplier` as RequestBody

* http://localhost:8080/api/bags\[?count=Integer\]
  * GET: Retrieve `count` amount of potato bags.
  As requested this parameter is optional, the default value is 3.
  To retrieve all of the potato bags, use negative number, like `-1`.
  
  * POST: Requires a valid `PotatoBag` as RequestBody
