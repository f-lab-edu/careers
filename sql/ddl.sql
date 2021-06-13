create table Curator(
    id int(10) not null auto_increment,
    email varchar(64) not null,
    name varchar(64) not null,
    password varchar(200) not null,
    salt varchar(64) not null,
    primary key (id),
    unique index idx_email (email)
);

create table Profile(
    profileId int(10) not null auto_increment,
    curatorId int(10) not null,
    title varchar(64),
    introduction varchar(64),
    primary key (profileId),
    foreign key (curatorId) references curator (id)
);

create table Career(
    careerId int(10) not null auto_increment,
    profileId int(10),
    company varchar(64),
    companyTitle varchar(64),
    primary key (careerId),
    foreign key (profileId) references profile (profileId)
);

create table Academic(
    academicId int(10) not null auto_increment,
    profileId int(10),
    name varchar(64),
    major varchar(64),
    primary key (academicId),
    foreign key (profileId) references profile (profileId)
);

create table Feed(
    feedId int(10) not null auto_increment,
    content varchar(64) not null,
    url varchar(64),
    date timestamp not null,
    curatorId int(10),
    primary key (feedId),
    foreign key (curatorId) references Curator (id)
);