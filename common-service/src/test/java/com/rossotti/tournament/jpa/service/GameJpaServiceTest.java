package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.exception.NoSuchEntityException;
import com.rossotti.tournament.enumeration.GameStatus;
import com.rossotti.tournament.model.Game;
import com.rossotti.tournament.jpa.repository.GameRepositoryTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.rossotti.tournament.config.ServiceConfig.class)
public class GameJpaServiceTest {

	private GameJpaService gameJpaService;

	@Autowired
	public void setGameJpaService(GameJpaService gameJpaService) {
		this.gameJpaService = gameJpaService;
	}

	@Test
	public void getById_Found() {
		Game game = gameJpaService.getById(1L);
		Assert.assertEquals(LocalTime.of(8, 0, 0), game.getStartTime());
		Assert.assertEquals(GameStatus.Completed, game.getGameStatus());
		Assert.assertEquals(LocalDateTime.of(2020, 1, 16, 20, 0), game.getCreateTs());
		Assert.assertEquals(LocalDateTime.of(2020, 1, 19, 20, 0), game.getLupdTs());
		Assert.assertEquals(2, game.getLupdUserId().longValue());
		Assert.assertEquals(Boolean.TRUE, game.getGameTeams().get(0).getHomeTeam());
		Assert.assertEquals("BaseTeam1", game.getGameTeams().get(0).getEventTeam().getOrganizationTeam().getTeamName());
		Assert.assertEquals(2, game.getGameTeams().get(0).getEventTeam().getEventTeamRankings().size());
		Assert.assertEquals(45, game.getGameRound().getGameDuration().shortValue());
		Assert.assertEquals("BaseLocation1", game.getGameRound().getGameLocation().getBaseLocationName());
		Assert.assertEquals("BaseLocation", game.getGameRound().getGameLocation().getOrganizationLocation().getLocationName());
		Assert.assertEquals(LocalDate.of(2020, 9, 29), game.getGameRound().getGameLocation().getGameDate().getGameDate());
		Assert.assertEquals("Campania Regional Frosh Soph Tournament", game.getGameRound().getGameLocation().getGameDate().getEvent().getEventName());
	}

	@Test
	public void getById_NotFound() {
		Assert.assertNull(gameJpaService.getById(41L));
	}

	@Test
	public void listAll() {
		List<Game> games = (List<Game>) gameJpaService.listAll();
		Assert.assertTrue(games.size() >= 4);
	}

	@Test
	public void findByGameStatus_Found() {
		List<Game> games = gameJpaService.findByGameStatus(GameStatus.Forfeited);
		Assert.assertEquals(1, games.size());
	}

	@Test
	public void findByGameStatus_NotFound() {
		List<Game> games = gameJpaService.findByGameStatus(GameStatus.Cancelled);
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByTeamName_Found() {
		List<Game> games = gameJpaService.findByTeamName("Inter Milan");
		Assert.assertEquals(2, games.size());
	}

	@Test
	public void findByTeamName_NotFound() {
		List<Game> games = gameJpaService.findByTeamName("FC Juventes");
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByEventName_Found() {
		List<Game> games = gameJpaService.findByEventName("Campania Regional Frosh Soph Tournament");
		Assert.assertEquals(4, games.size());
	}

	@Test
	public void findByEventName_NotFound() {
		List<Game> games = gameJpaService.findByEventName("Verona Invitational");
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByGameDate_Found() {
		List<Game> games = gameJpaService.findByGameDate(LocalDate.of(2020, 9, 29));
		Assert.assertEquals(2, games.size());
	}

	@Test
	public void findByGameDate_NotFound() {
		List<Game> games = gameJpaService.findByGameDate(LocalDate.of(2016, 2, 21));
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByLocationName_Found() {
		List<Game> games = gameJpaService.findByLocationName("Verona Arena");
		Assert.assertEquals(2, games.size());
	}

	@Test
	public void findByLocationName_NotFound() {
		List<Game> games = gameJpaService.findByLocationName("Pompeii Ampitheater");
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByTeamNameGameDateTime_Found() {
		Game game = gameJpaService.findByTeamNameGameDateTime("Inter Milan", LocalDate.of(2020, 9, 29), LocalTime.of(10, 0, 0));
		Assert.assertEquals(GameStatus.Scheduled, game.getGameStatus());
	}

	@Test
	public void findByTeamNameGameDateTime_NotFound() {
		Assert.assertNull(gameJpaService.findByTeamNameGameDateTime("Inter Circle", LocalDate.of(2020, 9, 29), LocalTime.of(8, 0, 0)));
	}

	@Test
	public void create_Created() {
		gameJpaService.save(GameRepositoryTest.createMockGame(GameStatus.Scheduled, LocalTime.of(11, 0, 0)));
		Game game = gameJpaService.findByTeamNameGameDateTime("Verona", LocalDate.of(2010, 1 , 15), LocalTime.of(11, 0, 0));
		Assert.assertEquals(GameStatus.Scheduled, game.getGameStatus());
		Assert.assertEquals(2, game.getGameTeams().size());
	}

	@Test
	public void create_GameStatusIsMandatory_Null() {
		ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> gameJpaService.save(GameRepositoryTest.createMockGame(null, LocalTime.of(14, 0, 0))));
		Assert.assertTrue(exception.getMessage().contains("GameStatus is mandatory"));
	}

	@Test
	public void create_Duplicate() {
		DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> gameJpaService.save(GameRepositoryTest.createMockGame(GameStatus.Scheduled, LocalTime.of(15, 0, 0))));
		Assert.assertTrue(exception.getMessage().contains("could not execute statement"));
	}

	@Test
	public void update_Updated() {
		Game game = gameJpaService.findByTeamNameGameDateTime("Roma CF", LocalDate.of(2010, 1, 15), LocalTime.of(9, 0, 0));
		Assert.assertEquals(GameStatus.Scheduled, game.getGameStatus());
		game.setGameStatus(GameStatus.Completed);
		gameJpaService.save(game);
		Game findGame = gameJpaService.findByTeamNameGameDateTime("Roma CF", LocalDate.of(2010, 1, 15), LocalTime.of(9, 0, 0));
		Assert.assertEquals(GameStatus.Completed, findGame.getGameStatus());
	}

	@Test
	public void update_GameStatusIsMandatory_Null() {
		Game game = gameJpaService.findByTeamNameGameDateTime("Inter Milan", LocalDate.of(2020, 9, 29), LocalTime.of(10, 0, 0));
		game.setGameStatus(null);
		TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> gameJpaService.save(game));
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("GameStatus is mandatory"));
	}

	@Test
	public void delete_Deleted() {
		gameJpaService.delete(19L);
		Assert.assertNull(gameJpaService.getById(19L));
	}

	@Test
	public void delete_NotFound() {
		NoSuchEntityException exception = assertThrows(NoSuchEntityException.class, () -> gameJpaService.delete(41L));
		Assert.assertTrue(exception.getMessage().contains("Game does not exist"));
		Assert.assertEquals(Game.class, exception.getEntityClass());
	}
}