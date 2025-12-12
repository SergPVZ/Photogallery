CREATE TABLE IF NOT EXISTS photographer(
    id UUID                  PRIMARY KEY,                         -- ID фотографа (первичный ключ)
    photographer_first_name  VARCHAR(50)         NOT NULL,        -- Имя фотографа (обязательное поле)
    photographer_last_name   VARCHAR(50)         NOT NULL,        -- Фамилия фотографа (обязательное поле)
    email                    VARCHAR(100) UNIQUE NOT NULL,        -- Email фотографа (уникальный, обязательный)
    phone                    VARCHAR(20),                         -- Телефон фотографа
    address                  TEXT,                                -- Адрес фотографа
    city                     VARCHAR(50),                         -- Часовой пояс фотографа
    registration_date        TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Дата регистрации
    is_active                BOOLEAN   DEFAULT true               -- Активность фотографа на сайте
);
