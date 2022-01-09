CREATE TABLE loans (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_date TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP,
    loan_amount DOUBLE NOT NULL,
    installments INTEGER DEFAULT 0,
    first_installment_date VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    customer_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;