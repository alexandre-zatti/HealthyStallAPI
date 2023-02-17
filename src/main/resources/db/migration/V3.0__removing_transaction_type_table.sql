drop table "transaction";
drop table "transaction_type";

CREATE TABLE "transaction" (
   id serial PRIMARY KEY,
   person_id int NOT NULL,
   transaction_type VARCHAR( 255 ) NOT NULL,
   product_id int,
   deposit NUMERIC ( 10, 2 ) DEFAULT 0.00,
   created_at TIMESTAMP NOT NULL,
   CONSTRAINT transaction_person_id_fk
       FOREIGN KEY(person_id)
           REFERENCES "person"(id),
   CONSTRAINT transaction_product_id_fk
       FOREIGN KEY(product_id)
           REFERENCES "product"(id)
);