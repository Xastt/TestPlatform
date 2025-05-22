CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       username VARCHAR(100) NOT NULL,
                       password VARCHAR(255),
                       role VARCHAR(50)
);

CREATE INDEX idx_users_username ON users(username);

CREATE TABLE persons (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         user_id UUID NOT NULL,
                         surname VARCHAR(60) NOT NULL,
                         name VARCHAR(60) NOT NULL,
                         patronymic VARCHAR(60) NOT NULL,
                         phonenumber VARCHAR(20) NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         CONSTRAINT fk_persons_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_persons_user_id ON persons(user_id);
CREATE INDEX idx_persons_email ON persons(email);

