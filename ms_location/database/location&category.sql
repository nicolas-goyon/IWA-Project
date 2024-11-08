CREATE TABLE location (
     id SERIAL PRIMARY KEY,
     id_user BIGINT NOT NULL,
     id_category BIGINT NOT NULL,
     title VARCHAR(100) NOT NULL,
     description VARCHAR(500),
     address VARCHAR(255) NOT NULL,
     active BOOLEAN NOT NULL
);

CREATE TABLE category (
      id SERIAL PRIMARY KEY,
      name VARCHAR(100) NOT NULL
);