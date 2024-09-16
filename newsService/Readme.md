# News service
Небольшой REST API «Сервис новостей»  с использованием Spring MVC.


Работа со списком новостей и комментариев к ним. Авторизация пользователей.
Фильтрация запросов с использованием спецификаций.
Маппинг сущностей с использованием MapStruct.
Работа с базой данных с использованием Spring Boot Data JPA.

## Доступные запросы

#### Новости (news)
Редактирование и удаление новости разрешается только тому пользователю, который её создал.
При возврате списка новостей ответ не содержит списка комментариев к каждой новости. Вместо этого каждый объект из списка содержит значение, отображающее количество комментариев {commentAmount}.
[GET] http://localhost:8081/api/news/filter - фильтрация новостей по категории и автору.
Params:
categoryName: ""
userName: ""
pageSize: NotNull, Positive
pageNumber: NotNull
[GET] http://localhost:8081/api/news
[GET] http://localhost:8081/api/news/{id}
[POST] http://localhost:8081/api/news/
[PUT] http://localhost:8081/api/news/{id}
[DELETE] http://localhost:8081/api/news/{id}
###### Default Request:
JSON:{{"title": "","content": "","categoryName": ""}}
###### Default Response:
JSON:{{"title": "","content": "","categoryName": "","userName": "","comments":[]
}}
#### Комментарии (comments)
Редактирование и удаление комментария к новости разрешается только тому пользователю, который его создал.
[GET] http://localhost:8081/api/comment
[GET] http://localhost:8081/api/comment/{id}
[POST] http://localhost:8081/api/comment/
[PUT] http://localhost:8081/api/comment/{id}
[DELETE] http://localhost:8081/api/comment/{id}

###### Default Request:
JSON:{{"content": "","newsId": ""}}
###### Default Response:
JSON:{{"content": "","userName": "","updatedTime": ""}}
#### Пользователь (user)
[GET] http://localhost:8081/api/user
[GET] http://localhost:8081/api/user/{id}
[POST] http://localhost:8081/api/user
[PUT] http://localhost:8081/api/user/{id}
[DELETE] http://localhost:8081/api/user/{id}
###### Default Request:
JSON:{{"name": ""}}
###### Default Response:
JSON:{{"name": ""}}

#### Категория (category)
[GET] http://localhost:8081/api/category
[GET] http://localhost:8081/api/category/{id}
[POST] http://localhost:8081/api/category
[PUT] http://localhost:8081/api/category/{id}
[DELETE] http://localhost:8081/api/category/{id}
###### Default Request:
JSON:{{"name": ""}}
###### Default Response:
JSON:{{"name": ""}}

## Структура БД
Список пользователей хранится в таблице users в формате:
«id|name|uuid».
Список категорий хранится в таблице categories в формате:
«id|name».
Список новостей хранится в таблице news в формате:
«id|category_id|user_id|title|content».
Список комментариев хранится в таблице comments в формате:
«id|news_id|user_id|content|creation_time|updated_time».
## Значения по умолчанию
### application.yml
spring.datasource.url: по умолчанию: jdbc:postgresql://localhost:5432/news_service_db.
spring.datasource.username: по умолчанию: user.
spring.datasource.password: по умолчанию: pass.
server.port: 8081.
### Docker variables
SERVER_URL - url для подключения к Базе данных.
По умолчанию: jdbc:postgresql://db:5432/news_service_db
SERVER_USERNAME - Имя пользователя в Базе данных.
По умолчанию: user
SERVER_PASS - Пароль пользователя в Базе данных.
По умолчанию: pass
## How To Use
```
mvn clean install
docker build -t contact-list .
docker compose up
```