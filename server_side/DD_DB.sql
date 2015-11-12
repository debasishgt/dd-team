create database if not exists DD_DB;

use DD_DB;

drop table if exists players;
drop table if exists vehicles;
drop table if exists vehicle_types;
drop table if exists dd_games;


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
	type_id INT,
	vehicle_power INT,
	vehicle_weight INT,
	vehicle_armor INT,
	vehicle_control INT,
	vehicle_speed INT,
	vehicle_acceleration INT,

	FOREIGN KEY (type_id) REFERENCES vehicle_types(id)
);

create table players(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(16),
	user_password VARCHAR(16),
	isConnected BOOLEAN,
	parts TINYINT,
	vehicle_id INT,
	


	FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) 
);

create table dd_games(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	map_name varchar(16),
	winner_id INT NULL
);

