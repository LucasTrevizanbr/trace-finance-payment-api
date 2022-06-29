CREATE TABLE diary_payment (
    id bigint PRIMARY KEY NOT NULL auto_increment,
    amount DECIMAL(6,2),
    period VARCHAR(10),
    payment_date_time DATETIME,
    wallet_id BINARY(16),
    CONSTRAINT FOREIGN KEY fk_wallet_id (wallet_id) REFERENCES wallet (id)
) engine = InnoDB default charset = utf8;