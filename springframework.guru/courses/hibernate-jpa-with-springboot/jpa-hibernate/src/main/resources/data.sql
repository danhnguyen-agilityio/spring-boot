insert into course (id, name, last_updated_date, created_date) values (10001, 'David', sysdate(), sysdate());
insert into course (id, name, last_updated_date, created_date) values (10002, 'Lusi', sysdate(), sysdate());
insert into course (id, name, last_updated_date, created_date) values (10003, 'Naven', sysdate(), sysdate());

insert into passport(id, number) values (40001, 'E123456');
insert into passport(id, number) values (40002, 'N123457');
insert into passport(id, number) values (40003, 'L123890');

insert into student(id, name, passport_id) values (20001, 'Ranga', 40001);
insert into student(id, name, passport_id) values (20002, 'Adam', 40002);
insert into student(id, name, passport_id) values (20003, 'Jane', 40003);

insert into review(id, rating, description, course_id) values (50001, '5', 'Greating Course', 10001);
insert into review(id, rating, description, course_id) values (50002, '4', 'Wonderful Course', 10002);
insert into review(id, rating, description, course_id) values (50003, '5', 'Awesome Course', 10003);