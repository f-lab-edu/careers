create table Curator(
    id int(10) not null auto_increment,
    email varchar(64) not null,
    name varchar(64) not null,
    password varchar(64) not null,
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

create table Voting(
  votingId int(10),
  votingTitle varchar(20) NOT NULL,
  votingWriter int(10) NOT NULL,
  votingExplanation varchar(50) NOT NULL,
  timestamp TIMESTAMP,
  deadline TIMESTAMP,
  PRIMARY KEY (votingId),
  FOREIGN KEY (votingWriter) REFERENCES Curator(id)
);

CREATE TABLE VotingItem(
  votingItemId int(10) auto_increment,
  votingId int(10) NOT NULL,
  votingItemName varchar(20) NOT NULL,
  voteCount int(11) DEFAULT 0,
  PRIMARY KEY(votingItemId),
  FOREIGN KEY(votingId) REFERENCES Voting(votingId)
);