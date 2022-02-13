# Credit Application

Credit Application System, It takes credit application requests and gives response according to customer information.

## Running
Credit API is a Spring Boot via maven. You can build a jar and deploy with docker.
```bash
git clone https://github.com/0uz/CreditProject.git
mvn clean install -U -DskipTests=true
docker-compose build --no-cache
docker-compose up --force-recreate
```

---

## Technologies
:white_check_mark: Spring Boot <br>
:white_check_mark: Postgresql <br>
:white_check_mark: Redis <br>
:white_check_mark: Docker <br>
###Tools
:white_check_mark: Swagger Open API <br>
:white_check_mark: Redis Commander <br>

---
## Documentation
###Swagger
```html
http://localhost:8080/swagger-ui/index.html
```
![swagger](photo/swagger-ui.png)

###Redis Commander
```html
http://localhost:8081
```

<img src="photo/redis-commander.png" width="500">

---
##Frontend
```html
cd CreditProjectFrontend
.\landingPage.html
```
<img src="photo/login.png" width="500"> <br>
<img src="photo/register.png" width="500"> <br>
<img src="photo/creditResult.png" width="500"><br>
---
##Exception Handling
Exceptions handled<br>
![exception](photo/exceptionHandle1.png)
![exception](photo/exceptionHandle2.png)
---

## Tests
Integration and Unit tests written.<br>
![tests](photo/tests.png)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
