-- changeset Alivera:1
CREATE TABLE client (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL ,
    last_name VARCHAR(255) NOT NULL ,
    email VARCHAR(255) NOT NULL ,
    phone_number VARCHAR(255) NOT NULL
);

-- changeset Alivera:2
CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY ,
    account_number VARCHAR(255) NOT NULL ,
    account_holder_name VARCHAR(255) NOT NULL ,
    balance DECIMAL(10, 2) NOT NULL ,
    account_type VARCHAR(255) NOT NULL ,
    currency VARCHAR(255) NOT NULL ,
    client_id BIGINT NOT NULL ,
    FOREIGN KEY (client_id) REFERENCES client(id)
);

-- changeset Alivera:3
CREATE TABLE card (
                      id BIGSERIAL PRIMARY KEY ,
                      client_id BIGINT NOT NULL ,
                      account_id BIGINT NOT NULL ,
                      card_status VARCHAR(255) NOT NULL ,
                      card_type VARCHAR(255) NOT NULL ,
                      card_number VARCHAR(255) NOT NULL ,
                      expiration_date DATE NOT NULL ,
                      cvv VARCHAR(255) NOT NULL,
                      FOREIGN KEY (account_id) REFERENCES account(id),
                      FOREIGN KEY (client_id) REFERENCES client(id)
);

--changeset Alivera:4
CREATE TABLE transaction (
                             id BIGSERIAL PRIMARY KEY ,
                             account_id BIGINT NOT NULL ,
                             transaction_date DATE NOT NULL ,
                             amount DECIMAL(10, 2) NOT NULL ,
                             description VARCHAR(255),
                             FOREIGN KEY (account_id) REFERENCES account(id)
);

-- changeset Alivera:5
CREATE TABLE payment (
                         id BIGSERIAL PRIMARY KEY ,
                         transaction_id BIGINT NOT NULL ,
                         card_id BIGINT NOT NULL ,
                         amount DECIMAL(10, 2) ,
                         currency VARCHAR(3),
                         payment_status VARCHAR(255),
                        FOREIGN KEY (transaction_id) REFERENCES transaction(id),
                        FOREIGN KEY (card_id) REFERENCES card(id )
);

-- changeset Alivera: 6
CREATE TABLE notification (
    id BIGSERIAL PRIMARY KEY ,
    client_id BIGINT NOT NULL ,
    notification_type VARCHAR(50) NOT NULL ,
    message VARCHAR(255) NOT NULL ,
    FOREIGN KEY (client_id) REFERENCES client(id)
);