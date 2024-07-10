CREATE TABLE users (
                       id serial PRIMARY KEY,
                       name VARCHAR (100) NOT NULL,
                       surname VARCHAR (100) NOT NULL,
                       age DATE NOT NULL,
                       team VARCHAR (50) NOT NULL
);

CREATE TABLE marks
(
    id           SERIAL PRIMARY KEY,
    lesson_date  TIMESTAMP NOT NULL,
    user_one_id  INT NOT NULL,
    user_one_mark DOUBLE PRECISION,
    user_two_id  INT NOT NULL,
    user_two_mark DOUBLE PRECISION,
    CONSTRAINT fk_user_one FOREIGN KEY (user_one_id) REFERENCES users ( id ),
    CONSTRAINT fk_user_two FOREIGN KEY (user_two_id) REFERENCES users ( id )
);