CREATE database Users;
CREATE TABLE user_info
(
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(50) NOT NULL,
    second_name VARCHAR(50) NOT NULL,
    age         INT         NOT NULL,
    team        VARCHAR(50) NOT NULL
);