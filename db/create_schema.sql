CREATE TABLE IF NOT EXISTS users
(
    login    varchar(200) UNIQUE PRIMARY KEY,
    name     varchar(200),
    password varchar(200)
);
CREATE TABLE IF NOT EXISTS roles
(
    id   SERIAL PRIMARY KEY,
    name varchar(200)
);
CREATE TABLE IF NOT EXISTS roles_users
(
    id         SERIAL PRIMARY KEY,
    user_login text references users (login),
    role_id    int references roles (id)
);


create table roles
(
    id   bigserial not null,
    name varchar(255),
    primary key (id)
);
create table roles_users
(
    users_login varchar(255) not null,
    roles_id    int8         not null,
    primary key (users_login, roles_id)
);
create table users
(
    login    varchar(255) not null,
    name     varchar(255),
    password varchar(255),
    primary key (login)
);