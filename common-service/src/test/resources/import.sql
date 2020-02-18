insert into organization (id, organizationName, address1, address2, city, contactEmail, contactFirstName, contactLastName, contactPhone, country, startDate, endDate, organizationStatus, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(1, 'FC Juventes', 'Via Stupinigi 182', 'Suite 456', 'Vinovo', 'cristiana.girelli@gmail.com', 'Cristiana', 'Girelli', '9093381808', 'Italy', '2016-02-21', '9999-12-31', 'Active', 'Piedmont', '10048', '2015-10-27 20:00:00.0', '2020-01-18 20:00:00.0', 1);
insert into organization (id, organizationName, address1, address2, city, contactEmail, contactFirstName, contactLastName, contactPhone, country, startDate, endDate, organizationStatus, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(2, 'FC Juventes', 'Via Stupinigi 182', 'Suite 442', 'Vinovo', 'barbara.bonansea@gmail.com', 'Barbara', 'Bonansea', '9093381810', 'Italy', '2010-01-15', '2016-02-20', 'Inactive', 'Piedmont', '10048', '2010-10-27 20:00:00.0', '2020-01-18 20:00:00.0', 1);
insert into organization (id, organizationName, address1, address2, city, contactEmail, contactFirstName, contactLastName, contactPhone, country, startDate, endDate, organizationStatus, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(3, 'FC Juventes', 'Via Stupinigi 182', 'Suite 789', 'Vinovo', 'aurora.galli@yahoo.com', 'Aurora', 'Galli', '9093381825', 'Italy', '1901-01-01', '1901-01-01', 'Pending', 'Piedmont', '10048', '2020-01-19 20:00:00.0', '2020-01-20 20:00:00.0', 1);
insert into organization (id, organizationName, address1, address2, city, contactEmail, contactFirstName, contactLastName, contactPhone, country, startDate, endDate, organizationStatus, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(4, 'Fiorentina FC', 'Via Ximenes 74/76', '', 'Firenze', 'tatiana.bonetti@yahoo.com', 'Tatiana', 'Bonetti', '9097047379', 'Italy', '2012-01-15', '9999-12-31', 'Active', 'Tuscany', '50014', '2020-01-19 20:00:00.0', '2020-01-20 20:00:00.0', 1);
insert into organization (id, organizationName, address1, address2, city, contactEmail, contactFirstName, contactLastName, contactPhone, country, startDate, endDate, organizationStatus, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(5, 'US Sassuolo', 'Via Giacomo Matteotti, 80', 'Suite 5', 'Reggio Emilia', 'daniela.sabatino@telecomitalia.com', 'Daniela', 'Sabatino', '9097047728', 'Italy', '2012-01-15', '9999-12-31', 'Active', 'Emilia-Romagna', '42122', '2020-01-19 20:00:00.0', '2020-01-20 20:00:00.0', 1);
insert into organization (id, organizationName, address1, address2, city, contactEmail, contactFirstName, contactLastName, contactPhone, country, startDate, endDate, organizationStatus, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(6, 'AC Milan SPA', 'Centro Sportivo Peppino', 'Arena Road', 'Milano', 'tucceri.cimini@telecomitalia.com', 'Tucceri', 'Cimini', '390248521', 'Italy', '2012-01-15', '9999-12-31', 'Active', 'Lombardy', '20146', '2020-01-19 20:00:00.0', '2020-01-20 20:00:00.0', 1);
insert into organization (id, organizationName, address1, address2, city, contactEmail, contactFirstName, contactLastName, contactPhone, country, startDate, endDate, organizationStatus, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(7, 'UPC Tavagnacco', 'Via Sempione', '1', 'Adegliacco', 'veritti.federica@telecomitalia.com', 'Federica', 'Veritti', '390248521', 'Italy', '2012-01-15', '9999-12-31', 'Active', 'Tavagnacco', '33010', '2020-01-19 20:00:00.0', '2020-01-20 20:00:00.0', 1);

insert into user (id, organizationId, email, firstName, lastName, password, userStatus, userType, createTs, lupdTs, lupdUserId) VALUES(1, 1, 'valentina.giacinti@telecomitalia.com', 'Valentina', 'Giacinti', 'Rosson1', 'Active', 'Administrator', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1);
insert into user (id, organizationId, email, firstName, lastName, password, userStatus, userType, createTs, lupdTs, lupdUserId) VALUES(2, 1, 'laura.fusetti@gmail.com', 'Laura', 'Fusetti', 'Rosse66', 'Inactive', 'Manager', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1);
insert into user (id, organizationId, email, firstName, lastName, password, userStatus, userType, createTs, lupdTs, lupdUserId) VALUES(3, 1, 'valentina.bergamaschi@hotmail.com', 'Valentina', 'Bergamaschi', 'Calcio33', 'Active', 'Manager', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1);
insert into user (id, organizationId, email, firstName, lastName, password, userStatus, userType, createTs, lupdTs, lupdUserId) VALUES(4, 1, 'alessia.piazza@telecomitalia.com', 'Alessia', 'Piazza', '9Brescia', 'Inactive', 'User', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1);
insert into user (id, organizationId, email, firstName, lastName, password, userStatus, userType, createTs, lupdTs, lupdUserId) VALUES(5, 1, 'martina.capelli@telecomitalia.com', 'Martina', 'Capelli', 'Pass123', 'Active', 'Guest', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1);
insert into user (id, organizationId, email, firstName, lastName, password, userStatus, userType, createTs, lupdTs, lupdUserId) VALUES(6, 4, 'valentina.giacinti@telecomitalia.com', 'Valentina', 'Giacinti', 'Rosson2', 'Active', 'Administrator', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 4);
insert into user (id, organizationId, email, firstName, lastName, password, userStatus, userType, createTs, lupdTs, lupdUserId) VALUES(7, 4, 'dicriscio.federica@telecomitalia.com', 'Federica', 'Di Criscio', 'Turtle2', 'Active', 'Manager', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 4);

insert into organizationTeam (id, organizationId, teamName, city, country, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(1, 1, 'Verona', 'Naples', 'Italy', 'Campania', '10052', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);
insert into organizationTeam (id, organizationId, teamName, city, country, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(2, 1, 'Inter Milan', 'Milan', 'Italy', 'Lombardy', '10096', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1);
insert into organizationTeam (id, organizationId, teamName, city, country, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(3, 4, 'Milan', 'Milan', 'Italy', 'Lombardy', '10094', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1);
insert into organizationTeam (id, organizationId, teamName, city, country, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(4, 4, 'Tavarnuzze', 'Impruneta', 'Italy', 'Trentino', '10036', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);

insert into organizationLocation (id, organizationId, locationName, address1, address2, city, country, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(1, 1, 'Verona Arena', '123 Main Street', 'Suite 1', 'Naples', 'Italy', 'Campania', '10052', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);
insert into organizationLocation (id, organizationId, locationName, address1, address2, city, country, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(2, 1, 'San Siro', '456 Central Avenue', 'PO Box 16', 'Milan', 'Italy', 'Lombardy', '10096', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1);
insert into organizationLocation (id, organizationId, locationName, address1, address2, city, country, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(3, 2, 'Giuseppe Meazza Stadium', '890 Park Street', '', 'Milan', 'Italy', 'Lombardy', '10094', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1);
insert into organizationLocation (id, organizationId, locationName, address1, address2, city, country, state, zipCode, createTs, lupdTs, lupdUserId) VALUES(4, 4, 'Stadio Briamasco', '321 Church Street', '2nd Floor', 'Trento', 'Italy', 'Trentino', '10036', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);

insert into template (id, templateType, gridGroupRound1, gridTeamsRound1, groupPlay1, groupPlayoffGamesRound1, gridGroupRound2, gridTeamsRound2, groupPlay2, groupPlayoffGamesRound2, quarterFinalGames, semiFinalGames) VALUES(1, 'four_x_four_pp', 4, 4, 'Pairing', 0, 0, 0, 'None', 0, 8, 8);
insert into template (id, templateType, gridGroupRound1, gridTeamsRound1, groupPlay1, groupPlayoffGamesRound1, gridGroupRound2, gridTeamsRound2, groupPlay2, groupPlayoffGamesRound2, quarterFinalGames, semiFinalGames) VALUES(2, 'four_x_four_rr', 4, 4, 'RoundRobin', 0, 0, 0, 'None', 0, 8, 8);

insert into event (id, organizationId, templateId, startDate, endDate, eventName, eventStatus, eventType, sport, createTs, lupdTs, lupdUserId) VALUES(1, 1, 1, '2020-09-29', '2020-09-30', 'Campania Regional Frosh Soph Tournament', 'Sandbox', 'Tournament', 'WaterPolo', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);
insert into event (id, organizationId, templateId, startDate, endDate, eventName, eventStatus, eventType, sport, createTs, lupdTs, lupdUserId) VALUES(2, 1, 1, '2020-09-24', '2020-09-25', 'Lombardy Halloween Invitational', 'Scheduled', 'Tournament', 'WaterPolo', '2020-01-19 20:00:00.0', '2020-01-16 20:00:00.0', 1);
insert into event (id, organizationId, templateId, startDate, endDate, eventName, eventStatus, eventType, sport, createTs, lupdTs, lupdUserId) VALUES(3, 1, 1, '2020-10-24', '2020-10-25', 'Lombardy Halloween Invitational', 'InProgress', 'Tournament', 'WaterPolo', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1);
insert into event (id, organizationId, templateId, startDate, endDate, eventName, eventStatus, eventType, sport, createTs, lupdTs, lupdUserId) VALUES(4, 5, 1, '2020-08-24', '2020-08-25', 'Trentino Sections Tournament', 'Complete', 'Tournament', 'Lacrosse', '2020-01-16 20:00:00.0', '2019-01-19 20:00:00.0', 2);
insert into event (id, organizationId, templateId, startDate, endDate, eventName, eventStatus, eventType, sport, createTs, lupdTs, lupdUserId) VALUES(5, 5, 2, '2020-08-24', '2020-08-25', 'Trentino Sections Tournament', 'Sandbox', 'Tournament', 'Lacrosse', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);
insert into event (id, organizationId, templateId, startDate, endDate, eventName, eventStatus, eventType, sport, createTs, lupdTs, lupdUserId) VALUES(6, 6, 1, '2020-10-30', '2020-10-31', 'Lombardy Memorial Tournament', 'Sandbox', 'Tournament', 'WaterPolo', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1);
insert into event (id, organizationId, templateId, startDate, endDate, eventName, eventStatus, eventType, sport, createTs, lupdTs, lupdUserId) VALUES(7, 7, 1, '2020-10-30', '2020-10-31', 'Pisa World Cup', 'Sandbox', 'Tournament', 'WaterPolo', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 1);

insert into eventTeam (id, eventId, organizationTeamId) VALUES(1, 1, 2);
insert into eventTeam (id, eventId, organizationTeamId) VALUES(2, 1, 3);

insert into eventTeamRanking (id, eventTeamId, rankingType, ranking) VALUES(1, 1, 'Initial', 1);
insert into eventTeamRanking (id, eventTeamId, rankingType, ranking) VALUES(2, 2, 'Initial', 2);
insert into eventTeamRanking (id, eventTeamId, rankingType, ranking) VALUES(3, 1, 'End', 2);
insert into eventTeamRanking (id, eventTeamId, rankingType, ranking) VALUES(4, 2, 'End', 1);

insert into gameDate (id, eventId, gameDate) VALUES(1, 1, '2020-09-29');
insert into gameDate (id, eventId, gameDate) VALUES(2, 1, '2020-09-30');

insert into gameLocation (id, gameDateId, organizationLocationId, startTime) VALUES(1, 1, 2, '20:00:00.0');
insert into gameLocation (id, gameDateId, organizationLocationId, startTime) VALUES(2, 1, 3, '19:30:00.0');
insert into gameLocation (id, gameDateId, organizationLocationId, startTime) VALUES(3, 2, 2, '08:00:00.0');
insert into gameLocation (id, gameDateId, organizationLocationId, startTime) VALUES(4, 2, 3, '07:30:00.0');

insert into gameRound (id, gameLocationId, gameType, gameDuration) VALUES(1, 1, 'GroupPlay', 45);
insert into gameRound (id, gameLocationId, gameType, gameDuration) VALUES(2, 1, 'Final', 50);
insert into gameRound (id, gameLocationId, gameType, gameDuration) VALUES(3, 2, 'GroupPlay', 45);
insert into gameRound (id, gameLocationId, gameType, gameDuration) VALUES(4, 2, 'Final', 50);

insert into game (id, gameRoundId, startTime, gameStatus, createTs, lupdTs, lupdUserId) VALUES(1, 1, '08:00:00.0', 'Completed', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);
insert into game (id, gameRoundId, startTime, gameStatus, createTs, lupdTs, lupdUserId) VALUES(2, 1, '10:00:00.0', 'Scheduled', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);
insert into game (id, gameRoundId, startTime, gameStatus, createTs, lupdTs, lupdUserId) VALUES(3, 1, '12:00:00.0', 'Scheduled', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);
insert into game (id, gameRoundId, startTime, gameStatus, createTs, lupdTs, lupdUserId) VALUES(4, 1, '16:00:00.0', 'Scheduled', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);
insert into game (id, gameRoundId, startTime, gameStatus, createTs, lupdTs, lupdUserId) VALUES(5, 1, '07:00:00.0', 'Forfeited', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);
insert into game (id, gameRoundId, startTime, gameStatus, createTs, lupdTs, lupdUserId) VALUES(6, 2, '16:00:00.0', 'Scheduled', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);
insert into game (id, gameRoundId, startTime, gameStatus, createTs, lupdTs, lupdUserId) VALUES(7, 2, '07:00:00.0', 'Scheduled', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);
insert into game (id, gameRoundId, startTime, gameStatus, createTs, lupdTs, lupdUserId) VALUES(8, 3, '07:00:00.0', 'Scheduled', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);
insert into game (id, gameRoundId, startTime, gameStatus, createTs, lupdTs, lupdUserId) VALUES(9, 4, '07:00:00.0', 'Scheduled', '2020-01-16 20:00:00.0', '2020-01-19 20:00:00.0', 2);

insert into gameTeam (id, gameId, eventTeamId, homeTeam, pointsScored) VALUES(1, 1, 1, TRUE, 14);
insert into gameTeam (id, gameId, eventTeamId, homeTeam, pointsScored) VALUES(2, 1, 2, FALSE, 5);
insert into gameTeam (id, gameId, eventTeamId, homeTeam, pointsScored) VALUES(3, 2, 1, TRUE, 14);
insert into gameTeam (id, gameId, eventTeamId, homeTeam, pointsScored) VALUES(4, 3, 2, TRUE, 14);
insert into gameTeam (id, gameId, eventTeamId, homeTeam, pointsScored) VALUES(5, 4, 2, TRUE, 14);
insert into gameTeam (id, gameId, eventTeamId, homeTeam, pointsScored) VALUES(6, 6, 1, TRUE, 14);
insert into gameTeam (id, gameId, eventTeamId, homeTeam, pointsScored) VALUES(7, 7, 2, FALSE, 14);
insert into gameTeam (id, gameId, eventTeamId, homeTeam, pointsScored) VALUES(8, 8, 1, TRUE, 14);
insert into gameTeam (id, gameId, eventTeamId, homeTeam, pointsScored) VALUES(9, 9, 2, FALSE, 14);
