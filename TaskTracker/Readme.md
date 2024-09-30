# Book management
Небольшой REST API «Управление книгами»  с использованием Spring MVC.

Работа со списком книг.<br>
Работа с базой данных с использованием Spring Boot Data JPA и Spring Boot Redis.<br>
## Доступные запросы

* [GET] http://localhost:8081/api/book/filter/one - фильтрация книг по названию, автору и категории.<br>
  Params:<br>
  categoryName: ""<br>
  title: ""<br>
  author: ""<br>
* [GET] http://localhost:8081/api/book
* [GET] http://localhost:8081/api/book/category/{categoryName}
* [POST] http://localhost:8081/api/book/
* [PUT] http://localhost:8081/api/book/{id}
* [DELETE] http://localhost:8081/api/book/{id}

Default Request:<br>JSON:{{"title": "","content": "","categoryName": "","author": ""}}

Default Response:<br>JSON:{{"title": "","content": "","categoryName": "","author": ""}}

## Значения по умолчанию
### Docker variables
SERVER_URL - url для подключения к Базе данных.<br>
По умолчанию: jdbc:postgresql://db:5432/news_service_db

SERVER_USERNAME - Имя пользователя в Базе данных.<br>
По умолчанию: user

SERVER_PASS - Пароль пользователя в Базе данных.<br>
По умолчанию: pass

REDIS_HOST - Хост подключения redis.<br>
По умолчанию: redis

REDIS_PORT - Порт подключения redis.<br>
По умолчанию: 6379

## How To Use
```
mvn clean install
docker build -t contact-list .
docker compose up
```