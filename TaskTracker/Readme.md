# Task Tracker

Небольшой REST API «Трекер задач» с использованием Spring MVC.<br>

Работа с базой данных с использованием MongoDB Reactive.<br>
Авторизация пользователей OAuth 2.0.<br>

## Доступные запросы

### Авторизация

* [POST] http://localhost:8081/api/auth/signin <br>
  Авторизация пользователя в системе.<br>
  Request:<br>JSON:{{"username": "","password": ""}} <br>
  Response:<br>JSON:{{"username": "","token": ""}}
* [POST] http://localhost:8081/api/auth/register <br>
  Регистрация нового пользователя в системе.<br>
  Request:<br>JSON:{{"username": "","password": "","roles":[]}}

### Задачи (task)

* [GET] http://localhost:8081/api/task/observers - добавить наблюдателя в задачу.<br>
  Params:<br>
  taskId: ""<br>
  userId: ""<br>
* [GET] http://localhost:8081/api/task
* [GET] http://localhost:8081/api/task/{id}
* [POST] http://localhost:8081/api/task/
* [PUT] http://localhost:8081/api/task/{id}
* [DELETE] http://localhost:8081/api/task/{id}

Default JSON:{{"name": "",
"description": "",
"status": "",
"assigneeId": "",
"authorId": "",
"observerIds":[]}

### Пользователь (user)

* [GET] http://localhost:8081/api/user
* [GET] http://localhost:8081/api/user/{id}
* [POST] http://localhost:8081/api/user/
* [PUT] http://localhost:8081/api/user/{id}
* [DELETE] http://localhost:8081/api/user/{id}

  Default JSON:{{"username": "","email": ""}

## Значения по умолчанию

### application.yml

app.jwt.secretKey:<br> по умолчанию:  "===============secret=====Key=====Some==============="

app.jwt.tokenExpiration:<br> по умолчанию:  10m

### Docker variables

MONGODB_HOST - Хост подключения redis.<br>
По умолчанию: mongodb

## How To Use

```
mvn clean install
docker build -t task-tracker .
docker compose up
```
