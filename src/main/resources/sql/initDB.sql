DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS users;


DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE public.restaurants
(
    name character varying(40) COLLATE pg_catalog."default" NOT NULL,
    id INTEGER PRIMARY KEY DEFAULT nextval('global_seq')
)

    TABLESPACE pg_default;

ALTER TABLE public.restaurants
    OWNER to postgres;

CREATE TABLE public.meals
(
    name character varying(40) COLLATE pg_catalog."default" NOT NULL,
    id INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    restaraunt_id INTEGER NOT NULL,
    meal_type TEXT DEFAULT 'Not In Menu',
    price INTEGER NOT NULL,
    FOREIGN KEY (restaraunt_id) REFERENCES restaurants (id) ON DELETE CASCADE
)

CREATE TABLE public.orders
(
    id INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    restaraunt_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaraunt_id) REFERENCES restaurants (id) ON DELETE CASCADE
)


CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE roles (
                       id INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
                       name VARCHAR  NOT NULL
);

CREATE TABLE users_roles (
                             user_id INTEGER NOT NULL,
                             role_id INTEGER NOT NULL,
                             CONSTRAINT user_roles_idx UNIQUE (user_id, role_id),
                             FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                             FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX meals_unique_user_datetime_idx ON meals (user_id, date_time);

CREATE TABLE menus(
     id  INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
     restaurant_id INTEGER NOT NULL,
     created TIMESTAMP DEFAULT now() NOT NULL,
     FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE menus_meal(
    id  INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    meal_id INTEGER NOT NULL,
    menus_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    FOREIGN KEY (meal_id) REFERENCES meals (id) ON DELETE CASCADE,
    FOREIGN KEY (menus_id) REFERENCES menus (id) ON DELETE CASCADE
);



CREATE TABLE restaurant_order (
      id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
      user_id INTEGER NOT NULL,
      restaurant_id INTEGER NOT NULL,
      CONSTRAINT user_restaurant_idx UNIQUE (user_id, restaurant_id, created),
      created TIMESTAMP DEFAULT now(),
      is_enabled BOOL DEFAULT false,
      FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
      FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);