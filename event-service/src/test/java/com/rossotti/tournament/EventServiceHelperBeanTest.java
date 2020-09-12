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
	public void buildDisplayGameIds_valid() {
		List<GameDate> gameDates = EventServiceUtilTest.buildGameDates(1L, 3L, 5L, 7L, 2L, 4L, 6L, 8L);
		Assert.assertTrue( eventServiceHelperBean.validateGames(gameDates));
	}

	@Test
	public void buildDisplayGameIds_invalidNullValue() {
		List<GameDate> gameDates = EventServiceUtilTest.buildGameDates(1L, 3L, 5L, null, 2L, 4L, 6L, 8L);
		Assert.assertFalse( eventServiceHelperBean.validateGames(gameDates));
	}

	@Test
	public void buildDisplayGameIds_invalidDuplicateValue() {
		List<GameDate> gameDates = EventServiceUtilTest.buildGameDates(1L, 3L, 5L, 5L, 2L, 4L, 6L, 8L);
		Assert.assertFalse( eventServiceHelperBean.validateGames(gameDates));
	}

	@Test
	public void buildDisplayGameIds_invalidMissingValue() {
		List<GameDate> gameDates = EventServiceUtilTest.buildGameDates(1L, 3L, 5L, 101L, 2L, 4L, 6L, 8L);
		Assert.assertFalse( eventServiceHelperBean.validateGames(gameDates));
	}
}