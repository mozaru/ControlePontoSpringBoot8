DROP TABLE IF EXISTS teste;

CREATE TABLE teste (
    id IDENTITY NOT NULL PRIMARY KEY,
    name varchar(100) NOT NULL
);

insert into teste (name) values ('abacate');
insert into teste (name) values ('pera');
insert into teste (name) values ('maca');
insert into teste (name) values ('tomate');
insert into teste (name) values ('abacaxi');
insert into teste (name) values ('banana');
insert into teste (name) values ('uva');

