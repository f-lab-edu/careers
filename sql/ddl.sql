create table Curator(
    id int(10) not null auto_increment,
    email varchar(64) not null,
    name varchar(64) not null,
    password varchar(64) not null,
    salt varchar(64) not null,
    primary key (id),
    unique index idx_email (email)
);
