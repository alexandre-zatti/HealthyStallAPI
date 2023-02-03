CREATE TABLE "person" (
      id serial PRIMARY KEY,
      username VARCHAR ( 20 ) NOT NULL,
      profile_img_path VARCHAR ( 1000 ) NOT NULL,
      balance NUMERIC ( 10, 2 ) NOT NULL DEFAULT 0.00,
      pix_qrcode VARCHAR ( 1000 ),
      created_at TIMESTAMP NOT NULL,
      updated_at TIMESTAMP
);