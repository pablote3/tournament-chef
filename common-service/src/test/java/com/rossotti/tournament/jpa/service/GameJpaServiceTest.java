package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.exception.CustomException;
import com.rossotti.tournament.jpa.enumeration.GameStatus;
import com.rossotti.tournament.jpa.model.Game;
import com.rossotti.tournament.jpa.repository.GameRepositoryTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

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
		Assert.assertEquals("Inter Milan", game.getGameTeams().get(0).getEventTeam().getOrganizationTeam().getTeamName());
		Assert.assertEquals(1, game.getGameTeams().get(0).getEventTeam().getEventTeamRankings().get(0).getRanking().shortValue());
		Assert.assertEquals(45, game.getGameRound().getGameDuration().shortValue());
		Assert.assertEquals("San Siro", game.getGameRound().getGameLocation().getOrganizationLocation().getLocationName());
		Assert.assertEquals(LocalDate.of(2020, 9, 29), game.getGameRound().getGameLocation().getGameDate().getGameDate());
		Assert.assertEquals("Campania Regional Frosh Soph Tournament", game.getGameRound().getGameLocation().getGameDate().getEvent().getEventName());

	}

	@Test
	public void getById_NotFound() {
		Assert.assertNull(gameJpaService.getById(31L));
	}

	@Test
	public void listAll() {
		List<Game> games = (List<Game>) gameJpaService.listAll();
		Assert.assertTrue(games.size() >= 4);
	}

	@Test
	public void findByGameStatus_Found() {
		List<Game> games = gameJpaService.findByGameStatus(GameStatus.Scheduled);
		Assert.assertEquals(9, games.size());
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
		Assert.assertEquals(2, games.size());
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
		Assert.assertEquals(4, games.size());
	}

	@Test
	public void findByLocationName_NotFound() {
		List<Game> games = gameJpaService.findByLocationName("Pompeii Ampitheater");
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByTeamNameGameDateTime_Found() {
		Game game = gameJpaService.findByTeamNameGameDateTime("Inter Milan", LocalDate.of(2020, 9, 29), LocalTime.of(8, 00, 00, 0));
		Assert.assertEquals(GameStatus.Completed, game.getGameStatus());
	}

	@Test
	public void findByTeamNameGameDateTime_NotFound() {
		Assert.assertNull(gameJpaService.findByTeamNameGameDateTime("Inter Circle", LocalDate.of(2020, 9, 29), LocalTime.of(8, 00, 00, 0)));
	}

//	@Test
//	public void create_Created() {
//		gameJpaService.save(GameRepositoryTest.createMockGame(1L, "bonetti.tatiana@hotmail.com", "Bonetti", "Super3"));
//		Game findGame = gameJpaService.findByOrganizationNameAndGameEmail("FC Juventes", "bonetti.tatiana@hotmail.com");
//		Assert.assertEquals("Super3", findGame.getPassword());
//	}

//	@Test
//	public void create_EmailIsMandatory_Empty() {
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			gameJpaService.save(GameRepositoryTest.createMockGame(1L, "", "Bonetti", "Super3"));
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("Email is mandatory", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void create_LastNameIsMandatory_Null() {
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			gameJpaService.save(GameRepositoryTest.createMockGame(1L, "bonetti.tatiana@hotmail.com", null, "Super3"));
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("LastName is mandatory", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void create_EmailInvalidFormat() {
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			gameJpaService.save(GameRepositoryTest.createMockGame(1L, "bonetti.tatiana.hotmail.com", "Bonetti", "Super3"));
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("Email invalid format", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void create_PasswordInvalidFormat() {
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			gameJpaService.save(GameRepositoryTest.createMockGame(1L, "bonetti.tatiana@hotmail.com", "Bonetti", "Sup"));
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("Password invalid format", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void update_Updated() {
//		Game updateGame = gameJpaService.findByOrganizationNameAndGameEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
//		updateGame.setGameStatus(GameStatus.Active);
//		gameJpaService.save(updateGame);
//		Game findGame = gameJpaService.findByOrganizationNameAndGameEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
//		Assert.assertEquals(GameStatus.Active, findGame.getGameStatus());
//	}
//
//	@Test
//	public void update_EmailIsMandatory_Empty() {
//		Game updateGame = gameJpaService.findByOrganizationNameAndGameEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
//		updateGame.setGameStatus(GameStatus.Active);
//		updateGame.setEmail("");
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			gameJpaService.save(updateGame);
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("Email is mandatory", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void update_LastNameIsMandatory_Null() {
//		Game updateGame = gameJpaService.findByOrganizationNameAndGameEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
//		updateGame.setGameStatus(GameStatus.Active);
//		updateGame.setLastName(null);
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			gameJpaService.save(updateGame);
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("LastName is mandatory", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void update_PasswordInvalidFormat() {
//		Game updateGame = gameJpaService.findByOrganizationNameAndGameEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
//		updateGame.setGameStatus(GameStatus.Active);
//		updateGame.setPassword("Sup");
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			gameJpaService.save(updateGame);
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("Password invalid format", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void update_EmailInvalidFormat() {
//		Game updateGame = gameJpaService.findByOrganizationNameAndGameEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
//		updateGame.setEmail("alessia.piazza_telecomitalia.com");
//		updateGame.setGameStatus(GameStatus.Active);
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			gameJpaService.save(updateGame);
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("Email invalid format", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void delete_Deleted() {
//		gameJpaService.delete(7L);
//		Assert.assertNull(gameJpaService.getById(7L));
//	}
//
//	@Test
//	public void delete_NotFound() {
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			gameJpaService.delete(21L);
//		});
//		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
//		Assert.assertEquals("Server error when trying to find record for id of {}", exception.getError().getErrorMessage());
//		Assert.assertEquals("MSG_VAL_0012", exception.getError().getError());
//	}
}