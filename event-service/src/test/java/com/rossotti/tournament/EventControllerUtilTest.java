package com.rossotti.tournament;

import com.rossotti.tournament.controller.EventControllerUtil;
import com.rossotti.tournament.dto.RoundDTO;
import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.GameRoundType;
import com.rossotti.tournament.enumeration.HalfDay;
import com.rossotti.tournament.model.OrganizationLocation;
import com.rossotti.tournament.model.OrganizationTeam;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventControllerUtilTest {

	private static final String baseTeamName = "BaseTeam";
	private static final String baseLocationName = "BaseLocation";

	@Test
	public void getOrganizationTeam_notFound_emptyList() {
		List<OrganizationTeam> organizationTeams = new ArrayList<>();
		OrganizationTeam organizationTeam = EventControllerUtil.getOrganizationTeam(organizationTeams, baseTeamName);
		Assert.assertNull(organizationTeam);
	}

	@Test
	public void getOrganizationTeam_notFound_validList() {
		List<OrganizationTeam> organizationTeams = new ArrayList<>();
		organizationTeams.add(getOrganizationTeam("team1"));
		organizationTeams.add(getOrganizationTeam("teambase"));
		organizationTeams.add(getOrganizationTeam("BaseTeam2"));
		organizationTeams.add(getOrganizationTeam("baseTeam"));
		OrganizationTeam organizationTeam = EventControllerUtil.getOrganizationTeam(organizationTeams, baseTeamName);
		Assert.assertNull(organizationTeam);
	}

	@Test
	public void getOrganizationTeam_found() {
		List<OrganizationTeam> organizationTeams = new ArrayList<>();
		organizationTeams.add(getOrganizationTeam("team1"));
		organizationTeams.add(getOrganizationTeam("teambase"));
		organizationTeams.add(getOrganizationTeam("BaseTeam"));
		organizationTeams.add(getOrganizationTeam("baseTeam"));
		OrganizationTeam organizationTeam = EventControllerUtil.getOrganizationTeam(organizationTeams, baseTeamName);
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
		OrganizationLocation organizationLocation = EventControllerUtil.getOrganizationLocation(organizationLocations, baseLocationName);
		Assert.assertNull(organizationLocation);
	}

	@Test
	public void getOrganizationLocation_notFound_validList() {
		List<OrganizationLocation> organizationLocations = new ArrayList<>();
		organizationLocations.add(getOrganizationLocation("location1"));
		organizationLocations.add(getOrganizationLocation("locationbase"));
		organizationLocations.add(getOrganizationLocation("BaseLocation2"));
		organizationLocations.add(getOrganizationLocation("baseLocation"));
		OrganizationLocation organizationLocation = EventControllerUtil.getOrganizationLocation(organizationLocations, baseLocationName);
		Assert.assertNull(organizationLocation);
	}

	@Test
	public void getOrganizationLocation_found() {
		List<OrganizationLocation> organizationLocations = new ArrayList<>();
		organizationLocations.add(getOrganizationLocation("location1"));
		organizationLocations.add(getOrganizationLocation("locationbase"));
		organizationLocations.add(getOrganizationLocation("BaseLocation"));
		organizationLocations.add(getOrganizationLocation("baseLocation"));
		OrganizationLocation organizationLocation = EventControllerUtil.getOrganizationLocation(organizationLocations, baseLocationName);
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
		TemplateDTO templateDTO = getTemplateDTO(0, false, false, false);
		List<GameRoundType> gameRounds = EventControllerUtil.getGameRounds(templateDTO);
		Assert.assertNotNull(gameRounds);
		Assert.assertEquals(0, gameRounds.size());
	}

	@Test
	public void getGameRounds_found() {
		TemplateDTO templateDTO = getTemplateDTO(4, false, true, true);
		List<GameRoundType> gameRounds = EventControllerUtil.getGameRounds(templateDTO);
		Assert.assertNotNull(gameRounds);
		Assert.assertEquals(6, gameRounds.size());
	}

	private TemplateDTO getTemplateDTO(int preliminary, boolean quarterFinal, boolean semiFinal, boolean championship) {
		TemplateDTO templateDTO = new TemplateDTO();
		RoundDTO roundDTO = new RoundDTO();
		roundDTO.setPreliminary(preliminary);
		roundDTO.setQuarterFinal(quarterFinal);
		roundDTO.setSemiFinal(semiFinal);
		roundDTO.setChampionship(championship);
		templateDTO.setRoundDTO(roundDTO);
		return templateDTO;
	}

	@Test
	public void determineHalfDay_first() {
		HalfDay halfDay = EventControllerUtil.determineHalfDay(LocalDate.of(2020, 6, 5), 2.0f);
		Assert.assertEquals(HalfDay.First, halfDay);
	}

	@Test
	public void determineHalfDay_last() {
		HalfDay halfDay = EventControllerUtil.determineHalfDay(LocalDate.of(2020, 6, 6), 2.0f);
		Assert.assertEquals(HalfDay.Last, halfDay);
	}

	@Test
	public void determineHalfDay_none() {
		HalfDay halfDay = EventControllerUtil.determineHalfDay(LocalDate.of(2020, 6, 6), 2.5f);
		Assert.assertEquals(HalfDay.None, halfDay);
	}
}