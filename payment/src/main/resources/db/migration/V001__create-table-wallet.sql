CREATE TABLE wallet (
	id  BINARY(16) PRIMARY KEY NOT NULL,
    owner_name VARCHAR(100) NOT NULL,
    limit_value DECIMAL(6,2) NOT NULL
)engine = InnoDB default charset = utf8;