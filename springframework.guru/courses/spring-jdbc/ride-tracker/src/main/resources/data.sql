create table `ride` (
`id` int not null auto_increment,
`name` varchar(100) not null,
`duration` int not null,
primary key (`id`));

alter table `ride` add ride_date DATETIME AFTER duration;

insert into ride (name, duration) values ('Bike',45);
insert into ride (name, duration) values ('Motobike',60);