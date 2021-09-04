DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS restaurant_order;
DROP TABLE IF EXISTS menu_meal;
DROP TABLE IF EXISTS meal;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS role;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE public.restaurant
(
    name character varying(40) COLLATE pg_catalog."default" NOT NULL,
    id   INTEGER PRIMARY KEY DEFAULT nextval('global_seq')
);

CREATE TABLE public.meal
(
    name          character varying(40) COLLATE pg_catalog."default" NOT NULL,
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    restaurant_id INTEGER                                            NOT NULL,
    meal_type     TEXT                DEFAULT 'Not In Menu',
    price         INTEGER                                            NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX meals_unique_restaurant_name_idx ON meal (restaurant_id, name);

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
CREATE UNIQUE INDEX users_unique_role_idx ON user_role (user_id, role_id);

CREATE TABLE menu
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    restaurant_id INTEGER                           NOT NULL,
    created       TIMESTAMP           DEFAULT now() NOT NULL,
    is_enabled    BOOL                DEFAULT false,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX unique_menu_idx ON menu (restaurant_id, created);

CREATE TABLE restaurant_order
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id       INTEGER NOT NULL,
    restaurant_id INTEGER NOT NULL,
    CONSTRAINT user_restaurant_idx UNIQUE (user_id, restaurant_id, created),
    created       TIMESTAMP           DEFAULT now(),
    is_enabled    BOOL                DEFAULT true,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX unique_restaurant_order_idx ON restaurant_order (restaurant_id, user_id, created);

CREATE TABLE menu_meal
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    menu_id INTEGER NOT NULL,
    meal_id INTEGER NOT NULL,
    CONSTRAINT menu_meal_idx UNIQUE (menu_id, meal_id, created),
    created       TIMESTAMP           DEFAULT now(),
    FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE,
    FOREIGN KEY (meal_id) REFERENCES meal (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX unique_menu_meal_idx ON menu_meal (menu_id, meal_id, created);


INSERT INTO USERS (name, email, password)
VALUES ('Admin', 'admin@gmail.com', '$2a$10$IqTJTjn39IU5.7sSCDQxzu3xug6z/LPU6IF0azE/8CkHCwYEnwBX.'),
       ('Oleg', 'oleg@mail.ru', '$2a$10$Tl7q5gHahZGbb.GEr5Zr9u./qM1U./EQOHr3wkop3c4pG43S55rRq');

INSERT INTO ROLE(name)
VALUES ('ADMIN'),
       ('USER');

INSERT INTO USER_ROLE (USER_ID, ROLE_ID)
VALUES ('100000', '100002');
VALUES ('100001', '100003');

INSERT INTO RESTAURANT (NAME)
VALUES ('Тануки'),
       ('Макдональдс');

INSERT INTO menu (restaurant_id,is_enabled)
VALUES (100004, true),(100005, true);

DELETE FROM MENU;

INSERT INTO MEAL (NAME, RESTARAUNT_ID, PRICE)
VALUES ('Калифорния', '100004', '450'),
       ('Мисо суп', '100004', '250'),
       ('Жареный угорь', '100004', '650'),
       ('Пенне Арабьята', '100005', '634'),
       ('Цезарь с креветками', '100005', '391'),
       ('Пицца Барбекю', '100005', '580');

select id from meal;

select * from menu;