CREATE TABLE IF NOT EXISTS viewer(
    id UUID           PRIMARY KEY,
    name              VARCHAR(20) NOT NULL,
    email             VARCHAR(100) UNIQUE NOT NULL,        -- Email пользователя (уникальный, обязательный)
    city              VARCHAR(50),                         -- Часовой пояс
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Дата регистрации
    is_active         BOOLEAN   DEFAULT true               -- Активность на сайте
);

