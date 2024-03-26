drop schema if exists spring;
drop user if exists 'user'@'localhost';

create schema spring;
create user 'user'@'localhost' identified by 'pass123';
grant select, insert, delete, update on spring.* to user@'localhost';
use spring;

CREATE TABLE log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    entrada BOOLEAN NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


insert into log (entrada, data)
values (TRUE, '2023-08-01 08:00:00'), -- Entrada
       (FALSE, '2023-08-01 17:00:00'); -- Saída
