SET foreign_key_checks = 0;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Accounts;
DROP TABLE IF EXISTS Market_Account;
DROP TABLE IF EXISTS Stock_Account;
DROP TABLE IF EXISTS Transactions;
DROP TABLE IF EXISTS deposit;
DROP TABLE IF EXISTS withdraw;
DROP TABLE IF EXISTS buy;
DROP TABLE IF EXISTS sell;
DROP TABLE IF EXISTS acc_interest;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS actor;
DROP TABLE IF EXISTS movie_contract;
SET foreign_key_checks = 1;

CREATE TABLE Customer(
    tax_id int(9) UNIQUE, 
	username varchar(255) PRIMARY KEY, 
    password varchar(255),
	name varchar(255), 
	state char(2),
	phonenumber varchar(100),
	email varchar(255)
);

CREATE TABLE Accounts(
    id_num int(100) AUTO_INCREMENT,
    own varchar(255) NOT NULL,
	PRIMARY KEY(id_num, own),
	FOREIGN KEY(own)
		REFERENCES Customer(username)
		ON UPDATE CASCADE ON DELETE CASCADE
	);
)