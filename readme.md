## Useful commands

```
docker run --rm -p 33060:3306 -e MYSQL_ROOT_PASSWORD=sa -e MYSQL_DATABASE=test_db -e MYSQL_USER=user -e MYSQL_PASSWORD=password mysql:5.7.31

mvn clean package

eval $(egrep -v '^#' .env | xargs) java -jar target/hibernate-logging-1.0-SNAPSHOT-jar-with-dependencies.jar
```
