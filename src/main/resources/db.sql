DROP TABLE IF EXISTS user_table;

CREATE TABLE user_table (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL ,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    surname VARCHAR(255),
    car_number VARCHAR(255) NOT NULL,
    balance NUMERIC(10, 2) NOT NULL,
    created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user_table (username, password, name, surname, car_number, balance)
VALUES ('Banis', 'Geras','Tomas','Barauskas','XXX 777',100),
       ('Algelis', 'Blogas','Algis','Pavardenis','YYY 888',700),
       ('Saule', 'Saule','Laima','Pavasaraite','SSS 666',600),
       ('Maxima', 'Daug','Kasa','Savitarna','MMM 111',1000);
