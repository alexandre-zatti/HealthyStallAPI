CREATE TABLE "feirante" (
    id serial PRIMARY KEY,
    person_id int NOT NULL,
    active bool NOT NULL,
    assign_at TIMESTAMP NOT NULL,
    revoked_at TIMESTAMP,
    CONSTRAINT feirante_person_id_fk
      FOREIGN KEY(person_id)
          REFERENCES "person"(id)
);

CREATE TABLE "product" (
    id serial PRIMARY KEY,
    title VARCHAR ( 30 ) NOT NULL,
    description VARCHAR ( 255 ),
    thumbnail_path VARCHAR ( 1000 ),
    price NUMERIC ( 10, 2 ) NOT NULL DEFAULT 0.00,
    inventory int NOT NULL DEFAULT 0,
    active bool NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE "transaction_type" (
    id serial PRIMARY KEY,
    title VARCHAR ( 30 ) NOT NULL,
    description VARCHAR ( 255 ),
    active bool NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE "transaction" (
    id serial PRIMARY KEY,
    person_id int NOT NULL,
    transaction_type_id int NOT NULL,
    product_id int,
    feirante_id int,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT transaction_person_id_fk
        FOREIGN KEY(person_id)
            REFERENCES "person"(id),
    CONSTRAINT transaction_product_id_fk
        FOREIGN KEY(product_id)
            REFERENCES "product"(id),
    CONSTRAINT transaction_feirante_id_fk
        FOREIGN KEY(feirante_id)
            REFERENCES "feirante"(id),
    CONSTRAINT transaction_type_id_fk
        FOREIGN KEY(transaction_type_id)
            REFERENCES "transaction_type"(id)

);