# Kafka Example
## Order service
Небольшой REST API «Сервис заказов»  с использованием Spring MVC.
Имеет эндпоинт, на который приходит POST-запрос с сущностью Order.
Сущность Order состоит из двух полей: String product и Integer quantity.
Эндпоинт принимает сущность и отправляет в Kafka событие OrderEvent.
Событие отправляется в топик order-topic.
## Order status service
Небольшое приложение «Order status service».
Состоит из KafkaListener, который слушает топик order-topic.
Когда в слушатель приходит событие, происходит отправка другого события в топик  order-status-topic.
Это событие состоит из полей String status и Instant date.
В поле status записывается "CREATED", в поле date — текущая дата.

## Доступные запросы

* [POST] http://localhost:8081/api/kafka-service/order

Default Request:<br>JSON:{{"product": "", "quantity": }}

## How To Use

### Order status service
```
mvn clean install
docker build -t order-status .
```
### Order service
```
mvn clean install
docker build -t order-service .
docker compose up
```