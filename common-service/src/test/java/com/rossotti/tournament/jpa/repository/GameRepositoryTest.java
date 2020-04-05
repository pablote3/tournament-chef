package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.enumeration.*;
import com.rossotti.tournament.model.*;
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
		Assert.assertEquals("BaseTeam1", game.getGameTeams().get(0).getEventTeam().getOrganizationTeam().getTeamName());
		Assert.assertEquals(2, game.getGameTeams().get(0).getEventTeam().getEventTeamRankings().size());
		Assert.assertEquals(45, game.getGameRound().getGameDuration().shortValue());
		Assert.assertEquals("BaseLocation1", game.getGameRound().getGameLocation().getOrganizationLocation().getLocationName());
		Assert.assertEquals(LocalDate.of(2020, 9, 29), game.getGameRound().getGameLocation().getGameDate().getGameDate());
		Assert.assertEquals("Campania Regional Frosh Soph Tournament", game.getGameRound().getGameLocation().getGameDate().getEvent().getEventName());
	}

	@Test
	public void findById_NotFound() {
		Assert.assertNull(gameRepository.findById(31L));
	}

	@Test
	public void findAll() {
		List<Game> games = gameRepository.findAll();
		Assert.assertTrue(games.size() >= 4);
	}

	@Test
	public void findByGameStatus_Found() {
		List<Game> games = gameRepository.findByGameStatus(GameStatus.Forfeited);
		Assert.assertEquals(1, games.size());
	}

	@Test
	public void findByGameStatus_NotFound() {
		List<Game> games = gameRepository.findByGameStatus(GameStatus.Cancelled);
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByTeamName_Found() {
		List<Game> games = gameRepository.findByTeamName("Inter Milan");
		Assert.assertEquals(4, games.size());
	}

	@Test
	public void findByTeamName_NotFound() {
		List<Game> games = gameRepository.findByTeamName("Tavarnuzze");
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByEventName_Found() {
		List<Game> games = gameRepository.findByEventName("Campania Regional Frosh Soph Tournament");
		Assert.assertEquals(4, games.size());
	}

	@Test
	public void findByEventName_NotFound() {
		List<Game> games = gameRepository.findByEventName("Verona Invitational");
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByGameDate_Found() {
		List<Game> games = gameRepository.findByGameDate(LocalDate.of(2020, 9, 29));
		Assert.assertEquals(2, games.size());
	}

	@Test
	public void findByGameDate_NotFound() {
		List<Game> games = gameRepository.findByGameDate(LocalDate.of(2016, 2, 21));
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByLocationName_Found() {
		List<Game> games = gameRepository.findByLocationName("Giuseppe Meazza Stadium");
		Assert.assertEquals(2, games.size());
	}

	@Test
	public void findByLocationName_NotFound() {
		List<Game> games = gameRepository.findByLocationName("Pompeii Ampitheater");
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByTeamNameGameDateTime_Found() {
		Game game = gameRepository.findByTeamNameGameDateTime("Inter Milan", LocalDate.of(2020, 9, 29), LocalTime.of(10, 0, 0, 0));
		Assert.assertEquals(GameStatus.Scheduled, game.getGameStatus());
	}

	@Test
	public void findByTeamNameGameDateTime_NotFound() {
		Assert.assertNull(gameRepository.findByTeamNameGameDateTime("Inter Circle", LocalDate.of(2020, 9, 29), LocalTime.of(8, 0, 0, 0)));
	}

	@Test
	public void create() {
		gameRepository.save(createMockGame(GameStatus.Completed, LocalTime.of(10, 0, 0)));
		Game game = gameRepository.findByTeamNameGameDateTime("BaseTeam1", LocalDate.of(2010, 1 , 15), LocalTime.of(10, 0, 0));
		Assert.assertEquals(GameStatus.Completed, game.getGameStatus());
		Assert.assertEquals(2, game.getGameTeams().size());
	}

	@Test
	public void create_ConstraintViolation_GameStatusMissing() {
		Exception exception = assertThrows(ConstraintViolationException.class, () -> gameRepository.save(createMockGame(null, LocalTime.of(9, 0, 0))));
		Assert.assertTrue(exception.getMessage().contains("GameStatus is mandatory"));
	}

	@Test
	public void create_Duplicate() {
		Exception exception = assertThrows(DataIntegrityViolationException.class, () -> gameRepository.save(createMockGame(GameStatus.Scheduled, LocalTime.of(15, 0, 0))));
		Assert.assertTrue(exception.getMessage().contains("could not execute statement"));
	}

	@Test
	public void update() {
		gameRepository.save(updateMockGame("Verona", LocalTime.of(8, 0, 0), GameStatus.Completed));
		Game game = gameRepository.findByTeamNameGameDateTime("Verona", LocalDate.of(2010, 1, 15), LocalTime.of(8, 0, 0));
		Assert.assertEquals(GameStatus.Completed, game.getGameStatus());
	}

	@Test
	public void update_GameStatusMissing() {
		Exception exception = assertThrows(TransactionSystemException.class, () -> gameRepository.save(updateMockGame("Verona", LocalTime.of(8, 0, 0), null)));
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("GameStatus is mandatory"));
	}

	@Test
	public void delete() {
		Game game = gameRepository.findByTeamNameGameDateTime("Roma CF", LocalDate.of(2010, 1, 15), LocalTime.of(14, 0, 0));
		if (game != null) {
			gameRepository.deleteById(game.getId());
		}
		else {
			Assert.fail("Unable to find record to delete");
		}
		Assert.assertNull(gameRepository.findByTeamNameGameDateTime("Roma CF", LocalDate.of(2010, 1, 15), LocalTime.of(14, 0, 0)));
	}

	public static Game createMockGame(GameStatus gameStatus, LocalTime gameTime) {
		Game game = new Game();
		game.setStartTime(gameTime);
		game.setGameStatus(gameStatus);
		game.setGameRound(createMockGameRound(game));
		game.getGameTeams().add(createMockGameTeam(0, game));
		game.getGameTeams().add(createMockGameTeam(1, game));
		game.setCreateTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		game.setLupdTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		game.setLupdUserId(4L);
		return game;
	}

	private static GameRound createMockGameRound(Game game) {
		GameRound gameRound = new GameRound();
		gameRound.setId(9L);
		List<Game> games = new ArrayList<>();
		games.add(game);
		gameRound.setGames(games);
		gameRound.setGameLocation(createMockGameLocation(gameRound));
		return gameRound;
	}

	private static GameLocation createMockGameLocation(GameRound gameRound) {
		GameLocation gameLocation = new GameLocation();
		gameLocation.setId(9L);
		gameLocation.setOrganizationLocation(createMockOrganizationLocation());
		gameLocation.setGameDate(createMockGameDate(gameLocation));
		List<GameRound> gameRounds = new ArrayList<>();
		gameRounds.add(gameRound);
		gameLocation.setGameRounds(gameRounds);
		return gameLocation;
	}

	private static GameDate createMockGameDate(GameLocation gameLocation) {
		GameDate gameDate = new GameDate();
		gameDate.setId(7L);
		List<GameLocation> gameLocations = new ArrayList<>();
		gameLocations.add(gameLocation);
		gameDate.setGameDate(LocalDate.of(2020, 10 , 30));
		gameDate.setGameLocations(gameLocations);
		gameDate.setEvent(createMockEvent(gameDate));
		return gameDate;
	}

	private static Event createMockEvent(GameDate gameDate) {
		Event event = new Event();
		event.setId(11L);
		List<GameDate> gameDates = new ArrayList<>();
		gameDates.add(gameDate);
		event.setGameDates(gameDates);
		List<EventTeam> eventTeams = new ArrayList<>();
		eventTeams.add(createMockEventTeam(10L, 15L, event));
		eventTeams.add(createMockEventTeam(11L, 16L, event));
		event.setEventTeams(eventTeams);
		return event;
	}

	private static EventTeam createMockEventTeam(Long eventId, Long orgTeamId, Event event) {
		EventTeam eventTeam = new EventTeam();
		eventTeam.setId(eventId);
		eventTeam.setEvent(event);
		eventTeam.setOrganizationTeam(createMockOrganizationTeam(orgTeamId, eventTeam));
		return eventTeam;
	}

	private static OrganizationTeam createMockOrganizationTeam(Long orgTeamId, EventTeam eventTeam) {
		OrganizationTeam organizationTeam = new OrganizationTeam();
		organizationTeam.setId(orgTeamId);
		List<EventTeam> eventTeams = new ArrayList<>();
		eventTeams.add(eventTeam);
		organizationTeam.setEventTeams(eventTeams);
		return organizationTeam;
	}

	private static OrganizationLocation createMockOrganizationLocation() {
		OrganizationLocation organizationLocation = new OrganizationLocation();
		organizationLocation.setId(13L);
		return organizationLocation;
	}

	public static GameTeam createMockGameTeam(int index, Game game) {
		GameTeam gameTeam = new GameTeam();
		gameTeam.setGame(game);
		gameTeam.setPointsScored((short)12);
		gameTeam.setHomeTeam(Boolean.TRUE);
		gameTeam.setEventTeam(game.getGameRound().getGameLocation().getGameDate().getEvent().getEventTeams().get(index));
		return gameTeam;
	}

	private Game updateMockGame(String teamName, LocalTime gameTime, GameStatus gameStatus) {
		Game game = gameRepository.findByTeamNameGameDateTime(teamName, LocalDate.of(2010, 1, 15), gameTime);
		game.setGameStatus(gameStatus);
		return game;
	}
}