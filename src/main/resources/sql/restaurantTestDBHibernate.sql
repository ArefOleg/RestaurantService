DROP TABLE menu_meal IF EXISTS ;
DROP TABLE menu IF EXISTS;
DROP TABLE meal IF EXISTS;
DROP TABLE restaurant IF EXISTS;
DROP SEQUENCE IF EXISTS PUBLIC.global_seq;

CREATE SEQUENCE PUBLIC.global_seq AS INTEGER START WITH 100000;

CREATE TABLE PUBLIC.restaurant
(
    name VARCHAR(255) NOT NULL,
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE PUBLIC.GLOBAL_SEQ PRIMARY KEY
);

INSERT INTO PUBLIC.RESTAURANT (NAME)
VALUES ('Mirazur'),
       ('Gaggan');

CREATE TABLE PUBLIC.menu
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    restaurant_id INTEGER                 NOT NULL,
    created       TIMESTAMP DEFAULT now() NOT NULL,
    is_enabled    BOOLEAN   DEFAULT true,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

INSERT INTO PUBLIC.MENU(restaurant_id, is_enabled)
VALUES (100000, true),
       (100001, true);

CREATE TABLE PUBLIC.meal
(
    name          VARCHAR(255) NOT NULL,
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE PUBLIC.GLOBAL_SEQ PRIMARY KEY,
    restaurant_id INTEGER      NOT NULL,
    meal_type     VARCHAR(255) DEFAULT 'Not In Menu',
    price         INTEGER      NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

INSERT INTO PUBLIC.MEAL(NAME, restaurant_id, price)
VALUES ('Grand Velas Tacos', 100000, 25000),
       ('GOLDEN OPULENCE SUNDAE', 100001, 1000);

CREATE TABLE PUBLIC.menu_meal
(
    iid            INTEGER GENERATED BY DEFAULT AS SEQUENCE PUBLIC.GLOBAL_SEQ PRIMARY KEY,
    menu_id INTEGER NOT NULL,
    meal_id INTEGER NOT NULL,
    CONSTRAINT menu_meal_idx UNIQUE (menu_id, meal_id, created),
    created       TIMESTAMP           DEFAULT now(),
    FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE,
    FOREIGN KEY (meal_id) REFERENCES meal (id) ON DELETE CASCADE
);

INSERT INTO PUBLIC.menu_meal
(menu_id, meal_id) VALUES (100002,100004),(100003,100005);