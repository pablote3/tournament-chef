package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.jpa.enumeration.*;
import com.rossotti.tournament.jpa.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
		Assert.assertEquals(10, games.size());
	}

	@Test
	public void findByGameStatus_NotFound() {
		List<Game> games = gameRepository.findByGameStatus(GameStatus.Cancelled);
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByTeamName_Found() {
		List<Game> games = gameRepository.findByTeamName("Inter Milan");
		Assert.assertEquals(1, games.size());
	}

	@Test
	public void findByTeamName_NotFound() {
		List<Game> games = gameRepository.findByTeamName("FC Juventes");
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByEventName_Found() {
		List<Game> games = gameRepository.findByEventName("Campania Regional Frosh Soph Tournament");
		Assert.assertEquals(1, games.size());
	}

	@Test
	public void findByEventName_NotFound() {
		List<Game> games = gameRepository.findByEventName("Verona Invitational");
		Assert.assertEquals(0, games.size());
	}

	@Test
	public void findByGameDate_Found() {
		List<Game> games = gameRepository.findByGameDate(LocalDate.of(2020, 9, 29));
		Assert.assertEquals(1, games.size());
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
		Game game = gameRepository.findByTeamNameGameDateTime("Inter Milan", LocalDate.of(2020, 9, 29), LocalTime.of(8, 00, 00, 0));
		Assert.assertEquals(GameStatus.Completed, game.getGameStatus());
	}

	@Test
	public void findByTeamNameGameDateTime_NotFound() {
		Assert.assertNull(gameRepository.findByTeamNameGameDateTime("Inter Circle", LocalDate.of(2020, 9, 29), LocalTime.of(8, 00, 00, 0)));
	}
}