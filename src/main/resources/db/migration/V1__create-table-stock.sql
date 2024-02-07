CREATE DATABASE IF NOT EXISTS mandakaru_broker;


CREATE TABLE stock(
                      id VARCHAR(20) PRIMARY KEY,
                      symbol VARCHAR(3) NOT NULL,
                      company_name VARCHAR(25) NOT NULL,
                      price FLOAT NOT NULL
);