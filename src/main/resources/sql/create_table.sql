create table account
(
    id          varchar(255) not null
        primary key,
    created_at  datetime     not null,
    updated_at  datetime     null,
    category_id varchar(255) null,
    name        varchar(255) null,
    password    varchar(255) null,
    type        varchar(255) null,
    user_id     varchar(255) null,
    username    varchar(255) null
);

create table category
(
    id         varchar(255) not null
        primary key,
    created_at datetime     not null,
    updated_at datetime     null,
    name       varchar(255) null,
    type       varchar(255) null,
    user_id    varchar(255) null
);

create table user
(
    id         varchar(255) not null
        primary key,
    created_at datetime     not null,
    updated_at datetime     null,
    email      varchar(255) null,
    name       varchar(255) null,
    password   varchar(255) null,
    username   varchar(255) null
);

create table refresh_token
(
    id          varchar(255) not null
        primary key,
    created_at  datetime     not null,
    updated_at  datetime     null,
    expiry_date datetime     not null,
    token       varchar(255) not null,
    user_id     varchar(255) null,
    constraint UK_r4k4edos30bx9neoq81mdvwph
        unique (token),
    constraint FKfgk1klcib7i15utalmcqo7krt
        foreign key (user_id) references user (id)
);
