
create table medicos(

    id bigint not null auto_increment,
    name varchar(100) not null,
    email varchar(100) not null unique,
    document varchar(6) not null unique,
    specialty varchar(100) not null,
    street varchar(100) not null,
    district varchar(100) not null,
    number varchar(20),
    city varchar(100) not null,
    phoneNumber varchar(20) not null,
    active tinyint,

    primary key(id)

);
update medicos set active = 1;