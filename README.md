# notes

Application to manage notes. Application use h2 in memory database

## Requirements

For building and running the application you need:

- [JDK 1.11](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `de.codecentric.springbootsample.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

# Examples
## To get all notes 
curl http://localhost:8080/note/all/
## To get a note with an id
curl http://localhost:8080/note/1
## To remove note 
curl -X DELETE http://localhost:8080/note/1
## To create note
curl -X POST -H "Content-Type: application/json" -d '{"title":"ExmpleTitle","content":"ExampleContent"}' http://localhost:8080/note/
## To modify note
curl -X PUT -H "Content-Type: application/json" -d '{"id":"2","title":"ExmpleTitle","content":"ExampleChange"}' http://localhost:8080/note/
## To get note history
curl http://localhost:8080/note/history/1
