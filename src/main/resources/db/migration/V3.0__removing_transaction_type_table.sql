drop table "transaction";
drop table "transaction_type";

CREATE TABLE "transaction" (
   id serial PRIMARY KEY,
   person_id int NOT NULL,
   transaction_type VARCHAR( 255 ) NOT NULL,
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
           REFERENCES "feirante"(id)
);