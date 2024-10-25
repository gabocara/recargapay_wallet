DROP TABLE USER_APP IF EXISTS;
CREATE TABLE USER_APP (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            email VARCHAR(100) NOT NULL UNIQUE
);

CREATE INDEX idx_user_name ON USER_APP(name);

DROP TABLE wallet IF EXISTS;

CREATE TABLE wallet (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
                        last_updated TIMESTAMP NOT NULL,
                        version BIGINT NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES USER_APP(id)
);

CREATE INDEX idx_wallet_user_id ON wallet(user_id);

DROP TABLE transaction IF EXISTS;

CREATE TABLE transaction (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             wallet_id BIGINT NOT NULL,
                             amount DECIMAL(19, 2) NOT NULL,
                             timestamp TIMESTAMP NOT NULL,
                             type VARCHAR(20) NOT NULL,
                             FOREIGN KEY (wallet_id) REFERENCES wallet(id)
);

CREATE INDEX idx_transaction_wallet_id ON transaction(wallet_id);
CREATE INDEX idx_transaction_timestamp ON transaction(timestamp);

INSERT INTO USER_APP (name, email) VALUES ('John Doe', 'johndoe@example.com');
INSERT INTO USER_APP (name, email) VALUES ('John Doe1', 'johndoe1@example.com');
