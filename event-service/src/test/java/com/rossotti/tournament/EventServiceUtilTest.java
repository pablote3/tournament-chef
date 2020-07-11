package com.rossotti.tournament;

import com.rossotti.tournament.dto.RoundDTO;
import com.rossotti.tournament.enumeration.GameRoundType;
import com.rossotti.tournament.enumeration.HalfDay;
import com.rossotti.tournament.model.*;
import com.rossotti.tournament.service.EventServiceUtil;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventServiceUtilTest {
	private static final String baseTeamName = "BaseTeam";
	private static final String baseLocationName = "BaseLocation";

	@Test
	public void getOrganizationTeam_notFound_emptyList() {
		List<OrganizationTeam> organizationTeams = new ArrayList<>();
		OrganizationTeam organizationTeam = EventServiceUtil.getOrganizationTeam(organizationTeams, baseTeamName);
		Assert.assertNull(organizationTeam);
	}

	@Test
	public void getOrganizationTeam_notFound_validList() {
		List<OrganizationTeam> organizationTeams = new ArrayList<>();
		organizationTeams.add(getOrganizationTeam("team1"));
		organizationTeams.add(getOrganizationTeam("teambase"));
		organizationTeams.add(getOrganizationTeam("BaseTeam2"));
		organizationTeams.add(getOrganizationTeam("baseTeam"));
		OrganizationTeam organizationTeam = EventServiceUtil.getOrganizationTeam(organizationTeams, baseTeamName);
		Assert.assertNull(organizationTeam);
	}

	@Test
	public void getOrganizationTeam_found() {
		List<OrganizationTeam> organizationTeams = new ArrayList<>();
		organizationTeams.add(getOrganizationTeam("team1"));
		organizationTeams.add(getOrganizationTeam("teambase"));
		organizationTeams.add(getOrganizationTeam("BaseTeam"));
		organizationTeams.add(getOrganizationTeam("baseTeam"));
		OrganizationTeam organizationTeam = EventServiceUtil.getOrganizationTeam(organizationTeams, baseTeamName);
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
		OrganizationLocation organizationLocation = EventServiceUtil.getOrganizationLocation(organizationLocations, baseLocationName);
		Assert.assertNull(organizationLocation);
	}

	@Test
	public void getOrganizationLocation_notFound_validList() {
		List<OrganizationLocation> organizationLocations = new ArrayList<>();
		organizationLocations.add(getOrganizationLocation("location1"));
		organizationLocations.add(getOrganizationLocation("locationbase"));
		organizationLocations.add(getOrganizationLocation("BaseLocation2"));
		organizationLocations.add(getOrganizationLocation("baseLocation"));
		OrganizationLocation organizationLocation = EventServiceUtil.getOrganizationLocation(organizationLocations, baseLocationName);
		Assert.assertNull(organizationLocation);
	}

	@Test
	public void getOrganizationLocation_found() {
		List<OrganizationLocation> organizationLocations = new ArrayList<>();
		organizationLocations.add(getOrganizationLocation("location1"));
		organizationLocations.add(getOrganizationLocation("locationbase"));
		organizationLocations.add(getOrganizationLocation("BaseLocation"));
		organizationLocations.add(getOrganizationLocation("baseLocation"));
		OrganizationLocation organizationLocation = EventServiceUtil.getOrganizationLocation(organizationLocations, baseLocationName);
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
		List<GameRoundType> gameRounds = EventServiceUtil.getGameRounds(roundDTO);
		Assert.assertNotNull(gameRounds);
		Assert.assertEquals(0, gameRounds.size());
	}

	@Test
	public void getGameRounds_found() {
		RoundDTO roundDTO =  new RoundDTO(4, false, true, true, 0, 0);
		List<GameRoundType> gameRounds = EventServiceUtil.getGameRounds(roundDTO);
		Assert.assertNotNull(gameRounds);
		Assert.assertEquals(6, gameRounds.size());
	}

	@Test
	public void determineHalfDay_first() {
		HalfDay halfDay = EventServiceUtil.determineHalfDay(LocalDate.of(2020, 6, 5), 2.0f);
		Assert.assertEquals(HalfDay.First, halfDay);
	}

	@Test
	public void determineHalfDay_last() {
		HalfDay halfDay = EventServiceUtil.determineHalfDay(LocalDate.of(2020, 6, 6), 2.0f);
		Assert.assertEquals(HalfDay.Last, halfDay);
	}

	@Test
	public void determineHalfDay_none() {
		HalfDay halfDay = EventServiceUtil.determineHalfDay(LocalDate.of(2020, 6, 6), 2.5f);
		Assert.assertEquals(HalfDay.None, halfDay);
	}

	@Test
	public void buildGameRounds_fullDay_1of1() {
		RoundDTO roundDTO = new RoundDTO(2, false, true, true, 0, 4);
		List<GameRound> gameRounds = EventServiceUtil.buildGameRounds(
				1,
				1,
				0,
				HalfDay.None,
				roundDTO,
				EventServiceUtil.getGameRounds(roundDTO),
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
		List<GameRound> gameRounds = EventServiceUtil.buildGameRounds(
				1,
				2,
				0,
				HalfDay.None,
				roundDTO,
				EventServiceUtil.getGameRounds(roundDTO),
				new GameLocation()
		);
		Assert.assertEquals(2, gameRounds.size());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(0).getGameType());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(1).getGameType());
	}

	@Test
	public void buildGameRounds_fullDay_2of2() {
		RoundDTO roundDTO = new RoundDTO(2, false, true, true, 0, 2);
		List<GameRound> gameRounds = EventServiceUtil.buildGameRounds(
				2,
				2,
				2,
				HalfDay.None,
				roundDTO,
				EventServiceUtil.getGameRounds(roundDTO),
				new GameLocation()
		);
		Assert.assertEquals(2, gameRounds.size());
		Assert.assertEquals(GameRoundType.SemiFinal, gameRounds.get(0).getGameType());
		Assert.assertEquals(GameRoundType.Championship, gameRounds.get(1).getGameType());
	}

	@Test
	public void buildGameRounds_halfDayFirst_1of1() {
		RoundDTO roundDTO = new RoundDTO(2, false, true, true, 4, 0);
		List<GameRound> gameRounds = EventServiceUtil.buildGameRounds(
				1,
				1,
				0,
				HalfDay.First,
				roundDTO,
				EventServiceUtil.getGameRounds(roundDTO),
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
		List<GameRound> gameRounds = EventServiceUtil.buildGameRounds(
				1,
				2,
				0,
				HalfDay.First,
				roundDTO,
				EventServiceUtil.getGameRounds(roundDTO),
				new GameLocation()
		);
		Assert.assertEquals(2, gameRounds.size());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(0).getGameType());
		Assert.assertEquals(GameRoundType.GroupPlay, gameRounds.get(1).getGameType());
	}

	@Test
	public void buildGameRounds_halfDayFirst_2of2() {
		RoundDTO roundDTO = new RoundDTO(4, false, true, true, 2, 4);
		List<GameRound> gameRounds = EventServiceUtil.buildGameRounds(
				2,
				2,
				2,
				HalfDay.First,
				roundDTO,
				EventServiceUtil.getGameRounds(roundDTO),
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
		List<GameRound> gameRounds = EventServiceUtil.buildGameRounds(
				1,
				2,
				0,
				HalfDay.Last,
				roundDTO,
				EventServiceUtil.getGameRounds(roundDTO),
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
		List<GameRound> gameRounds = EventServiceUtil.buildGameRounds(
				2,
				2,
				4,
				HalfDay.Last,
				roundDTO,
				EventServiceUtil.getGameRounds(roundDTO),
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

		Assert.assertTrue(EventServiceUtil.validateGameLocations(gameDates, 1));
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

		Assert.assertTrue(EventServiceUtil.validateGameLocations(gameDates, 3));
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

		Assert.assertFalse(EventServiceUtil.validateGameLocations(gameDates, 1));
	}

	@Test
	public void validateGameLocations_invalid_no_game_locations() {
		List<GameLocation> gameLocations = new ArrayList<>();

		List<GameDate> gameDates =  new ArrayList<>();
		GameDate gameDate = new GameDate();
		gameDate.setGameLocations(gameLocations);
		gameDates.add(gameDate);

		Assert.assertFalse(EventServiceUtil.validateGameLocations(gameDates, 1));
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
		Assert.assertTrue(EventServiceUtil.validateGameRounds(gameDates, roundDTO));
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
		Assert.assertFalse(EventServiceUtil.validateGameRounds(gameDates, roundDTO));
	}
}