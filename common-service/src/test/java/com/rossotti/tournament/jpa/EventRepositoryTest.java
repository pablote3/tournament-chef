package com.rossotti.tournament.jpa;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.jpa.enumeration.*;
import com.rossotti.tournament.jpa.model.*;
import com.rossotti.tournament.jpa.repository.EventRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@ActiveProfiles(profiles = "development")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceConfig.class)
public class EventRepositoryTest {

	@Autowired
	private EventRepository eventRepository;

	@Test
	public void findById() {
		Event event = eventRepository.findById(1L);
		Assert.assertEquals(LocalDate.of(2020, 9, 29), event.getStartDate());
		Assert.assertEquals(LocalDate.of(2020, 9, 30), event.getEndDate());
		Assert.assertEquals("Campania Regional Frosh Soph Tournament", event.getEventName());
		Assert.assertEquals(EventStatus.Sandbox, event.getEventStatus());
		Assert.assertEquals(EventType.Tournament, event.getEventType());
		Assert.assertEquals(Sport.WaterPolo, event.getSport());
		Assert.assertEquals(LocalDateTime.of(2020, 1, 16, 20, 0), event.getCreateTs());
		Assert.assertEquals(LocalDateTime.of(2020, 1, 19, 20, 0), event.getLupdTs());
		Assert.assertTrue(event.getLupdUserId() == 2L);
		Assert.assertEquals(5, event.getOrganization().getUsers().size());
		Assert.assertEquals(2, event.getOrganization().getTeams().size());
		Assert.assertEquals(2, event.getOrganization().getLocations().size());
		Assert.assertEquals(2, event.getEventTeams().size());
		Assert.assertEquals(2, event.getGameDates().size());
		Assert.assertEquals(2, event.getGameDates().get(0).getGameLocations().size());
		Assert.assertEquals(2, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().size());
		Assert.assertEquals(1, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().get(0).getGames().size());
		Assert.assertEquals(1, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().get(0).getGames().get(0).getGameTeams().size());
		Assert.assertEquals("4x4Pairing+Semis+Finals", event.getTemplate().getTemplateName());
	}

	@Test
	public void findAll() {
		List<Event> events = eventRepository.findAll();
		Assert.assertTrue(events.size() >= 4);
	}

	@Test
	public void findByOrganizationName_Found() {
		List<Event> events = eventRepository.findByOrganizationName("FC Juventes");
		Assert.assertEquals(4, events.size());
	}

	@Test
	public void findByOrganizationName_NotFound() {
		List<Event> events = eventRepository.findByOrganizationName("Juventes");
		Assert.assertEquals(0, events.size());
	}

	@Test
	public void findByOrganizationNameAndAsOfDate_Found() {
		List<Event> events = eventRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2020, 9, 24));
		Event event = events.get(0);
		Assert.assertEquals("Lombardy Memorial Tournament", event.getEventName());
	}

	@Test
	public void findByOrganizationNameAndAsOfDate_NotFound() {
		List<Event> events = eventRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2020, 8, 20));
		Assert.assertEquals(0, events.size());
	}

	@Test
	public void findByOrganizationNameAsOfDateTemplateName_Found() {
		Event event = eventRepository.findByOrganizationNameAsOfDateTemplateName("FC Juventes", LocalDate.of(2020, 9, 24), "4x4Pairing+Semis+Finals");
		Assert.assertEquals("Lombardy Memorial Tournament", event.getEventName());
	}

	@Test
	public void findByOrganizationNameAsOfDateTemplateName_NotFound() {
		Event event = eventRepository.findByOrganizationNameAsOfDateTemplateName("FC Juventes", LocalDate.of(2020, 9, 23), "4x4Pairing+Semis+Finals");
		Assert.assertNull(event);
	}

	@Test
	public void create() {
		eventRepository.save(createMockEvent("Juventes Fall Classic", LocalDate.of(2012, 9, 10), LocalDate.of(2012, 9, 11)));
		List<Event> events = eventRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2012, 9, 10));
		Event event = events.get(0);
		Assert.assertEquals("Juventes Fall Classic", event.getEventName());
		Assert.assertEquals(2, event.getEventTeams().size());
		Assert.assertEquals(2, event.getGameDates().size());
		Assert.assertEquals(2, event.getGameDates().get(0).getGameLocations().size());
		Assert.assertEquals(2, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().size());
		Assert.assertEquals(1, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().get(0).getGames().size());
		Assert.assertEquals(1, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().get(0).getGames().get(0).getGameTeams().size());
	}

	@Test(expected= DataIntegrityViolationException.class)
	public void create_MissingRequiredData() {
		eventRepository.save(createMockEvent(null, LocalDate.of(2012, 9, 10), LocalDate.of(2012, 9, 11)));
	}

	@Test(expected= DataIntegrityViolationException.class)
	public void create_Duplicate() {
		eventRepository.save(createMockEvent("Lombardy Halloween Invitational", LocalDate.of(2020, 10, 24), LocalDate.of(2020, 10, 25)));
	}

	@Test
	public void update() {
		eventRepository.save(updateMockEvent("FC Juventes", LocalDate.of(2020, 10, 25), EventStatus.Complete));
		List<Event> events = eventRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2020, 10, 25));
		Event event = events.get(0);
		Assert.assertEquals(EventStatus.Complete, event.getEventStatus());
	}

	@Test(expected= DataIntegrityViolationException.class)
	public void update_MissingRequiredData() {
		eventRepository.save(updateMockEvent("FC Juventes", LocalDate.of(2020, 10, 25), null));
	}

	@Test
	public void delete() {
		List<Event> events = eventRepository.findByOrganizationNameAndAsOfDate("US Sassuolo", LocalDate.of(2020, 8, 26));
		Event event = events.get(0);
		if (event != null) {
			eventRepository.deleteById(event.getId());
		}
		else {
			Assert.fail("Unable to find record to delete");
		}
		List<Event> findEvents = eventRepository.findByOrganizationNameAndAsOfDate("US Sassuolo", LocalDate.of(2020, 8, 26));
		Assert.assertEquals(0, findEvents.size());
	}

	private Event createMockEvent(String eventName, LocalDate startDate, LocalDate endDate) {
		Event event = new Event();
		event.setOrganization(createMockOrganization());
		event.setTemplate(createMockTemplate());
		event.setEventName(eventName);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setEventStatus(EventStatus.Sandbox);
		event.setEventType(EventType.Tournament);
		event.setSport(Sport.WaterPolo);
		event.getEventTeams().add(createMockEventTeam(3L, 1L, "Verona", event));
		event.getEventTeams().add(createMockEventTeam(4L, 4L, "Juventes", event));
		event.getGameDates().add(createMockGameDate(LocalDate.of(2012, 9, 10), event, 1L, 2L));
		event.getGameDates().add(createMockGameDate(LocalDate.of(2012, 9, 11), event, 1L, 3L));
		event.setCreateTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		event.setLupdTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		event.setLupdUserId(4L);
		return event;
	}

	private Organization createMockOrganization() {
		Organization organization = new Organization();
		organization.setId(1L);
		organization.setOrganizationName("FC Juventes");
		organization.setStartDate(LocalDate.of(2016, 2, 21));
		organization.setEndDate(LocalDate.of(9999, 12, 31));
		return organization;
	}

	private Template createMockTemplate() {
		Template template = new Template();
		template.setId(1L);
		return  template;
	}

	private EventTeam createMockEventTeam(Long eventTeamId, Long organizationTeamId, String teamName, Event event) {
		EventTeam eventTeam = new EventTeam();
		eventTeam.setEvent(event);
		eventTeam.setOrganizationTeam(createMockOrganizationTeam(organizationTeamId, teamName));
		return eventTeam;
	}

	private GameDate createMockGameDate(LocalDate date, Event event, Long locationId1, Long locationId2) {
		GameDate gameDate = new GameDate();
		gameDate.setGameDate(date);
		gameDate.getGameLocations().add(createMockGameLocation(locationId1, gameDate));
		gameDate.getGameLocations().add(createMockGameLocation(locationId2, gameDate));
		gameDate.setEvent(event);
		return gameDate;
	}
	
	private OrganizationTeam createMockOrganizationTeam(Long organizationTeamId, String teamName) {
		OrganizationTeam organizationTeam = new OrganizationTeam();
		organizationTeam.setId(organizationTeamId);
		organizationTeam.setOrganization(createMockOrganization());
		organizationTeam.setTeamName(teamName);
		return organizationTeam;
	}

	private GameLocation createMockGameLocation(Long locationId, GameDate gameDate) {
		GameLocation gameLocation = new GameLocation();
		gameLocation.setGameDate(gameDate);
		gameLocation.setOrganizationLocation(createMockOrganizationLocation(locationId));
		gameLocation.setStartTime(LocalTime.of(8, 0, 0));
		gameLocation.getGameRounds().add(createMockGameRound(GameType.GroupPlay, gameLocation));
		gameLocation.getGameRounds().add(createMockGameRound(GameType.Final, gameLocation));
		return gameLocation;
	}

	private GameRound createMockGameRound(GameType gameType, GameLocation gameLocation) {
		GameRound gameRound = new GameRound();
		gameRound.setGameLocation(gameLocation);
		gameRound.setGameType(gameType);
		gameRound.setGameDuration((short)45);
		gameRound.getGames().add(createMockGame(gameRound));
		return gameRound;
	}

	private Game createMockGame(GameRound gameRound) {
		Game game = new Game();
		game.setStartTime(LocalTime.of(8, 0, 0));
		game.setHomeTeamName("Rebels");
		game.setAwayTeamName("Trubadors");
		game.setGameStatus(GameStatus.Scheduled);
		game.setGameRound(gameRound);
		game.getGameTeams().add(createMockGameTeam(game));
		game.setCreateTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		game.setLupdTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		game.setLupdUserId(4L);
		return game;
	}

	private GameTeam createMockGameTeam(Game game) {
		GameTeam gameTeam = new GameTeam();
		gameTeam.setPointsScored((short)12);
		gameTeam.setGame(game);
		return gameTeam;
	}

	private OrganizationLocation createMockOrganizationLocation(Long organizationLocationId) {
		OrganizationLocation organizationLocation = new OrganizationLocation();
		organizationLocation.setId(organizationLocationId);
		organizationLocation.setOrganization(createMockOrganization());
		return organizationLocation;
	}

	private Event updateMockEvent(String orgName, LocalDate asOfDate, EventStatus eventStatus) {
		Event event = eventRepository.findByOrganizationNameAsOfDateTemplateName("FC Juventes", asOfDate, "4x4Pairing+Semis+Finals");
		event.setEventStatus(eventStatus);;
		return event;
	}
}