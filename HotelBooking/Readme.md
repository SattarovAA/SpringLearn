# Hotel booking

Небольшой REST API «<Бронирование Отеля>» с использованием Spring MVC.

Работа с базой данных Postgres с использованием Spring Boot Data JPA. Авторизация пользователей.<br>
Фильтрация запросов с использованием спецификаций.<br>
Маппинг сущностей с использованием MapStruct.<br>
Гибридный слой статистики из Kafka и MongoDB имеющий эндпоинт для выгрузки статистических данных в CSV-файл.

## How To Use

```
mvn clean install
docker build -t hotel-booking .
docker compose up
```

## Доступные запросы

## Swagger-ui

http://localhost:8088/swagger-ui/index.html

### Авторизация

* [POST] http://localhost:8088/api/auth/register <br>
  Регистрация нового пользователя в системе.

### Отели (Hotels)

Создание, редактирование и удаление отеля доступно только пользователям с правами ROLE_ADMIN.<br>
При возврате списка отелей ответ не содержит списка комнат к каждой отелю.

* [GET] http://localhost:8088/api/hotel/filter
* [POST] http://localhost:8088/api/hotel/rating/{id} - добавление новой оценки отеля.
* [GET] http://localhost:8088/api/hotel
* [GET] http://localhost:8088/api/hotel/{id}
* [POST] http://localhost:8088/api/hotel
* [PUT] http://localhost:8088/api/hotel/{id}
* [DELETE] http://localhost:8088/api/hotel/{id}

### Комнаты (Rooms)

Создание, редактирование и удаление комнаты доступно только пользователям с правами ROLE_ADMIN.<br>

* [GET] http://localhost:8088/api/room/filter
* [GET] http://localhost:8088/api/room
* [GET] http://localhost:8088/api/room/{id}
* [POST] http://localhost:8088/api/room
* [PUT] http://localhost:8088/api/room/{id}
* [DELETE] http://localhost:8088/api/room/{id}

### Бронирования (Reservations)

Получение списка бронирований доступно только пользователям с правами ROLE_ADMIN.

* [GET] http://localhost:8088/api/reservation
* [POST] http://localhost:8088/api/reservation

### Пользователи (users)

* [GET] http://localhost:8088/api/user/{id}
* [PUT] http://localhost:8088/api/user/{id}
* [DELETE] http://localhost:8088/api/user/{id}

## Статистика (statistic)

Доступно только пользователям с правами ROLE_ADMIN

* [GET] http://localhost:8088/api/statistic/csv/register_event
* [GET] http://localhost:8088/api/statistic/csv/reservation_event

## Структура БД

Данные для слоя статистика хранятся в MongoDb в формате JSON.<br>
RegistrationEvent:<br>JSON:{{"id"}}<br>
ReservationEvent:<br>JSON:{{"id","checkIn","checkOut"}}

| table_name   | primary | foreign  | foreign | column   | column               | column            | column  | column   | column | column |
|--------------|---------|----------|---------|----------|----------------------|-------------------|---------|----------|--------|--------|
| hotels       | id      |          |         | ad_title | distance_from_center | number_of_ratings | address | rating   | city   | name   |
| rooms        | id      | hotel_id |         | number   | name                 | description       | cost    | capacity |
| reservations | id      | room_id  | user_id | name     | check_in             | check_out         |
| users        | id      |          |         | email    | username             | password          |
| user_roles   |         | user_id  |         | roles    |

## Значения по умолчанию

### application.yml

spring.datasource.url:<br> по умолчанию: jdbc:postgresql://localhost:5432/hotel_booking_db.

spring.datasource.username:<br> по умолчанию: user.

spring.datasource.password:<br> по умолчанию: pass.

spring.data.mongodb.host:<br> по умолчанию: localhost

spring.data.mongodb.username:<br> по умолчанию: root

spring.data.mongodb.pass:<br> по умолчанию: root

spring.data.mongodb.database:<br> по умолчанию: appdatabase

spring.kafka.bootstrap-servers:<br> по умолчанию: localhost:9092

app.kafka.kafkaMessageTopicRegistration:<br> по умолчанию: "user-register-topic"

app.kafka.kafkaMessageTopicReservation:<br> по умолчанию: "user-reservation-topic"

app.kafka.kafkaMessageGroupId:<br> по умолчанию: "kafka-message-group-id-2"

app.kafka.listeners.registration-event<br> по умолчанию: false

app.kafka.listeners.reservation-event<br> по умолчанию: false

server.port:<br> по умолчанию: 8088.

### Docker variables

SERVER_URL - url для подключения Postgres.<br>
По умолчанию: jdbc:postgresql://db:5432/hotel_booking_db

SERVER_USERNAME - Имя пользователя в Postgres.<br>
По умолчанию: user

SERVER_PASS - Пароль пользователя в Postgres.<br>
По умолчанию: pass

MONGODB_HOST - Хост подключения MongoDb.<br>
По умолчанию: mongodb

MONGODB_SERVER_USERNAME - Имя пользователя в MongoDb.<br>
По умолчанию: root

MONGODB_SERVER_PASS - Пароль пользователя в MongoDb.<br>
По умолчанию: root

MONGODB_HOST - host для подключения MongoDb.<br>
По умолчанию: localhost

MONGODB_SERVER_DATABASE - database для подключения MongoDb.<br>
По умолчанию: appdatabase

KAFKA_SERVER - адрес подключения к kafka.<br>
По умолчанию: kafka:9092

KAFKA_LISTENERS_ENABLED - kafka listeners.<br>
По умолчанию: true