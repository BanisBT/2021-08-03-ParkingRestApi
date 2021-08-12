DROP TABLE IF EXISTS user_table;
DROP TABLE IF EXISTS parking_fine;
DROP TABLE IF EXISTS parking_ticket;
DROP TABLE IF EXISTS parking_record_status;
DROP TABLE IF EXISTS parking_zone;
DROP TABLE IF EXISTS parking_city;

CREATE TABLE parking_record_status
(
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    parking_status VARCHAR(255) UNIQUE   NOT NULL,
    created        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE parking_zone
(
    id      BIGSERIAL PRIMARY KEY NOT NULL,
    name    VARCHAR(255) UNIQUE   NOT NULL,
    created timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
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

CREATE TABLE parking_fine
(
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    fine_date_time TIMESTAMP             NOT NULL,
    fine_amount    NUMERIC(6, 2),
    user_id        BIGINT REFERENCES user_table (id),
    parking_status BIGINT REFERENCES parking_record_status (id),
    parking_city   BIGINT REFERENCES parking_city (id),
    parking_zone   BIGINT REFERENCES parking_zone (id),
    created        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE parking_ticket
(
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    ticket_began   TIMESTAMP             NOT NULL,
    ticket_end     TIMESTAMP,
    ticket_amount  NUMERIC,
    user_id        BIGINT REFERENCES user_table (id),
    parking_status BIGINT REFERENCES parking_record_status (id),
    parking_city   BIGINT REFERENCES parking_city (id),
    parking_zone   BIGINT REFERENCES parking_zone (id),
    created        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO parking_record_status (parking_status)
VALUES ('OPEN'),
       ('PAID'),
       ('UNPAID');

INSERT INTO parking_city(name)
VALUES ('VILNIUS'),
       ('KAUNAS'),
       ('KLAIPEDA');

INSERT INTO parking_zone(name)
VALUES ('VILNIUS_BLUE_ZONE'),
       ('VILNIUS_RED_ZONE'),
       ('VILNIUS_GREEN_ZONE'),
       ('KAUNAS_BLUE_ZONE'),
       ('KAUNAS_RED_ZONE'),
       ('KLAIPEDA_BLUE_ZONE');

INSERT INTO user_table (username, password, name, surname, car_number, balance)
VALUES ('Banis', 'Geras', 'Tomas', 'Barauskas', 'XXX 777', 100),
       ('Algelis', 'Blogas', 'Algis', 'Pavardenis', 'YYY 888', 700),
       ('Saule', 'Saule', 'Laima', 'Pavasaraite', 'SSS 666', 600),
       ('Maxima', 'Daug', 'Kasa', 'Savitarna', 'MMM 111', 1000);

INSERT INTO parking_fine (fine_date_time, fine_amount, user_id, parking_status, parking_city, parking_zone)
VALUES ('2021-04-05 10:30:00', 100, 1, 2, 1, 3),
       ('2021-04-07 11:33:33', 300, 2, 3, 1, 2),
       ('2021-04-10 11:33:33', 200, 2, 3, 1, 4),
       ('2021-04-11 11:33:33', 400, 2, 2, 2, 5),
       ('2021-04-12 11:33:33', 550, 2, 3, 3, 6);

INSERT INTO parking_ticket(ticket_began, ticket_end, ticket_amount, user_id, parking_status, parking_city, parking_zone)
VALUES ('2021-04-05 10:30:00', '2021-04-05 12:30:00', 10, 2, 1, 1, 3),
       ('2021-04-06 08:30:00', '2021-04-06 10:30:00', 20, 4, 2, 2, 5),
       ('2021-04-10 08:30:00', '2021-04-06 10:30:00', 20, 3, 3, 3, 6);
