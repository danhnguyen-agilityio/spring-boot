create table `ride` (
`id` int not null auto_increment,
`name` varchar(100) not null,
`duration` int not null,
primary key (`id`));

insert into ride (name, duration) values ('Bike',45);
insert into ride (name, duration) values ('Motobike',60);