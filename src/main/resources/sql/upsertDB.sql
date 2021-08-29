DELETE FROM USERS;
DELETE FROM ROLE;
DELETE FROM USER_ROLE;
DELETE FROM RESTAURANT;
DELETE FROM MEAL;
DELETE FROM RESTAURANT_ORDER;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO USERS (name, email, password)
VALUES ('Admin', 'admin@gmail.com', '$2a$10$IqTJTjn39IU5.7sSCDQxzu3xug6z/LPU6IF0azE/8CkHCwYEnwBX.'),
       ('Oleg', 'oleg@mail.ru', '$2a$10$Tl7q5gHahZGbb.GEr5Zr9u./qM1U./EQOHr3wkop3c4pG43S55rRq');
SELECT * FROM USERS;
INSERT INTO ROLE(name)
VALUES ('ADMIN'),('USER');

INSERT INTO USER_ROLE (USER_ID, ROLE_ID)
VALUES ('100000', '100002');
VALUES ('100001', '100003');

INSERT INTO RESTAURANT (NAME)
VALUES ('Тануки'), ('Макдональдс');

INSERT INTO MEAL (NAME, RESTARAUNT_ID, PRICE)
VALUES ('Калифорния', '100004', '450'),('Мисо суп', '100004', '250'),
       ('Жареный угорь', '100004', '650'),('Пенне Арабьята', '100005', '634'),
       ('Цезарь с креветками', '100005', '391'),('Пицца Барбекю', '100005', '580');