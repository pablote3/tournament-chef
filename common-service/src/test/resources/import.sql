insert into organization VALUES(1, '2015-10-27 20:00:00.0', '2020-01-18 20:00:00.0', 1, 'Via Stupinigi 182', 'Suite 456', 'Vinovo', 'cristiana.girelli@gmail.com', 'Cristiana', 'Girelli', '9093381808', 'Italy', '9999-12-31', 'FC Juventes', 'Active', '2016-02-21', 'Piedmont', '10048');
insert into organization VALUES(2, '2010-10-27 20:00:00.0', '2020-01-18 20:00:00.0', 1, 'Via Stupinigi 182', 'Suite 442', 'Vinovo', 'barbara.bonansea@gmail.com', 'Barbara', 'Bonansea', '9093381810', 'Italy', '2016-02-20', 'FC Juventes', 'Inactive', '2010-01-15', 'Piedmont', '10048');
insert into organization VALUES(3, '2020-01-19 20:00:00.0', '2020-01-20 20:00:00.0', 1, 'Via Stupinigi 182', 'Suite 789', 'Vinovo', 'aurora.galli@yahoo.com', 'Aurora', 'Galli', '9093381825', 'Italy', '1901-01-01', 'FC Juventes', 'Pending', '1901-01-01', 'Piedmont', '10048');
insert into organization VALUES(4, '2020-01-19 20:00:00.0', '2020-01-20 20:00:00.0', 1, 'Via Ximenes 74/76', '', 'Firenze', 'tatiana.bonetti@yahoo.com', 'Tatiana', 'Bonetti', '9097047379', 'Italy', '9999-12-31', 'Fiorentina FC', 'Active', '2012-01-15', 'Tuscany', '50014');
insert into organization VALUES(5, '2020-01-19 20:00:00.0', '2020-01-20 20:00:00.0', 1, 'Via Giacomo Matteotti, 80', 'Suite 5', 'Reggio Emilia', 'daniela.sabatino@telecomitalia.com', 'Daniela', 'Sabatino', '9097047728', 'Italy', '9999-12-31', 'US Sassuolo', 'Active', '2012-01-15', 'Emilia-Romagna', '42122');

insert into user VALUES(1, '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1, 'valentina.giacinti@telecomitalia.com', 'Valentina', 'Giacinti', 'Rossonere1', 'Active', 'Administrator', 1);
insert into user VALUES(2, '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1, 'laura.fusetti@gmail.com', 'Laura', 'Fusetti', 'Vismara123', 'Inactive', 'Manager', 1);
insert into user VALUES(3, '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1, 'valentina.bergamaschi@hotmail.com', 'Valentina', 'Bergamaschi', 'Calcio333', 'Active', 'Manager', 1);
insert into user VALUES(4, '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1, 'alessia.piazza@telecomitalia.com', 'Alessia', 'Piazza', 'Bresciaxxx', 'Inactive', 'User', 1);
insert into user VALUES(5, '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1, 'martina.capelli@telecomitalia.com', 'Martina', 'Capelli', 'password123', 'Active', 'Guest', 1);
insert into user VALUES(6, '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 4, 'valentina.giacinti@telecomitalia.com', 'Valentina', 'Giacinti', 'Rossonere2', 'Active', 'Administrator', 4);

insert into organizationTeam VALUES(1, '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2, 'Naples', 'Italy', 'Campania', 'Verona', '10052', 1);
insert into organizationTeam VALUES(2, '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1, 'Milan', 'Italy', 'Lombardy', 'Inter Milan', '10096', 1);
insert into organizationTeam VALUES(3, '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1, 'Milan', 'Italy', 'Lombardy', 'Milan', '10094', 4);
insert into organizationTeam VALUES(4, '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2, 'Trento', 'Italy', 'Trentino', 'Juventes', '10036', 4);