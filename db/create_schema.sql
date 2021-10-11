create table if not exists roles
(
    id   bigserial not null,
    name varchar(255),
    primary key (id)
);
create table if not exists roles_users
(
    users_login varchar(255) not null,
    roles_id    int8         not null,
    primary key (users_login, roles_id)
);
create table if not exists users
(
    login    varchar(255) not null,
    name     varchar(255) not null,
    password varchar(255) not null,
    primary key (login)
);