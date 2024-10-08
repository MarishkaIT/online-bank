CREATE TABLE client (
    id BIGINT PRIMARY KEY ,
    first_name VARCHAR(255) NOT NULL ,
    last_name VARCHAR(255) NOT NULL ,
    email VARCHAR(255) NOT NULL ,
    phone_number VARCHAR(255) NOT NULL
);

CREATE TABLE account (
    id BIGINT PRIMARY KEY ,
    account_number VARCHAR(255) NOT NULL ,
    account_holder_name VARCHAR(255) NOT NULL ,
    balance NUMERIC(10,2) NOT NULL ,
    account_type VARCHAR(255) NOT NULL ,
    currency VARCHAR(3) NOT NULL ,
    client_id BIGINT NOT NULL ,
    FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE card (
    id BIGINT PRIMARY KEY ,
    client_id BIGINT NOT NULL ,
    account_id BIGINT ,
    card_status VARCHAR(255) NOT NULL ,
    card_type VARCHAR(255) NOT NULL ,
    card_number VARCHAR(255) NOT NULL ,
    expiration_date DATE NOT NULL ,
    cvv VARCHAR(255) NOT NULL ,
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE notification (
    id BIGINT PRIMARY KEY ,
    client_id BIGINT NOT NULL ,
    notification_type VARCHAR(255) NOT NULL ,
    message VARCHAR(255) NOT NULL
);

CREATE TABLE transaction (
    id BIGINT PRIMARY KEY ,
    account_id BIGINT NOT NULL ,
    transaction_date DATE NOT NULL ,
    amount DECIMAL(10,2) NOT NULL ,
    description VARCHAR(255) NOT NULL ,
    FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE payment (
    id BIGINT PRIMARY KEY ,
    transaction_id BIGINT NOT NULL ,
    card_id BIGINT,
    amount DECIMAL(10, 2) NOT NULL ,
    currency VARCHAR(3) NOT NULL ,
    payment_status VARCHAR(255) NOT NULL ,
    FOREIGN KEY (transaction_id) REFERENCES transaction(id),
    FOREIGN KEY (card_id) REFERENCES card(id)
);