DROP TABLE IF EXISTS user_table;
DROP TABLE IF EXISTS parking_fine;
DROP TABLE IF EXISTS parking_ticket;

CREATE TABLE user_table
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    username   VARCHAR(255) UNIQUE   NOT NULL,
    password   VARCHAR(255)          NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    surname    VARCHAR(255)          NOT NULL,
    car_number VARCHAR(255)          NOT NULL,
    balance    NUMERIC(10, 2)        NOT NULL,
    created    timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated    timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE parking_fine
(
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    fine_date_time DATE                  NOT NULL,
    fine_amount    NUMERIC(6, 2),
    created        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated        timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE parking_ticket
(
    id            BIGSERIAL PRIMARY KEY NOT NULL,
    ticket_began  DATE                  NOT NULL,
    ticket_end    DATE,
    ticket_amount NUMERIC,
    created       timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated       timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user_table (username, password, name, surname, car_number, balance)
VALUES ('Banis', 'Geras', 'Tomas', 'Barauskas', 'XXX 777', 100),
       ('Algelis', 'Blogas', 'Algis', 'Pavardenis', 'YYY 888', 700),
       ('Saule', 'Saule', 'Laima', 'Pavasaraite', 'SSS 666', 600),
       ('Maxima', 'Daug', 'Kasa', 'Savitarna', 'MMM 111', 1000);

INSERT INTO parking_fine (fine_date_time, fine_amount)
VALUES ('2021-04-05 10:30:00', 100),
       ('2021-04-06 11:33:33', 300);

INSERT INTO parking_ticket(ticket_began, ticket_end, ticket_amount)
VALUES ('2021-04-05 10:30:00', '2021-04-05 12:30:00', 10),
       ('2021-04-06 08:30:00', '2021-04-06 10:30:00', 20);
