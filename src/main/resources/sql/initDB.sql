DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS meal;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS restaurant_order;

DROP SEQUENCE IF EXISTS global_seq;

ALTER SEQUENCE global_seq RESTART WITH 100000;

CREATE TABLE public.restaurant
(
    name character varying(40) COLLATE pg_catalog."default" NOT NULL,
    id   INTEGER PRIMARY KEY DEFAULT nextval('global_seq')
);

CREATE TABLE public.meal
(
    name          character varying(40) COLLATE pg_catalog."default" NOT NULL,
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    restaraunt_id INTEGER                                            NOT NULL,
    meal_type     TEXT                DEFAULT 'Not In Menu',
    price         INTEGER                                            NOT NULL,
    FOREIGN KEY (restaraunt_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE users
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name       VARCHAR                           NOT NULL,
    email      VARCHAR                           NOT NULL,
    password   VARCHAR                           NOT NULL,
    registered TIMESTAMP           DEFAULT now() NOT NULL,
    enabled    BOOL                DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE role
(
    id   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name VARCHAR NOT NULL
);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);

CREATE TABLE menu
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    restaurant_id INTEGER                           NOT NULL,
    created       TIMESTAMP           DEFAULT now() NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE restaurant_order
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id       INTEGER NOT NULL,
    restaurant_id INTEGER NOT NULL,
    CONSTRAINT user_restaurant_idx UNIQUE (user_id, restaurant_id, created),
    created       TIMESTAMP           DEFAULT now(),
    is_enabled    BOOL                DEFAULT false,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

INSERT INTO USERS (name, email, password)
VALUES ('Admin', 'admin@gmail.com', '$2a$10$IqTJTjn39IU5.7sSCDQxzu3xug6z/LPU6IF0azE/8CkHCwYEnwBX.'),
       ('Oleg', 'oleg@mail.ru', '$2a$10$Tl7q5gHahZGbb.GEr5Zr9u./qM1U./EQOHr3wkop3c4pG43S55rRq');

INSERT INTO ROLE(name)
VALUES ('ADMIN'),
       ('USER');

INSERT INTO USER_ROLE (USER_ID, ROLE_ID)
VALUES ('100008', '100010');
VALUES ('100001', '100003');

INSERT INTO RESTAURANT (NAME)
VALUES ('Тануки'),
       ('Макдональдс');

INSERT INTO MEAL (NAME, RESTARAUNT_ID, PRICE)
VALUES ('Калифорния', '100004', '450'),
       ('Мисо суп', '100004', '250'),
       ('Жареный угорь', '100004', '650'),
       ('Пенне Арабьята', '100005', '634'),
       ('Цезарь с креветками', '100005', '391'),
       ('Пицца Барбекю', '100005', '580');
