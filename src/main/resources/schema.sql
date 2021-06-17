--create schema user_management;
CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1 NOCYCLE;
create table users(id number primary key, login_id varchar(50),password varchar(50),first_name varchar(50), last_name varchar(50), phone number, last_updated date);
