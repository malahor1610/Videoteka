CREATE TABLE show (
   id BIGINT NOT NULL,
   title VARCHAR(255),
   original_title VARCHAR(255),
   release_date VARCHAR(255),
   poster VARCHAR(255),
   duration VARCHAR(255),
   type VARCHAR(255),
   position INTEGER NOT NULL,
   CONSTRAINT pk_show PRIMARY KEY (id)
);