CREATE TABLE IF NOT EXISTS photo(
    id UUID          PRIMARY KEY,
    name		     VARCHAR(50),
    genre_pictures   VARCHAR(50),
    upload_date      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);