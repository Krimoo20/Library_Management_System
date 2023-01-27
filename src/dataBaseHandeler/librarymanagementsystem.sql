CREATE DATABASE librarymanagementsystem;
CREATE TABLE book(
    id varchar(30) primary key,
    Title varchar(30),
    Auther varchar(30),
    Publisher varchar(30),
    Availble varchar(30),
a
);
CREATE TABLE issuedbook(
     FOREIGN KEY (bookID) REFERENCES book(id) 
     FOREIGN KEY (memberID) REFERENCES members(id)  
     issueTime timestamp(5),
     renew_count int(20),
);
CREATE TABLE members(
    id varchar(30) primary key ,
    name varchar(30),
    mobile int(20),
);
CREATE TABLE users(
    id varchar(30),
    username varchar(30),
    password varchar(30),
);