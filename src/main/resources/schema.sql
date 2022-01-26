create table if not exists user_data
(
    username              varchar(75)                                                  not null
        primary key,
    password              text                                                         not null,
    authority             enum ('ROLE_ADMINISTRATOR', 'ROLE_MODERATOR', 'ROLE_MEMBER') not null,
    is_enabled            tinyint(1) default 1                                         not null
);

create table if not exists refresh_tokens
(
    id    varchar(36) not null
        primary key,
    token text        not null,
    user  varchar(75) not null,
    constraint refresh_tokens_ibfk_1
        foreign key (user) references user_data (username)
);