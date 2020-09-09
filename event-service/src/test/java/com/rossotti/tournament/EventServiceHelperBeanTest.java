package com.rossotti.tournament;

import com.rossotti.tournament.enumeration.EventStatus;
import com.rossotti.tournament.service.EventServiceHelperBean;
import com.rossotti.tournament.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceHelperBeanTest {

	@InjectMocks
	private EventServiceHelperBean eventServiceHelperBean;

	@Test
	public void validateDatabaseEvent_valid() {
		Event event = new Event();
		event.setEventStatus(EventStatus.Sandbox);
		boolean i = eventServiceHelperBean.validateDatabaseEvent(event);
		Assert.assertTrue(i);
	}

	@Test
	public void validateDatabaseEvent_invalid() {
		Event event = new Event();
		event.setEventStatus(EventStatus.InProgress);
		Assert.assertFalse(eventServiceHelperBean.validateDatabaseEvent(event));
	}

	@Test
	public void validateRequestEvent_valid() {
		Event event = new Event();
		event.setEventStatus(EventStatus.Scheduled);
		Assert.assertTrue(eventServiceHelperBean.validateDatabaseEvent(event));
	}

	@Test
	public void validateRequestEvent_invalid() {
		Event event = new Event();
		event.setEventStatus(EventStatus.InProgress);
		Assert.assertFalse(eventServiceHelperBean.validateDatabaseEvent(event));
	}

	@Test
	public void validateTeams_valid() {
		List<EventTeam> eventTeams = new ArrayList<>();
		EventTeam eventTeam1 = new EventTeam();
		OrganizationTeam orgTeam1 = new OrganizationTeam();
		orgTeam1.setTeamName("Bari");
		eventTeam1.setOrganizationTeam(orgTeam1);
		eventTeams.add(eventTeam1);
		EventTeam eventTeam2 = new EventTeam();
		OrganizationTeam orgTeam2 = new OrganizationTeam();
		orgTeam2.setTeamName("Tavagnacco");
		eventTeam2.setOrganizationTeam(orgTeam2);
		eventTeams.add(eventTeam2);
		Assert.assertTrue(eventServiceHelperBean.validateTeams(eventTeams));
	}

	@Test
	public void validateTeams_invalid() {
		List<EventTeam> eventTeams = new ArrayList<>();
		EventTeam eventTeam1 = new EventTeam();
		OrganizationTeam orgTeam1 = new OrganizationTeam();
		orgTeam1.setTeamName("Bari");
		eventTeam1.setOrganizationTeam(orgTeam1);
		eventTeams.add(eventTeam1);
		EventTeam eventTeam2 = new EventTeam();
		OrganizationTeam orgTeam2 = new OrganizationTeam();
		orgTeam2.setTeamName("BaseTeam");
		eventTeam2.setOrganizationTeam(orgTeam2);
		eventTeams.add(eventTeam2);
		Assert.assertFalse(eventServiceHelperBean.validateTeams(eventTeams));
	}

	@Test
	public void validateLocations_valid() {
		List<GameDate> gameDates = new ArrayList<>();
		GameDate gameDate = new GameDate();
		List<GameLocation> gameLocations = new ArrayList<>();

		GameLocation gameLocation1 = new GameLocation();
		OrganizationLocation organizationLocation1 = new OrganizationLocation();
		organizationLocation1.setLocationName("Centro Sportivo Monteboro");
		gameLocation1.setOrganizationLocation(organizationLocation1);
		gameLocations.add(gameLocation1);

		GameLocation gameLocation2 = new GameLocation();
		OrganizationLocation organizationLocation2 = new OrganizationLocation();
		organizationLocation2.setLocationName("Stadio Tre Fontane");
		gameLocation2.setOrganizationLocation(organizationLocation2);
		gameLocations.add(gameLocation2);

		gameDate.setGameLocations(gameLocations);
		gameDates.add(gameDate);
		Assert.assertTrue(eventServiceHelperBean.validateLocations(gameDates));
	}

	@Test
	public void validateLocations_invalid() {
		List<GameDate> gameDates = new ArrayList<>();
		GameDate gameDate = new GameDate();
		List<GameLocation> gameLocations = new ArrayList<>();

		GameLocation gameLocation1 = new GameLocation();
		OrganizationLocation organizationLocation1 = new OrganizationLocation();
		organizationLocation1.setLocationName("Centro Sportivo Monteboro");
		gameLocation1.setOrganizationLocation(organizationLocation1);
		gameLocations.add(gameLocation1);

		GameLocation gameLocation2 = new GameLocation();
		OrganizationLocation organizationLocation2 = new OrganizationLocation();
		organizationLocation2.setLocationName("BaseLocation");
		gameLocation2.setOrganizationLocation(organizationLocation2);
		gameLocations.add(gameLocation2);

		gameDate.setGameLocations(gameLocations);
		gameDates.add(gameDate);
		Assert.assertFalse(eventServiceHelperBean.validateLocations(gameDates));
	}

	@Test
	public void validateGames_valid() {
		List<Long> gameIds = new ArrayList<>();
		gameIds.add(5L);
		gameIds.add(3L);
		gameIds.add(1L);
		gameIds.add(4L);
		gameIds.add(2L);
		Assert.assertTrue(eventServiceHelperBean.validateDisplayGameIds(gameIds));
	}

	@Test
	public void validateGames_invalid() {
		List<Long> gameIds = new ArrayList<>();
		gameIds.add(6L);
		gameIds.add(3L);
		gameIds.add(1L);
		gameIds.add(4L);
		gameIds.add(2L);
		Assert.assertFalse(eventServiceHelperBean.validateDisplayGameIds(gameIds));
	}

	@Test
	public void buildDisplayGameIds_valid() throws NullPointerException {
		List<GameDate> gameDates = new ArrayList<>();
		List<GameLocation> gameLocations1 = new ArrayList<>();
		List<GameRound> gameRounds1 = new ArrayList<>();
		List<GameRound> gameRounds2 = new ArrayList<>();
		List<Game> games1 = new ArrayList<>();
		List<Game> games2 = new ArrayList<>();
		List<Game> games3 = new ArrayList<>();
		List<Game> games4 = new ArrayList<>();

		GameRound gameRound1 = new GameRound();
		gameRounds1.add(gameRound1);
		Game game1 = new Game();
		game1.setDisplayGameId(8L);
		games1.add(game1);
		Game game2 = new Game();
		game2.setDisplayGameId(7L);
		games1.add(game2);
		gameRound1.setGames(games1);

		GameRound gameRound2 = new GameRound();
		gameRounds1.add(gameRound2);
		Game game3 = new Game();
		game3.setDisplayGameId(6L);
		games2.add(game3);
		Game game4 = new Game();
		game4.setDisplayGameId(5L);
		games2.add(game4);
		gameRound2.setGames(games2);

		GameRound gameRound3 = new GameRound();
		gameRounds2.add(gameRound3);
		Game game5 = new Game();
		game5.setDisplayGameId(4L);
		games3.add(game5);
		Game game6 = new Game();
		game6.setDisplayGameId(3L);
		games3.add(game6);
		gameRound3.setGames(games3);

		GameRound gameRound4 = new GameRound();
		gameRounds2.add(gameRound4);
		Game game7 = new Game();
		game7.setDisplayGameId(2L);
		games4.add(game7);
		Game game8 = new Game();
		game8.setDisplayGameId(1L);
		games4.add(game8);
		gameRound4.setGames(games4);

		GameLocation gameLocation1 = new GameLocation();
		gameLocation1.setGameRounds(gameRounds1);
		gameLocations1.add(gameLocation1);

		GameLocation gameLocation2 = new GameLocation();
		gameLocation2.setGameRounds(gameRounds2);
		gameLocations1.add(gameLocation2);

		GameDate gameDate = new GameDate();
		gameDate.setGameLocations(gameLocations1);
		gameDates.add(gameDate);
		Assert.assertEquals(8, eventServiceHelperBean.buildDisplayGameIds(gameDates).size());
	}

	@Test
	public void buildDisplayGameIds_invalidNullGameDates() {
		Assert.assertThrows(NullPointerException.class, () -> eventServiceHelperBean.buildDisplayGameIds(null));
	}

	@Test
	public void buildDisplayGameIds_invalidNullGameDate() {
		List<GameDate> gameDates = new ArrayList<>();
		GameDate gameDate = new GameDate();
		gameDates.add(gameDate);
		gameDates.add(null);
		Assert.assertThrows(NullPointerException.class, () -> eventServiceHelperBean.buildDisplayGameIds(gameDates));
	}

	@Test
	public void buildDisplayGameIds_invalidNullGameLocations() {
		List<GameDate> gameDates = new ArrayList<>();
		GameDate gameDate = new GameDate();
		gameDates.add(gameDate);
		gameDate.setGameLocations(null);
		Assert.assertThrows(NullPointerException.class, () -> eventServiceHelperBean.buildDisplayGameIds(gameDates));
	}

//	@Test
//	public void buildDisplayGameIds_nullGameRounds() {
//		List<GameDate> gameDates = new ArrayList<>();
//		List<GameLocation> gameLocations1 = new ArrayList<>();
//		List<GameRound> gameRounds1 = new ArrayList<>();
//		List<GameRound> gameRounds2 = new ArrayList<>();
//
//		GameRound gameRound1 = new GameRound();
//		gameRounds1.add(gameRound1);
//		Game game1 = new Game();
//		game1.setDisplayGameId(8L);
//		games1.add(game1);
//		Game game2 = new Game();
//		game2.setDisplayGameId(7L);
//		games1.add(game2);
//		gameRound1.setGames(games1);
//
//		GameRound gameRound2 = new GameRound();
//		gameRounds1.add(gameRound2);
//		Game game3 = new Game();
//		game3.setDisplayGameId(6L);
//		games2.add(game3);
//		Game game4 = new Game();
//		game4.setDisplayGameId(5L);
//		games2.add(game4);
//		gameRound2.setGames(games2);
//
//		GameRound gameRound3 = new GameRound();
//		gameRounds2.add(gameRound3);
//		Game game5 = new Game();
//		game5.setDisplayGameId(4L);
//		games3.add(game5);
//		Game game6 = new Game();
//		game6.setDisplayGameId(3L);
//		games3.add(game6);
//		gameRound3.setGames(games3);
//
//		GameRound gameRound4 = new GameRound();
//		gameRounds2.add(gameRound4);
//		Game game7 = new Game();
//		game7.setDisplayGameId(2L);
//		games4.add(game7);
//		Game game8 = new Game();
//		game8.setDisplayGameId(1L);
//		games4.add(game8);
//		gameRound4.setGames(games4);
//
//		GameLocation gameLocation1 = new GameLocation();
//		gameLocation1.setGameRounds(gameRounds1);
//		gameLocations1.add(gameLocation1);
//
//		GameLocation gameLocation2 = new GameLocation();
//		gameLocation2.setGameRounds(gameRounds2);
//		gameLocations1.add(gameLocation2);
//
//		GameDate gameDate = new GameDate();
//		gameDate.setGameLocations(gameLocations1);
//		gameDates.add(gameDate);
//		Assert.assertEquals(8, eventServiceHelperBean.buildGames(gameDates).size());
//	}
}