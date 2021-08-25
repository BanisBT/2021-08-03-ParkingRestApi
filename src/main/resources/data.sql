DROP TABLE IF EXISTS user_table CASCADE;
DROP TABLE IF EXISTS parking_fine;
DROP TABLE IF EXISTS parking_ticket;
DROP TABLE IF EXISTS parking_record_status;
DROP TABLE IF EXISTS parking_zone;
DROP TABLE IF EXISTS parking_city;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles
(
    id      BIGSERIAL PRIMARY KEY NOT NULL,
    name    VARCHAR(255) UNIQUE   NOT NULL,
    created timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE parking_record_status
(
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    parking_status VARCHAR(255) UNIQUE   NOT NULL,
    created        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE parking_zone
(
    id            BIGSERIAL PRIMARY KEY NOT NULL,
    name          VARCHAR(255) UNIQUE   NOT NULL,
    fine          NUMERIC(8, 2)         NOT NULL,
    cost_per_hour NUMERIC(6, 2)         NOT NULL,
    created       timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated       timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE parking_city
(
    id      BIGSERIAL PRIMARY KEY NOT NULL,
    name    VARCHAR(255) UNIQUE   NOT NULL,
    created timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_table
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    username   VARCHAR(255) UNIQUE   NOT NULL,
    password   VARCHAR(255)          NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    surname    VARCHAR(255)          NOT NULL,
    car_number VARCHAR(255)          NOT NULL,
    balance    NUMERIC(10, 2),
    created    timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated    timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_role
(
    user_id  BIGINT REFERENCES user_table (id),
    roles_id BIGINT REFERENCES roles (id)
);

CREATE TABLE parking_fine
(
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    fine_date_time TIMESTAMP             NOT NULL,
    fine_amount    NUMERIC(6, 2),
    user_id        BIGINT REFERENCES user_table (id) ON DELETE CASCADE,
    parking_status BIGINT REFERENCES parking_record_status (id) ON DELETE CASCADE,
    parking_city   BIGINT REFERENCES parking_city (id) ON DELETE CASCADE,
    parking_zone   BIGINT REFERENCES parking_zone (id) ON DELETE CASCADE,
    created        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE parking_ticket
(
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    ticket_began   TIMESTAMP             NOT NULL,
    ticket_end     TIMESTAMP,
    ticket_amount  NUMERIC,
    user_id        BIGINT REFERENCES user_table (id) ON DELETE CASCADE,
    parking_status BIGINT REFERENCES parking_record_status (id) ON DELETE CASCADE,
    parking_city   BIGINT REFERENCES parking_city (id) ON DELETE CASCADE,
    parking_zone   BIGINT REFERENCES parking_zone (id) ON DELETE CASCADE,
    created        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO roles (name)
VALUES ('REGULAR'),
       ('MANAGER');

INSERT INTO parking_record_status (parking_status)
VALUES ('OPEN'),
       ('PAID'),
       ('UNPAID');

INSERT INTO parking_city(name)
VALUES ('VILNIUS'),
       ('KAUNAS'),
       ('KLAIPEDA');

INSERT INTO parking_zone(name, cost_per_hour, fine)
VALUES ('VILNIUS_BLUE_ZONE', 10, 100),
       ('VILNIUS_RED_ZONE', 8, 80),
       ('VILNIUS_GREEN_ZONE', 6, 60),
       ('KAUNAS_BLUE_ZONE', 5, 50),
       ('KAUNAS_RED_ZONE', 4, 40),
       ('KLAIPEDA_BLUE_ZONE', 3, 30);

INSERT INTO user_table (username, password, name, surname, car_number, balance)
VALUES ('Banis', '{bcrypt}$2a$10$WJvAKW5R1VM2SSaAWf0WYO/FBcovz6X3BpulRoS2FWdUbcCZPo8V2', 'Tomas', 'Barauskas',
        'XXX 777', 100),
       ('Algelis', 'Blogas', 'Algis', 'Pavardenis', 'YYY 888', 700),
       ('Admin', '{bcrypt}$2a$10$WJvAKW5R1VM2SSaAWf0WYO/FBcovz6X3BpulRoS2FWdUbcCZPo8V2', 'Admin', 'Admin', 'X', 1),
       ('Saule', 'Saule', 'Laima', 'Pavasaraite', 'SSS 666', 1),
       ('Maxima', 'Daug', 'Kasa', 'Savitarna', 'MMM 111', 1000);

INSERT INTO user_role(user_id, roles_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (3, 2),
       (4, 1),
       (5, 1);

INSERT INTO parking_fine (fine_date_time, fine_amount, user_id, parking_status, parking_city, parking_zone)
VALUES ('2021-04-05 10:30:00', 100, 1, 3, 1, 3),
       ('2021-04-07 11:33:33', 300, 1, 3, 1, 2),
       ('2021-04-10 11:33:33', 200, 2, 3, 1, 4),
       ('2021-04-11 11:33:33', 400, 2, 2, 2, 5),
       ('2021-04-12 11:33:33', 550, null, 3, 3, 6);

INSERT INTO parking_ticket(ticket_began, ticket_end, ticket_amount, user_id, parking_status, parking_city, parking_zone)
VALUES ('2021-04-05 10:30:00', '2021-04-05 12:30:00', 10, 1, 1, 1, 3),
       ('2021-04-06 08:30:00', '2021-04-06 10:30:00', null, 4, 1, 2, 5),
       ('2021-04-10 08:30:00', '2021-04-06 10:30:00', 20, 3, 3, 3, 6);
