drop table if exists characters, players ;
		 
create table players (
        ID INT primary key,
        username varchar (12) not null,
        password varchar (12) not null,
         ) ;
		 
create table characters (
        ID INT primary key,
        playerID INT not null,
		x_position  decimal (4,4) not null,
		y_position decimal (4,4) not null,
		type INT not null,
		foreign key (playerID) references players (ID) on delete set cascade ) ;
