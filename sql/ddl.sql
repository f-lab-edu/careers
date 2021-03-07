create table Curator(
    email    varchar(64) not null primary key,
    username varchar(64) not null,
    password varchar(64) not null,
    salt     varchar(64) not null
);