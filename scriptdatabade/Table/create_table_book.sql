CREATE TYPE Topic AS ENUM ('COMEDY', 'ROMANCE', 'OTHER');

id SERIAL PRIMARY KEY,
book_id VARCHAR(200),
book_name VARCHAR(50),
topic Topic,
page_number INT,
release_date TIMESTAMP,
author_id INT,
CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES Author (Author_id),
is_borrow BOOLEAN;

