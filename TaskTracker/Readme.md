# Task Tracker

Небольшой REST API «Трекер задач» с использованием Spring MVC.

Работа с базой данных с использованием MongoDB Reactive.<br>

## Доступные запросы

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

### Docker variables

MONGODB_HOST - Хост подключения redis.<br>
По умолчанию: mongodb

## How To Use

```
mvn clean install
docker build -t task-tracker .
docker compose up
```