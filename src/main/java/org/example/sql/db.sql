CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       name VARCHAR (100) NOT NULL,
                       surname VARCHAR (100) NOT NULL,
                       age DATE NOT NULL,
                       team VARCHAR (50) NOT NULL
);

CREATE TABLE marks (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       lesson_date DATE NOT NULL,
                       user_one_id UUID NOT NULL,
                       user_one_mark DOUBLE PRECISION,
                       user_two_id UUID NOT NULL,
                       user_two_mark DOUBLE PRECISION,
                       CONSTRAINT fk_user_one FOREIGN KEY (user_one_id) REFERENCES users (id),
                       CONSTRAINT fk_user_two FOREIGN KEY (user_two_id) REFERENCES users (id)
);