create database if not exists DD_DB;

use DD_DB;

drop table if exists vehicle_types;
drop table if exists players;
drop table if exists vehicles;


create table vehicle_types(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	type_name varchar(16),
	description varchar(300),
	power INT,
	weight INT,
	armor INT,
	control INT,
	speed INT,
	acceleration INT
	
);

create table vehicles(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	model_name varchar(16),
	vehicle_type INT,

	FOREIGN KEY (vehicle_type) REFERENCES vehicles(id)
);

create table players(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(16),
	user_password VARCHAR(16),
	isConnected BOOLEAN,
	vehicle INT,


	FOREIGN KEY (vehicle) REFERENCES vehicles(id) 
);

