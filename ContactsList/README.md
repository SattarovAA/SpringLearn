# Contacts List
Небольшое веб-приложение «Список контактов».

Обработка запросов происходит через Spring MVC-контроллеры.

Интерфейс приложения реализован с помощью Thymeleaf.

Добавляет, удаляет студентов в список, выводит список в на экран.

## Страницы
### Таблица со студентами
Адрес: http://localhost:8081/contacts

Список студентов в формате таблицы «Имя|Фамилия|Электронная почта|Телефон».
#### Переход:
-- Добавить студента[http://localhost:8081/contacts/edit]

-- Edit [http://localhost:8081/contacts/{id}/edit]

-- Delete [DELETE][http://localhost:8081/contacts/{id}]
### Создание студента
Адрес: http://localhost:8081/contacts/edit

Принимает данные студентов в формате «Имя|Фамилия|Электронная почта|Телефон». Добавляет в хранилище.
### Редактирование студента
Адрес: http://localhost:8081/contacts/{id}/edit

Форма отображает текущие данные студента с {id}.

Принимает данные студентов в формате «Имя|Фамилия|Электронная почта|Телефон». Добавляет в хранилище.
### Удаление студента
Адрес: http://localhost:8081/contacts/{id}

Удаляет студента из хранилища.
## Значения по умолчанию
### application.yml
spring.datasource.url: по умолчанию: jdbc:postgresql://localhost:5432/contacts_db.

spring.datasource.username: по умолчанию: user.

spring.datasource.password: по умолчанию: pass.

server.port: 8081.
### Docker variables
SERVER_URL - url для подключения к Базе данных.

По умолчанию: jdbc:postgresql://db:5432/contacts_db

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
