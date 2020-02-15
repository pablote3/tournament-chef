package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.jpa.enumeration.*;
import com.rossotti.tournament.jpa.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles(profiles = "development")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceConfig.class)
public class GameRepositoryTest {

	@Autowired
	private GameRepository gameRepository;

	@Test
	public void findById_Found() {
		Game game = gameRepository.findById(1L);
		Assert.assertEquals(LocalTime.of(8, 0, 0), game.getStartTime());
		Assert.assertEquals(GameStatus.Completed, game.getGameStatus());
		Assert.assertEquals(LocalDateTime.of(2020, 1, 16, 20, 0), game.getCreateTs());
		Assert.assertEquals(LocalDateTime.of(2020, 1, 19, 20, 0), game.getLupdTs());
		Assert.assertEquals(2, game.getLupdUserId().longValue());
		Assert.assertEquals(Boolean.TRUE, game.getGameTeams().get(0).getHomeTeam());
		Assert.assertEquals("Inter Milan", game.getGameTeams().get(0).getEventTeam().getOrganizationTeam().getTeamName());
		Assert.assertEquals(1, game.getGameTeams().get(0).getEventTeam().getEventTeamRankings().get(0).getRanking().shortValue());
		Assert.assertEquals(45, game.getGameRound().getGameDuration().shortValue());
		Assert.assertEquals("San Siro", game.getGameRound().getGameLocation().getOrganizationLocation().getLocationName());
		Assert.assertEquals(LocalDate.of(2020, 9, 29), game.getGameRound().getGameLocation().getGameDate().getGameDate());
		Assert.assertEquals("Campania Regional Frosh Soph Tournament", game.getGameRound().getGameLocation().getGameDate().getEvent().getEventName());
	}

	@Test
	public void findById_NotFound() {
		Assert.assertNull(gameRepository.findById(21L));
	}

	@Test
	public void findAll() {
		List<Game> games = gameRepository.findAll();
		Assert.assertTrue(games.size() >= 4);
	}

	@Test
	public void findByGameStatus_Found() {
		List<Game> games = gameRepository.findByGameStatus(GameStatus.Scheduled);
		Assert.assertEquals(11, games.size());
	}

	@Test
	public void findByGameStatus_NotFound() {
		List<Game> games = gameRepository.findByGameStatus(GameStatus.Cancelled);
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByTeamName_Found() {
		List<Game> games = gameRepository.findByTeamName("Inter Milan");
		Assert.assertEquals(3, games.size());
	}

	@Test
	public void findByTeamName_NotFound() {
		List<Game> games = gameRepository.findByTeamName("FC Juventes");
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByEventName_Found() {
		List<Game> games = gameRepository.findByEventName("Campania Regional Frosh Soph Tournament");
		Assert.assertEquals(3, games.size());
	}

	@Test
	public void findByEventName_NotFound() {
		List<Game> games = gameRepository.findByEventName("Verona Invitational");
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByGameDate_Found() {
		List<Game> games = gameRepository.findByGameDate(LocalDate.of(2020, 9, 29));
		Assert.assertEquals(3, games.size());
	}

	@Test
	public void findByGameDate_NotFound() {
		List<Game> games = gameRepository.findByGameDate(LocalDate.of(2016, 2, 21));
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByLocationName_Found() {
		List<Game> games = gameRepository.findByLocationName("Verona Arena");
		Assert.assertEquals(4, games.size());
	}

	@Test
	public void findByLocationName_NotFound() {
		List<Game> games = gameRepository.findByLocationName("Pompeii Ampitheater");
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByTeamNameGameDateTime_Found() {
		Game game = gameRepository.findByTeamNameGameDateTime("Inter Milan", LocalDate.of(2020, 9, 29), LocalTime.of(8, 0, 0, 0));
		Assert.assertEquals(GameStatus.Completed, game.getGameStatus());
	}

	@Test
	public void findByTeamNameGameDateTime_NotFound() {
		Assert.assertNull(gameRepository.findByTeamNameGameDateTime("Inter Circle", LocalDate.of(2020, 9, 29), LocalTime.of(8, 0, 0, 0)));
	}

	@Test
	public void create() {
		gameRepository.save(createMockGame(GameStatus.Scheduled, LocalTime.of(9, 0, 0)));
		gameRepository.findAll();
		Game game = gameRepository.findByTeamNameGameDateTime("Inter Milan", LocalDate.of(2020, 9 , 29), LocalTime.of(9, 0, 0));
		Assert.assertEquals(GameStatus.Scheduled, game.getGameStatus());
	}

	@Test
	public void create_ConstraintViolation_GameStatusMissing() {
		Exception exception = assertThrows(ConstraintViolationException.class, () -> {
			gameRepository.save(createMockGame(null, LocalTime.of(9, 0, 0)));
		});
		Assert.assertTrue(exception.getMessage().contains("GameStatus is mandatory"));
	}

	@Test
	public void create_Duplicate() {
		Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
			gameRepository.save(createMockGame(GameStatus.Scheduled, LocalTime.of(8, 0, 0)));
		});
		Assert.assertTrue(exception.getMessage().contains("could not execute statement"));
	}

	@Test
	public void update() {
		gameRepository.save(updateMockGame(LocalTime.of(10, 0, 0), GameStatus.Forfeited));
		Game game = gameRepository.findByTeamNameGameDateTime("Inter Milan", LocalDate.of(2020, 9, 29), LocalTime.of(10, 0, 0));
		Assert.assertEquals(GameStatus.Forfeited, game.getGameStatus());
	}

	@Test
	public void update_GameStatusMissing() {
		Exception exception = assertThrows(TransactionSystemException.class, () -> {
			gameRepository.save(updateMockGame(LocalTime.of(10, 0, 0), null));
		});
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("GameStatus is mandatory"));
	}

	@Test
	public void delete() {
		gameRepository.findAll();
		Game game = gameRepository.findByTeamNameGameDateTime("Milan", LocalDate.of(2020, 9, 29), LocalTime.of(12, 0, 0));
		if (game != null) {
			gameRepository.deleteById(game.getId());
		}
		else {
			Assert.fail("Unable to find record to delete");
		}
		Assert.assertNull(gameRepository.findByTeamNameGameDateTime("Milan", LocalDate.of(2020, 9, 29), LocalTime.of(12, 0, 0)));
	}

	private static Game createMockGame(GameStatus gameStatus, LocalTime gameTime) {
		Game game = new Game();
		game.setStartTime(gameTime);
		game.setGameStatus(gameStatus);
		game.setGameRound(createMockGameRound(1L, game));
		game.getGameTeams().add(EventRepositoryTest.createMockGameTeam(game));
		game.setCreateTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		game.setLupdTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		game.setLupdUserId(4L);
		return game;
	}

	private static GameRound createMockGameRound(Long gameRoundId, Game game) {
		GameRound gameRound = new GameRound();
		gameRound.setId(gameRoundId);
		List<Game> games = new ArrayList<>();
		games.add(game);
		gameRound.setGames(games);
		gameRound.setGameLocation(createMockGameLocation(1L, gameRound));
		return gameRound;
	}

	private static GameLocation createMockGameLocation(Long gameLocationId, GameRound gameRound) {
		GameLocation gameLocation = new GameLocation();
		gameLocation.setId(gameLocationId);
		gameLocation.setOrganizationLocation(createMockOrganizationLocation(1L));
		gameLocation.setGameDate(createMockGameDate(1L, gameLocation));
		List<GameRound> gameRounds = new ArrayList<>();
		gameRounds.add(gameRound);
		gameLocation.setGameRounds(gameRounds);
		return gameLocation;
	}

	private static GameDate createMockGameDate(Long gameDateId, GameLocation gameLocation) {
		GameDate gameDate = new GameDate();
		gameDate.setId(gameDateId);
		List<GameLocation> gameLocations = new ArrayList<>();
		gameLocations.add(gameLocation);
		gameDate.setGameLocations(gameLocations);
		gameDate.setEvent(createMockEvent(1L, gameDate));
		return gameDate;
	}

	private static Event createMockEvent(Long eventId, GameDate gameDate) {
		Event event = new Event();
		event.setId(eventId);
		List<GameDate> gameDates = new ArrayList<>();
		gameDates.add(gameDate);
		event.setGameDates(gameDates);
		List<EventTeam> eventTeams = new ArrayList<>();
		eventTeams.add(createMockEventTeam(1L, event));
		event.setEventTeams(eventTeams);
		return event;
	}

	private static EventTeam createMockEventTeam(Long eventTeamId, Event event) {
		EventTeam eventTeam = new EventTeam();
		eventTeam.setId(eventTeamId);
		eventTeam.setEvent(event);
		eventTeam.setOrganizationTeam(createMockOrganizationTeam(1L, eventTeam));
		return eventTeam;
	}

	private static OrganizationTeam createMockOrganizationTeam(Long organizationTeamId, EventTeam eventTeam) {
		OrganizationTeam organizationTeam = new OrganizationTeam();
		organizationTeam.setId(organizationTeamId);
		organizationTeam.setTeamName("Inter Milan");
		List<EventTeam> eventTeams = new ArrayList<>();
		eventTeams.add(eventTeam);
		organizationTeam.setEventTeams(eventTeams);
		return organizationTeam;
	}

	private static OrganizationLocation createMockOrganizationLocation(Long organizationLocationId) {
		OrganizationLocation organizationLocation = new OrganizationLocation();
		organizationLocation.setId(organizationLocationId);
		return organizationLocation;
	}

	private Game updateMockGame(LocalTime gameTime, GameStatus gameStatus) {
		gameRepository.findAll();
		Game game = gameRepository.findByTeamNameGameDateTime("Inter Milan", LocalDate.of(2020, 9, 29), gameTime);
		game.setGameStatus(gameStatus);
		return game;
	}
}