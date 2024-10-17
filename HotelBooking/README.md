# Hotel booking

Небольшой REST API «<Бронирование Отеля>» с использованием Spring MVC.

Работа с базой данных с использованием Spring Boot Data JPA. Авторизация пользователей.<br>
Фильтрация запросов с использованием спецификаций.<br>
Маппинг сущностей с использованием MapStruct.<br>

Слой статистики получает данные из Kafka и хранит в MongoDB.<br>
Предоставляет эндпоинт для выгрузки статистических данных в CSV-файл.

## Доступные запросы

### Авторизация

* [POST] http://localhost:8081/api/auth/register <br>
  Регистрация нового пользователя в системе.<br>
  Request:<br>JSON:{{"username": "","password": "","roles":[]}}

### Отели (Hotels)

Создание, редактирование и удаление отеля доступно только пользователям с правами ROLE_ADMIN.<br>
При возврате списка отелей ответ не содержит списка комнат к каждой отелю.

* [GET] http://localhost:8081/api/hotel/filter <br>
  Params:<br>
  id: <br>
  adTitle: ""<br>
  name: ""<br>
  city: ""<br>
  address: ""<br>
  maxDistanceFromCenter: <br>
  minDistanceFromCenter: <br>
  maxRating: <br>
  minRating: <br>
  maxNumberOfRatings: <br>
  minNumberOfRatings: <br>
  pageSize: NotNull, Positive<br>
  pageNumber: NotNull
* [POST] http://localhost:8081/api/hotel/rating/{id} - добавление новой оценки отеля.
  Default Request:<br>JSON:{{"value": }}
* [GET] http://localhost:8081/api/hotel
* [GET] http://localhost:8081/api/hotel/{id}
* [POST] http://localhost:8081/api/hotel
* [PUT] http://localhost:8081/api/hotel/{id}
* [DELETE] http://localhost:8081/api/hotel/{id}

Default Request:<br>JSON:{{"adTitle": "","name": "","city": "","address": "","distanceFromCenter": ""}}

Default Response:<br>JSON:{{"adTitle": "","name": "","city": "","address": "","distanceFromCenter": "","
numberOfRatings": "","rating": "","rooms":[]}}

### Комнаты (Rooms)

Создание, редактирование и удаление комнаты доступно только пользователям с правами ROLE_ADMIN.<br>

* [GET] http://localhost:8081/api/room/filter <br>
  Params:<br>
  id: <br>
  name: ""<br>
  description: ""<br>
  number: ""<br>
  maxCost: <br>
  minCost: <br>
  maxCapacity: <br>
  minCapacity: <br>
  hotelId: <br>
  checkIn: "yyyy-mm-dd"<br>
  checkOut: "yyyy-mm-dd"<br>
  pageSize: NotNull, Positive<br>
  pageNumber: NotNull
* [GET] http://localhost:8081/api/room
* [GET] http://localhost:8081/api/room/{id}
* [POST] http://localhost:8081/api/room
* [PUT] http://localhost:8081/api/room/{id}
* [DELETE] http://localhost:8081/api/room/{id}

Default Request:<br>JSON:{{"name": "","description": "","number": "","cost": ,"capacity": ,"hotelId": }}

Default Response:<br>JSON:{{"name": "","description": "","number": "","cost": ,"capacity": ,"hotelId": ,"
reservations": []}}

### Бронирования (Reservations)

Получение списка бронирований доступно только пользователям с правами ROLE_ADMIN.

* [GET] http://localhost:8081/api/reservation
* [POST] http://localhost:8081/api/reservation

Default Request:<br>JSON:{{"checkIn": "yyyy-mm-dd","checkOut": "yyyy-mm-dd","roomId": }}

Default Response:<br>JSON:{{"checkIn": "yyyy-mm-dd","checkOut": "yyyy-mm-dd","roomId": }}

### Пользователи (users)

* [GET] http://localhost:8081/api/user/{id}
* [PUT] http://localhost:8081/api/user/{id}
* [DELETE] http://localhost:8081/api/user/{id}

Default Request:<br>JSON:{{"username": "","password": "","email": ,"roles": []}}

Default Request:<br>JSON:{{"username": "","password": "","email": ,"roles": []}}

## Статистика (statistic)

Доступно только пользователям с правами ROLE_ADMIN

* [GET] http://localhost:8081/api/statistic/csv/register_event <br>
  Request:<br>CSV:{"id"}
* [GET] http://localhost:8081/api/statistic/csv/reservation_event <br>
  Request:<br>CSV:{"id","checkIn","checkOut"}

## Структура БД

Данные для слоя статистика хранятся в MongoDb в формате JSON.<br>
RegistrationEvent:<br>JSON:{{"id"}}<br>
ReservationEvent:<br>JSON:{{"id","checkIn","checkOut"}}

| table_name   | primary | column   | column   | column | column   | column               | column   | column            |
|--------------|---------|----------|----------|--------|----------|----------------------|----------|-------------------|
| hotels       | id      | ad_title | city     | name   | address  | distance_from_center | rating   | number_of_ratings |
| rooms        | id      | hotel_id | number   | name   | cost     | description          | capacity |
| reservations | id      | room_id  | user_id  | name   | check_in | check_out            |
| users        | id      | username | password | email  |
| user_roles   |         | user_id  | roles    |

## Значения по умолчанию

### application.yml

spring.datasource.url:<br> по умолчанию: jdbc:postgresql://localhost:5432/hotel_booking_db.

spring.datasource.username:<br> по умолчанию: user.

spring.datasource.password:<br> по умолчанию: pass.

spring.data.mongodb.host:<br> по умолчанию: localhost

spring.kafka.bootstrap-servers:<br> по умолчанию: localhost:9092

app.kafka.kafkaMessageTopicRegistration:<br> по умолчанию: "user-register-topic"

app.kafka.kafkaMessageTopicReservation:<br> по умолчанию: "user-reservation-topic"

app.kafka.kafkaMessageGroupId:<br> по умолчанию: "kafka-message-group-id-2"

server.port:<br> по умолчанию: 8081.

### Docker variables

SERVER_URL - url для подключения к Базе данных.<br>
По умолчанию: jdbc:postgresql://db:5432/news_service_db

SERVER_USERNAME - Имя пользователя в Базе данных.<br>
По умолчанию: user

SERVER_PASS - Пароль пользователя в Базе данных.<br>
По умолчанию: pass

MONGODB_HOST - Хост подключения mongodb.<br>
По умолчанию: mongodb

KAFKA_SERVER - адрес подключения к kafka.<br>
По умолчанию: kafka:9092

## How To Use

```
mvn clean install
docker build -t hotel-booking .
docker compose up
```
