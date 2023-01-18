create table if not exists Clients
(
    id       identity,
    login    varchar(20) not null unique,
    password varchar(20) not null,
    primary key (id)
);

create table if not exists Tasks
(
    id       identity,
    name     varchar(10) not null,
    idClient int         not null,
    status   varchar(10) not null,
    primary key (id),
    foreign key (idClient) references Clients (id)
);

create table if not exists StatusHistory
(
    id       identity,
    idClient int         not null,
    idTask   int         not null,
    status   varchar(10) not null,
    date     timestamp   not null,
    primary key (id),
    foreign key (idClient) references Clients (id),
    foreign key (idTask) references Tasks (id)
);