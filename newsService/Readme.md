# News service

Небольшой REST API «Сервис новостей» с использованием Spring MVC.

Работа со списком новостей и комментариев к ним. Авторизация пользователей OAuth 2.0.<br>
Фильтрация запросов с использованием спецификаций.<br>
Маппинг сущностей с использованием MapStruct.<br>
Работа с базой данных с использованием Spring Boot Data JPA и Spring Boot Data Redis.<br>

## Доступные запросы

### Авторизация

* [POST] http://localhost:8081/api/auth/signin <br>
  Авторизация пользователя в системе.<br>
  Request:<br>JSON:{{"username": "","password": ""}} <br>
  Response:<br>JSON:{{"username": "","token": "","refreshToken": "","roles":[]}}
* [POST] http://localhost:8081/api/auth/register <br>
  Регистрация нового пользователя в системе.<br>
  Request:<br>JSON:{{"username": "","password": "","roles":[]}}
* [POST] http://localhost:8081/api/auth/refresh-token <br>
  Обновление токена.<br>
  Request:<br>JSON:{{"refreshToken": ""}} <br>
  Response:<br>JSON:{{"refreshToken": "","accessToken": ""}}
* [POST] http://localhost:8081/api/auth/logout <br>
  Выход из системы. <br>
  Request:<br>JSON:{{"username": "","password": ""}} <br>

### Новости (news)

Редактирование и удаление новости разрешается только тому пользователю, который её создал и пользователям с правами
ROLE_MODERATOR, ROLE_ADMIN.<br>
При возврате списка новостей ответ не содержит списка комментариев к каждой новости. Вместо этого каждый объект из
списка содержит значение, отображающее количество комментариев {commentAmount}.

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

Редактирование и удаление комментария к новости разрешается только тому пользователю, который его создал и пользователям
с правами ROLE_MODERATOR, ROLE_ADMIN.

* [GET] http://localhost:8081/api/comment
* [GET] http://localhost:8081/api/comment/{id}
* [POST] http://localhost:8081/api/comment/
* [PUT] http://localhost:8081/api/comment/{id}
* [DELETE] http://localhost:8081/api/comment/{id}

Default Request:<br>JSON:{{"content": "","newsId": ""}}

Default Response:<br>JSON:{{"content": "","userName": "","updatedTime": ""}}

### Пользователь (user)

Редактирование и удаление пользователя разрешается только тому пользователю, который его создал и пользователям с
правами ROLE_MODERATOR, ROLE_ADMIN.
Получение списка всех пользователей разрешается только пользователям с ролью ROLE_ADMIN.

* [GET] http://localhost:8081/api/user
* [GET] http://localhost:8081/api/user/{id}
* [PUT] http://localhost:8081/api/user/{id}
* [DELETE] http://localhost:8081/api/user/{id}

Default Request:<br>JSON:{{"name": "","password": "","roles":[]}}

Default Response:<br>JSON:{{"name": "","password": "","roles":[]}}

### Категория (category)

Создание, редактирование и удаление категории доступно только пользователей с правами ROLE_MODERATOR, ROLE_ADMIN.

* [GET] http://localhost:8081/api/category
* [GET] http://localhost:8081/api/category/{id}
* [POST] http://localhost:8081/api/category
* [PUT] http://localhost:8081/api/category/{id}
* [DELETE] http://localhost:8081/api/category/{id}

Default Request:<br>JSON:{{"name": ""}}

Default Response:<br>JSON:{{"name": ""}}

## Структура БД

|table_name| primary | column      | column   |column|column|column|
|----------|---------|-------------|----------|------|------|------|
|comments| id      | news_id     | user_id  |content|creation_time|updated_time|
|news| id      | category_id | user_id  |title|content|
|users| id      | name        | password |
|categories| id      | name        |
|user_roles| user_id | roles       |

## Значения по умолчанию

### application.yml

spring.datasource.url:<br> по умолчанию: jdbc:postgresql://localhost:5432/news_service_db.

spring.datasource.username:<br> по умолчанию: user.

spring.datasource.password:<br> по умолчанию: pass.

spring.data.redis.port:<br> по умолчанию: 6379.

spring.data.redis.host:<br> по умолчанию: localhost.

app.redis.enabled::<br> по умолчанию: false.

app.jwt.secretKey:<br> по умолчанию:  "===============secret=====Key=====Some==============="

app.jwt.tokenExpiration:<br> по умолчанию:  5m

app.jwt.refreshTokenExpiration:<br> по умолчанию:  30m

server.port:<br> по умолчанию: 8081.

### Docker variables

SERVER_URL - url для подключения к Базе данных.<br>
По умолчанию: jdbc:postgresql://db:5432/news_service_db

SERVER_USERNAME - Имя пользователя в Базе данных.<br>
По умолчанию: user

SERVER_PASS - Пароль пользователя в Базе данных.<br>
По умолчанию: pass

REDIS_ENABLED - Хост подключения redis.<br>
По умолчанию: true

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
