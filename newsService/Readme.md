# News service
Небольшой REST API «Сервис новостей»  с использованием Spring MVC.

Работа со списком новостей и комментариев к ним. Авторизация пользователей.<br>
Фильтрация запросов с использованием спецификаций.<br>
Маппинг сущностей с использованием MapStruct.<br>
Работа с базой данных с использованием Spring Boot Data JPA.<br>
## Доступные запросы

### Новости (news)
Редактирование и удаление новости разрешается только тому пользователю, который её создал.<br>
При возврате списка новостей ответ не содержит списка комментариев к каждой новости. Вместо этого каждый объект из списка содержит значение, отображающее количество комментариев {commentAmount}.

* [GET] http://localhost:8081/api/news/filter - фильтрация новостей по категории и автору.<br>
Params:<br>
categoryName: ""<br>
userName: ""<br>
pageSize: NotNull, Positive<br>
pageNumber: NotNull
* [GET] http://localhost:8081/api/news
* [GET] http://localhost:8081/api/news/{id}
* [POST] http://localhost:8081/api/news/
* [PUT] http://localhost:8081/api/news/{id}
* [DELETE] http://localhost:8081/api/news/{id}

Default Request:<br>JSON:{{"title": "","content": "","categoryName": ""}}

Default Response:<br>JSON:{{"title": "","content": "","categoryName": "","userName": "","comments":[]}}
### Комментарии (comments)
Редактирование и удаление комментария к новости разрешается только тому пользователю, который его создал.
* [GET] http://localhost:8081/api/comment
* [GET] http://localhost:8081/api/comment/{id}
* [POST] http://localhost:8081/api/comment/
* [PUT] http://localhost:8081/api/comment/{id}
* [DELETE] http://localhost:8081/api/comment/{id}

Default Request:<br>JSON:{{"content": "","newsId": ""}}

Default Response:<br>JSON:{{"content": "","userName": "","updatedTime": ""}}
### Пользователь (user)
* [GET] http://localhost:8081/api/user
* [GET] http://localhost:8081/api/user/{id}
* [POST] http://localhost:8081/api/user
* [PUT] http://localhost:8081/api/user/{id}
* [DELETE] http://localhost:8081/api/user/{id}

Default Request:<br>JSON:{{"name": ""}}

Default Response:<br>JSON:{{"name": ""}}

### Категория (category)
* [GET] http://localhost:8081/api/category
* [GET] http://localhost:8081/api/category/{id}
* [POST] http://localhost:8081/api/category
* [PUT] http://localhost:8081/api/category/{id}
* [DELETE] http://localhost:8081/api/category/{id}

Default Request:<br>JSON:{{"name": ""}}

Default Response:<br>JSON:{{"name": ""}}

## Структура БД
|table_name|primary|column|column|column|column|column|
|----------|-------|------|------|------|------|------|
|comments|id|news_id|user_id|content|creation_time|updated_time|
|news|id|category_id|user_id|title|content|
|users|id|name|uuid|
|categories|id|name|

## Значения по умолчанию
### application.yml
spring.datasource.url:<br> по умолчанию: jdbc:postgresql://localhost:5432/news_service_db.

spring.datasource.username:<br> по умолчанию: user.

spring.datasource.password:<br> по умолчанию: pass.

server.port:<br> по умолчанию: 8081.
### Docker variables
SERVER_URL - url для подключения к Базе данных.<br>
По умолчанию: jdbc:postgresql://db:5432/news_service_db

SERVER_USERNAME - Имя пользователя в Базе данных.<br>
По умолчанию: user

SERVER_PASS - Пароль пользователя в Базе данных.<br>
По умолчанию: pass

## How To Use
```
mvn clean install
docker build -t contact-list .
docker compose up
```
