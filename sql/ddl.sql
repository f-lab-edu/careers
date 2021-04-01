create table Curator(
    id int(10) not null auto_increment,
    email varchar(64) not null,
    name varchar(64) not null,
    password varchar(64) not null,
    salt varchar(64) not null,
    primary key (id, name), -- Profile 테이블에서 foreign key로 사용하기 위해 name도 pk설정
    unique index idx_email (email)
);

create table Profile(
    profileId int(10) not null auto_increment,
    curatorId int(10) not null,
    curatorName varchar(64) not null,
    title varchar(64),
    introduction varchar(64),
    primary key (profileId),
    foreign key (curatorId, curatorName) references curator (id, name)
);