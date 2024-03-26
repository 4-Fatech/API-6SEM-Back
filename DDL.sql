drop schema if exists spring;
drop user if exists 'user'@'localhost';

create schema spring;
create user 'user'@'localhost' identified by 'pass123';
grant select, insert, delete, update on spring.* to user@'localhost';
use spring;

CREATE TABLE log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    entrada_saida ENUM('1', '0') NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


insert into log (entrada_saida, data)
values ('1', '2023-08-01'), -- Entrada
       ('0', '2023-08-01'); -- Saída