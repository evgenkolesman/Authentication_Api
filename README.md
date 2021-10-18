Build status:

[![Build Status](https://app.travis-ci.com/evgenkolesman/Codemark.svg?branch=master)](https://app.travis-ci.com/github/evgenkolesman/Codemark)

Описание:       


Authenticatian_Api - REST сервис для работы с сущностями "Role", "User" согласно задания.
(задание - task.md) Для работы приложения используется PostgreSQL.

Работа приложения. Производим запуск приложения через консоль или в IDEA

REST запросы:

Работаем с сущностями "User":

1. Запрос на поиск всех объектов User в БД:

GET http://localhost:8080/user/

2. Создать объект User:

POST http://localhost:8080/user/
Content-Type: application/json

{
"login": "AlexUser",
"name": "Alex",
"password": "Password123",
"roles": null }

где login - логин, name - имя, password - пароль, roles - массив ролей

3. Изменить объект User:

PUT http://localhost:8080/user/
Content-Type: application/json

{
"login": "AlexUser",
"name": "Alex",
"password": "Password123",
"roles": [{"id":1,"name":"ADMIN"}]
}

4. Запрос поиска пользователя по логину

   GET http://localhost:8080/user/AlexUser

5. Удалить пользователя по логину

   DELETE http://localhost:8080/user/AlexUser

Работаем с сущностями "Role"(указаний по данной сущности не было сделал 3 основных метода, при необходимости функционал
быстро можно расширить):

1. Запрос на поиск всех объектов Role в БД:

GET http://localhost:8080/role/

2. Создать объект Role:

POST http://localhost:8080/role/
Content-Type: application/json

{
"id":null,
"name":"ADMIN",
"users":null
}

3. Удалить объект Role по идентификатору:

DELETE http://localhost:8080/role/1

Комментарии:

- Я писал исходя из того что надо написать без фронта.

- Исходя из связей ManyToMany, хотя BestPractice рекомендуют их избегать, 
но брал в расчет, что задание тестовое.

- БД выбрал по своему усмотрению 

- Никаких допов типа Liquebase, Heroku, Docker и тд не подключал в задании не было...
