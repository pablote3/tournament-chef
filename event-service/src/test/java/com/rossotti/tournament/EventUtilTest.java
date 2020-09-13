package com.rossotti.tournament;

import com.rossotti.tournament.dto.RoundDTO;
import com.rossotti.tournament.enumeration.GameRoundType;
import com.rossotti.tournament.enumeration.HalfDay;
import com.rossotti.tournament.model.*;
import com.rossotti.tournament.util.EventUtil;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventUtilTest {
	private static final String baseTeamName = "BaseTeam";
	private static final String baseLocationName = "BaseLocation";

	@Test
	public void getOrganizationTeam_notFound_emptyList() {
		List<OrganizationTeam> organizationTeams = new ArrayList<>();
		OrganizationTeam organizationTeam = EventUtil.getOrganizationTeam(organizationTeams, baseTeamName);
		Assert.assertNull(organizationTeam);
	}

	@Test
	public void getOrganizationTeam_notFound_validList() {
		List<OrganizationTeam> organizationTeams = new ArrayList<>();
		organizationTeams.add(getOrganizationTeam("team1"));
		organizationTeams.add(getOrganizationTeam("teambase"));
		organizationTeams.add(getOrganizationTeam("BaseTeam2"));
		organizationTeams.add(getOrganizationTeam("baseTeam"));
		OrganizationTeam organizationTeam = EventUtil.getOrganizationTeam(organizationTeams, baseTeamName);
		Assert.assertNull(organizationTeam);
	}

	@Test
	public void getOrganizationTeam_found() {
		List<OrganizationTeam> organizationTeams = new ArrayList<>();
		organizationTeams.add(getOrganizationTeam("team1"));
		organizationTeams.add(getOrganizationTeam("teambase"));
		organizationTeams.add(getOrganizationTeam("BaseTeam"));
		organizationTeams.add(getOrganizationTeam("baseTeam"));
		OrganizationTeam organizationTeam = EventUtil.getOrganizationTeam(organizationTeams, baseTeamName);
		Assert.assertNotNull(organizationTeam);
		Assert.assertEquals(baseTeamName, organizationTeam.getTeamName());
	}

	private OrganizationTeam getOrganizationTeam(String teamName) {
		OrganizationTeam organizationTeam = new OrganizationTeam();
		organizationTeam.setTeamName(teamName);
		return organizationTeam;
	}

	@Test
	public void getOrganizationLocation_notFound_emptyList() {
		List<OrganizationLocation> organizationLocations = new ArrayList<>();
		OrganizationLocation organizationLocation = EventUtil.getOrganizationLocation(organizationLocations, baseLocationName);
		Assert.assertNull(organizationLocation);
	}

	@Test
	public void getOrganizationLocation_notFound_validList() {
		List<OrganizationLocation> organizationLocations = new ArrayList<>();
		organizationLocations.add(getOrganizationLocation("location1"));
		organizationLocations.add(getOrganizationLocation("locationbase"));
		organizationLocations.add(getOrganizationLocation("BaseLocation2"));
		organizationLocations.add(getOrganizationLocation("baseLocation"));
		OrganizationLocation organizationLocation = EventUtil.getOrganizationLocation(organizationLocations, baseLocationName);
		Assert.assertNull(organizationLocation);
	}

	@Test
	public void getOrganizationLocation_found() {
		List<OrganizationLocation> organizationLocations = new ArrayList<>();
		organizationLocations.add(getOrganizationLocation("location1"));
		organizationLocations.add(getOrganizationLocation("locationbase"));
		organizationLocations.add(getOrganizationLocation("BaseLocation"));
		organizationLocations.add(getOrganizationLocation("baseLocation"));
		OrganizationLocation organizationLocation = EventUtil.getOrganizationLocation(organizationLocations, baseLocationName);
		Assert.assertNotNull(organizationLocation);
		Assert.assertEquals(baseLocationName, organizationLocation.getLocationName());
	}

	private OrganizationLocation getOrganizationLocation(String locationName) {
		OrganizationLocation organizationLocation = new OrganizationLocation();
		organizationLocation.setLocationName(locationName);
		return organizationLocation;
	}

	@Test
	public void getGameRounds_none() {
		RoundDTO roundDTO = new RoundDTO(0, false, false, false, 0, 0);
		List<GameRoundType> gameRounds = EventUtil.getGameRounds(roundDTO);
		Assert.assertNotNull(gameRounds);
		Assert.assertEquals(0, gameRounds.size());
	}

	@Test
	public void getGameRounds_found() {
		RoundDTO roundDTO =  new RoundDTO(4, false, true, true, 0, 0);
		List<GameRoundType> gameRounds = EventUtil.getGameRounds(roundDTO);
		Assert.assertNotNull(gameRounds);
		Assert.assertEquals(6, gameRounds.size());
	}

	@Test
	public void determineHalfDay_first() {
		HalfDay halfDay = EventUtil.determineHalfDay(LocalDate.of(2020, 6, 5), 2.0f);
		Assert.assertEquals(HalfDay.First, halfDay);
	}

	@Test
	public void determineHalfDay_last() {
		HalfDay halfDay = EventUtil.determineHalfDay(LocalDate.of(2020, 6, 6), 2.0f);
		Assert.assertEquals(HalfDay.Last, halfDay);
	}

	@Test
	public void determineHalfDay_none() {
		HalfDay halfDay = EventUtil.determineHalfDay(LocalDate.of(2020, 6, 6), 2.5f);
		Assert.assertEquals(HalfDay.None, halfDay);
	}

	@Test
	public void buildGameRounds_fullDay_1of1() {
		RoundDTO roundDTO = new RoundDTO(2, false, true, true, 0, 4);
		List<GameRound> gameRounds = EventUtil.buildGameRounds(
				1,
				1,
				0,
				HalfDay.None,
				roundDTO,
				EventUtil.getGameRounds(roundDTO),
				new GameLocation()
		);
		Assert.assertEquals(4, gameRounds.size());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(0).getGameType());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(1).getGameType());
		Assert.assertEquals(GameRoundType.SemiFinal, gameRounds.get(2).getGameType());
		Assert.assertEquals(GameRoundType.Championship, gameRounds.get(3).getGameType());
	}

	@Test
	public void buildGameRounds_fullDay_1of2() {
		RoundDTO roundDTO = new RoundDTO(2, false, true, true, 0, 2);
		List<GameRound> gameRounds = EventUtil.buildGameRounds(
				1,
				2,
				0,
				HalfDay.None,
				roundDTO,
				EventUtil.getGameRounds(roundDTO),
				new GameLocation()
		);
		Assert.assertEquals(2, gameRounds.size());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(0).getGameType());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(1).getGameType());
	}

	@Test
	public void buildGameRounds_fullDay_2of2() {
		RoundDTO roundDTO = new RoundDTO(2, false, true, true, 0, 2);
		List<GameRound> gameRounds = EventUtil.buildGameRounds(
				2,
				2,
				2,
				HalfDay.None,
				roundDTO,
				EventUtil.getGameRounds(roundDTO),
				new GameLocation()
		);
		Assert.assertEquals(2, gameRounds.size());
		Assert.assertEquals(GameRoundType.SemiFinal, gameRounds.get(0).getGameType());
		Assert.assertEquals(GameRoundType.Championship, gameRounds.get(1).getGameType());
	}

	@Test
	public void buildGameRounds_halfDayFirst_1of1() {
		RoundDTO roundDTO = new RoundDTO(2, false, true, true, 4, 0);
		List<GameRound> gameRounds = EventUtil.buildGameRounds(
				1,
				1,
				0,
				HalfDay.First,
				roundDTO,
				EventUtil.getGameRounds(roundDTO),
				new GameLocation()
		);
		Assert.assertEquals(4, gameRounds.size());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(0).getGameType());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(1).getGameType());
		Assert.assertEquals(GameRoundType.SemiFinal, gameRounds.get(2).getGameType());
		Assert.assertEquals(GameRoundType.Championship, gameRounds.get(3).getGameType());
	}

	@Test
	public void buildGameRounds_halfDayFirst_1of2() {
		RoundDTO roundDTO = new RoundDTO(4, false, true, true, 2, 4);
		List<GameRound> gameRounds = EventUtil.buildGameRounds(
				1,
				2,
				0,
				HalfDay.First,
				roundDTO,
				EventUtil.getGameRounds(roundDTO),
				new GameLocation()
		);
		Assert.assertEquals(2, gameRounds.size());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(0).getGameType());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(1).getGameType());
	}

	@Test
	public void buildGameRounds_halfDayFirst_2of2() {
		RoundDTO roundDTO = new RoundDTO(4, false, true, true, 2, 4);
		List<GameRound> gameRounds = EventUtil.buildGameRounds(
				2,
				2,
				2,
				HalfDay.First,
				roundDTO,
				EventUtil.getGameRounds(roundDTO),
				new GameLocation()
		);
		Assert.assertEquals(4, gameRounds.size());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(0).getGameType());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(1).getGameType());
		Assert.assertEquals(GameRoundType.SemiFinal, gameRounds.get(2).getGameType());
		Assert.assertEquals(GameRoundType.Championship, gameRounds.get(3).getGameType());
	}

	@Test
	public void buildGameRounds_halfDayLast_1of2() {
		RoundDTO roundDTO = new RoundDTO(4, false, true, true, 2, 4);
		List<GameRound> gameRounds = EventUtil.buildGameRounds(
				1,
				2,
				0,
				HalfDay.Last,
				roundDTO,
				EventUtil.getGameRounds(roundDTO),
				new GameLocation()
		);
		Assert.assertEquals(4, gameRounds.size());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(0).getGameType());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(1).getGameType());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(2).getGameType());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(3).getGameType());
	}

	@Test
	public void buildGameRounds_halfDayLast_2of2() {
		RoundDTO roundDTO = new RoundDTO(4, false, true, true, 2, 4);
		List<GameRound> gameRounds = EventUtil.buildGameRounds(
				2,
				2,
				4,
				HalfDay.Last,
				roundDTO,
				EventUtil.getGameRounds(roundDTO),
				new GameLocation()
		);
		Assert.assertEquals(2, gameRounds.size());
		Assert.assertEquals(GameRoundType.SemiFinal, gameRounds.get(0).getGameType());
		Assert.assertEquals(GameRoundType.Championship, gameRounds.get(1).getGameType());
	}

	@Test
	public void validateGameLocations_valid_1_game() {
		List<GameLocation> gameLocations = new ArrayList<>();
		GameLocation gameLocation = new GameLocation();
		gameLocations.add(gameLocation);

		List<GameDate> gameDates =  new ArrayList<>();
		GameDate gameDate = new GameDate();
		gameDate.setGameLocations(gameLocations);
		gameDates.add(gameDate);

		Assert.assertTrue(EventUtil.validateGameLocations(gameDates, 1));
	}

	@Test
	public void validateGameLocations_valid_3_game() {
		List<GameLocation> gameLocations = new ArrayList<>();
		gameLocations.add(new GameLocation());
		gameLocations.add(new GameLocation());
		gameLocations.add(new GameLocation());

		GameDate gameDate = new GameDate();
		gameDate.setGameLocations(gameLocations);
		List<GameDate> gameDates =  new ArrayList<>();
		gameDates.add(gameDate);

		Assert.assertTrue(EventUtil.validateGameLocations(gameDates, 3));
	}

	@Test
	public void validateGameLocations_invalid_extra_game() {
		List<GameLocation> gameLocations = new ArrayList<>();
		gameLocations.add(new GameLocation());
		gameLocations.add(new GameLocation());

		List<GameDate> gameDates =  new ArrayList<>();
		GameDate gameDate = new GameDate();
		gameDate.setGameLocations(gameLocations);
		gameDates.add(gameDate);

		Assert.assertFalse(EventUtil.validateGameLocations(gameDates, 1));
	}

	@Test
	public void validateGameLocations_invalid_no_game_locations() {
		List<GameLocation> gameLocations = new ArrayList<>();

		List<GameDate> gameDates =  new ArrayList<>();
		GameDate gameDate = new GameDate();
		gameDate.setGameLocations(gameLocations);
		gameDates.add(gameDate);

		Assert.assertFalse(EventUtil.validateGameLocations(gameDates, 1));
	}

	@Test
	public void validateGameRounds_valid() {
		List<GameLocation> gameLocations = new ArrayList<>();
		GameLocation gameLocation = new GameLocation();
		gameLocation.getGameRounds().add(new GameRound());
		gameLocation.getGameRounds().add(new GameRound());
		gameLocations.add(gameLocation);
		gameLocations.add(gameLocation);

		List<GameDate> gameDates =  new ArrayList<>();
		GameDate gameDate = new GameDate();
		gameDate.setGameLocations(gameLocations);
		gameDates.add(gameDate);

		RoundDTO roundDTO = new RoundDTO();
		roundDTO.setPreliminary(2);
		roundDTO.setQuarterFinal(false);
		roundDTO.setSemiFinal(true);
		roundDTO.setChampionship(true);
		Assert.assertTrue(EventUtil.validateGameRounds(gameDates, roundDTO));
	}

	@Test
	public void validateGameRounds_invalid() {
		List<GameLocation> gameLocations = new ArrayList<>();
		GameLocation gameLocation = new GameLocation();
		gameLocation.getGameRounds().add(new GameRound());
		gameLocation.getGameRounds().add(new GameRound());
		gameLocations.add(gameLocation);

		List<GameDate> gameDates =  new ArrayList<>();
		GameDate gameDate = new GameDate();
		gameDate.setGameLocations(gameLocations);
		gameDates.add(gameDate);

		RoundDTO roundDTO = new RoundDTO();
		roundDTO.setPreliminary(2);
		roundDTO.setQuarterFinal(false);
		roundDTO.setSemiFinal(true);
		roundDTO.setChampionship(true);
		Assert.assertFalse(EventUtil.validateGameRounds(gameDates, roundDTO));
	}

	@Test
	public void validateGames_valid() {
		List<GameDate> gameDates = buildGameDates(1L, 3L, 5L, 7L, 2L, 4L, 6L, 8L);
		Assert.assertEquals(8, EventUtil.buildDisplayGameIds(gameDates).size());
	}

	@Test (expected=NullPointerException.class)
	public void validateGames_invalidNullValue() {
		List<GameDate> gameDates = buildGameDates(1L, 3L, 5L, 7L, 2L, 4L, null, 8L);
		EventUtil.buildDisplayGameIds(gameDates);
	}

	@Test
	public void validateGames_validDuplicateValue() {
		List<GameDate> gameDates = buildGameDates(1L, 3L, 5L, 7L, 2L, 4L, 4L, 8L);
		Assert.assertEquals(8, EventUtil.buildDisplayGameIds(gameDates).size());
	}

	public static List<GameDate> buildGameDates(Long gameId1, Long gameId2, Long gameId3, Long gameId4,
										 		Long gameId5, Long gameId6, Long gameId7, Long gameId8) {
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
		game1.setDisplayGameId(gameId8);
		games1.add(game1);
		Game game2 = new Game();
		game2.setDisplayGameId(gameId7);
		games1.add(game2);
		gameRound1.setGames(games1);

		GameRound gameRound2 = new GameRound();
		gameRounds1.add(gameRound2);
		Game game3 = new Game();
		game3.setDisplayGameId(gameId6);
		games2.add(game3);
		Game game4 = new Game();
		game4.setDisplayGameId(gameId5);
		games2.add(game4);
		gameRound2.setGames(games2);

		GameRound gameRound3 = new GameRound();
		gameRounds2.add(gameRound3);
		Game game5 = new Game();
		game5.setDisplayGameId(gameId4);
		games3.add(game5);
		Game game6 = new Game();
		game6.setDisplayGameId(gameId3);
		games3.add(game6);
		gameRound3.setGames(games3);

		GameRound gameRound4 = new GameRound();
		gameRounds2.add(gameRound4);
		Game game7 = new Game();
		game7.setDisplayGameId(gameId2);
		games4.add(game7);
		Game game8 = new Game();
		game8.setDisplayGameId(gameId1);
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
		return gameDates;
	}

	@Test
	public void getGameCount_Teams8() {
		List<EventTeam> eventTeams = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			eventTeams.add(new EventTeam());
		}
		Assert.assertEquals(28, EventUtil.getGameCount_RR(eventTeams));
	}

	@Test
	public void getGameCount_Teams16() {
		List<EventTeam> eventTeams = new ArrayList<>();
		for (int i = 0; i < 16; i++) {
			eventTeams.add(new EventTeam());
		}
		Assert.assertEquals(120, EventUtil.getGameCount_RR(eventTeams));
	}
}