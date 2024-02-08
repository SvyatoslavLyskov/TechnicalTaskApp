CREATE TABLE wallet
(
    id      UUID PRIMARY KEY,
    balance NUMERIC NOT NULL CHECK (balance >= 0)
);

CREATE TABLE transaction
(
    id               SERIAL PRIMARY KEY,
    wallet_id        UUID       NOT NULL,
    operation_type   VARCHAR(8) NOT NULL,
    amount           NUMERIC    NOT NULL CHECK (amount > 0),
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (wallet_id) REFERENCES wallet (id)
);

CREATE INDEX idx_transaction_wallet_id ON transaction (wallet_id);