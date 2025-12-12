CREATE TABLE IF NOT EXISTS photo(
    id UUID                   PRIMARY KEY,
    name		              VARCHAR(50),
    genre_pictures            VARCHAR(50),
    photographer_last_name    VARCHAR(50),
    file_url                  TEXT,
    upload_date               TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);