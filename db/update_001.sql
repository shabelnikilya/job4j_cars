CREATE TABLE brands IF NOT EXISTS (
        id SERIAL PRIMARY KEY,
        name TEXT,
        model TEXT
);
CREATE TABLE body_types IF NOT EXISTS (
    id SERIAL PRIMARY KEY,
    name TEXT
);
CREATE TABLE cars IF NOT EXISTS (
    id SERIAL PRIMARY KEY,
    created TIMESTAMP,
    brand_id INT NOT NULL REFERENCES brands(id),
    body_type_id INT NOT NULL REFERENCES body_types(id),
);
CREATE TABLE accounts IF NOT EXISTS (
    id SERIAL PRIMARY KEY,
    email TEXT,
    login TEXT,
    password TEXT,
    is_author BOOLEAN,
    created TIMESTAMP
);
CREATE TABLE posts IF NOT EXISTS (
    id SERIAL PRIMARY KEY,
    description TEXT,
    cars_id INT NOT NULL UNIQUE REFERENCES cars(id),
    status BOOLEAN,
    accounts_id INT NOT NULL REFERENCES accounts(id),
    created TIMESTAMP
);
CREATE TABLE photos IF NOT EXISTS (
    id SERIAL PRIMARY KEY,
    URL text,
    posts_id INT NOT NULL REFERENCES posts(id)
);