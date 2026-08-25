DROP DATABASE IF EXISTS jdbc_course_db;

CREATE DATABASE jdbc_course_db;

USE jdbc_course_db;

CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30),
    gender BOOLEAN,
);