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
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles(profiles = "development")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceConfig.class)
public class EventRepositoryTest {

	@Autowired
	private EventRepository eventRepository;

	@Test
	public void findById_Found() {
		Event event = eventRepository.findById(1L);
		Assert.assertEquals(LocalDate.of(2020, 9, 29), event.getStartDate());
		Assert.assertEquals(LocalDate.of(2020, 9, 30), event.getEndDate());
		Assert.assertEquals("Campania Regional Frosh Soph Tournament", event.getEventName());
		Assert.assertEquals(EventStatus.Sandbox, event.getEventStatus());
		Assert.assertEquals(EventType.Tournament, event.getEventType());
		Assert.assertEquals(Sport.WaterPolo, event.getSport());
		Assert.assertEquals(LocalDateTime.of(2020, 1, 16, 20, 0), event.getCreateTs());
		Assert.assertEquals(LocalDateTime.of(2020, 1, 19, 20, 0), event.getLupdTs());
		Assert.assertEquals(2, event.getLupdUserId().longValue());
		Assert.assertEquals(4, event.getOrganization().getUserOrganizations().size());
		Assert.assertEquals(4, event.getOrganization().getOrganizationTeams().size());
		Assert.assertEquals(2, event.getOrganization().getOrganizationLocations().size());
		Assert.assertEquals(4, event.getEventTeams().size());
		Assert.assertEquals(2, event.getEventTeams().get(0).getEventTeamRankings().size());
		Assert.assertEquals(2, event.getGameDates().size());
		Assert.assertEquals(2, event.getGameDates().get(0).getGameLocations().size());
		Assert.assertEquals(1, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().size());
		Assert.assertEquals(1, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().get(0).getGames().size());
		Assert.assertEquals(2, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().get(0).getGames().get(0).getGameTeams().size());
		Assert.assertEquals(TemplateType.four_x_four_pp, event.getTemplateType());
	}

	@Test
	public void findById_NotFound() {
		Assert.assertNull(eventRepository.findById(31L));
	}

	@Test
	public void findAll() {
		List<Event> events = eventRepository.findAll();
		Assert.assertTrue(events.size() >= 4);
	}

	@Test
	public void findByEventName_Found() {
		List<Event> events = eventRepository.findByEventName("Lombardy Halloween Invitational");
		Assert.assertEquals(2, events.size());
	}

	@Test
	public void findByEventName_NotFound() {
		List<Event> events = eventRepository.findByEventName("Trentino Sections");
		Assert.assertEquals(0, events.size());
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
	public void findByEventNameAsOfDate_Found() {
		List<Event> events = eventRepository.findByEventNameAsOfDate("Lombardy Halloween Invitational", LocalDate.of(2020, 10, 24));
		Event event = events.get(0);
		Assert.assertEquals("Lombardy Halloween Invitational", event.getEventName());
	}

	@Test
	public void findByEventNameAsOfDate_NotFound() {
		List<Event> events = eventRepository.findByEventNameAsOfDate("Lombardy Halloween Invitational", LocalDate.of(2020, 8, 20));
		Assert.assertEquals(0, events.size());
	}

	@Test
	public void findByOrganizationNameAsOfDate_Found() {
		List<Event> events = eventRepository.findByOrganizationNameAsOfDate("FC Juventes", LocalDate.of(2019, 9, 24));
		Event event = events.get(0);
		Assert.assertEquals("Lombardy Halloween Invitational", event.getEventName());
	}

	@Test
	public void findByOrganizationNameAsOfDate_NotFound() {
		List<Event> events = eventRepository.findByOrganizationNameAsOfDate("FC Juventes", LocalDate.of(2020, 8, 20));
		Assert.assertEquals(0, events.size());
	}

	@Test
	public void findByEventNameAsOfDateTemplateType_Found() {
		Event event = eventRepository.findByEventNameAsOfDateTemplateType("Lombardy Halloween Invitational", LocalDate.of(2020, 10, 24), TemplateType.four_x_four_pp);
		Assert.assertEquals("Lombardy Halloween Invitational", event.getEventName());
	}

	@Test
	public void findByEventNameAsOfDateTemplateType_NotFound() {
		Assert.assertNull(eventRepository.findByEventNameAsOfDateTemplateType("Lombardy Halloween Invitational", LocalDate.of(2020, 9, 23), TemplateType.two_x_four_pp));
	}

	@Test
	public void findByOrganizationNameAsOfDateTemplateType_Found() {
		Event event = eventRepository.findByOrganizationNameAsOfDateTemplateType("FC Juventes", LocalDate.of(2019, 9, 24), TemplateType.four_x_four_pp);
		Assert.assertEquals("Lombardy Halloween Invitational", event.getEventName());
	}

	@Test
	public void findByOrganizationNameAsOfDateTemplateType_NotFound() {
		Assert.assertNull(eventRepository.findByOrganizationNameAsOfDateTemplateType("FC Juventes", LocalDate.of(2020, 9, 23), TemplateType.two_x_four_pp));
	}

	@Test
	public void create() {
		eventRepository.save(createMockEvent(7L, "Tavagnacco Fall Classic", 6L, 1L, 6L, 15L, LocalDate.of(2012, 9, 10), LocalDate.of(2012, 9, 11)));
		Event event = eventRepository.findByEventNameAsOfDateTemplateType("Tavagnacco Fall Classic", LocalDate.of(2012, 9, 10), TemplateType.four_x_four_pp);
		Assert.assertEquals("Tavagnacco Fall Classic", event.getEventName());
		Assert.assertEquals(2, event.getEventTeams().size());
		Assert.assertEquals(2, event.getGameDates().size());
		Assert.assertEquals(2, event.getGameDates().get(0).getGameLocations().size());
		Assert.assertEquals(2, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().size());
		Assert.assertEquals(1, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().get(0).getGames().size());
		Assert.assertEquals(1, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().get(0).getGames().get(0).getGameTeams().size());
	}

	@Test
	public void create_ConstraintViolation_EventNameMissing() {
		Exception exception = assertThrows(ConstraintViolationException.class, () -> eventRepository.save(createMockEvent(1L, null, 1L, 4L, 1L, 2L, LocalDate.of(2012, 9, 10), LocalDate.of(2012, 9, 11))));
		Assert.assertTrue(exception.getMessage().contains("EventName is mandatory"));
	}

	@Test
	public void create_Duplicate() {
		Exception exception = assertThrows(DataIntegrityViolationException.class, () -> eventRepository.save(createMockEvent(1L, "Lombardy Halloween Invitational", 1L, 4L, 1L, 2L, LocalDate.of(2020, 10, 24), LocalDate.of(2020, 10, 25))));
		Assert.assertTrue(exception.getMessage().contains("could not execute statement"));
	}

	@Test
	public void update() {
		eventRepository.save(updateMockEvent(LocalDate.of(2020, 10, 25), EventStatus.Complete));
		Event event = eventRepository.findByEventNameAsOfDateTemplateType("Lombardy Halloween Invitational", LocalDate.of(2020, 10, 25), TemplateType.four_x_four_pp);
		Assert.assertEquals(EventStatus.Complete, event.getEventStatus());
	}

	@Test
	public void update_TransactionSystemException_EventStatusMissing() {
		Exception exception = assertThrows(TransactionSystemException.class, () -> eventRepository.save(updateMockEvent(LocalDate.of(2020, 10, 25), null)));
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("EventStatus is mandatory"));
	}

	@Test
	public void delete() {
		Event event = eventRepository.findByEventNameAsOfDateTemplateType("Trentino Sections Tournament", LocalDate.of(2020, 8, 24), TemplateType.four_x_four_rr);
		if (event != null) {
			eventRepository.deleteById(event.getId());
		}
		else {
			Assert.fail("Unable to find record to delete");
		}
		Assert.assertNull(eventRepository.findByEventNameAsOfDateTemplateType("Trentino Sections Tournament", LocalDate.of(2020, 8, 24), TemplateType.four_x_four_rr));
	}

	public static Event createMockEvent(Long organizationId, String eventName, Long orgTeamId1, Long orgTeamId2, Long gameLocationId1, Long gameLocationId2, LocalDate startDate, LocalDate endDate) {
		Event event = new Event();
		event.setOrganization(createMockOrganization(organizationId));
		event.setTemplateType(TemplateType.four_x_four_pp);
		event.setEventName(eventName);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setEventStatus(EventStatus.Sandbox);
		event.setEventType(EventType.Tournament);
		event.setSport(Sport.WaterPolo);
		event.getEventTeams().add(createMockEventTeam(createMockOrganizationTeam(orgTeamId1), event, "BaseTeam1"));
		event.getEventTeams().add(createMockEventTeam(createMockOrganizationTeam(orgTeamId2), event, "BaseTeam2"));
		event.getGameDates().add(createMockGameDate(startDate, event, gameLocationId1, gameLocationId2));
		if (Period.between(startDate, endDate).getDays() != 0) {
			event.getGameDates().add(createMockGameDate(endDate, event, gameLocationId1, gameLocationId2));
		}
		event.setCreateTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		event.setLupdTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		event.setLupdUserId(4L);
		return event;
	}

	private static Organization createMockOrganization(Long organizationId) {
		Organization organization = new Organization();
		organization.setId(organizationId);
		return organization;
	}

	private static EventTeam createMockEventTeam(OrganizationTeam organizationTeam, Event event, String baseTeamName) {
		EventTeam eventTeam = new EventTeam();
		eventTeam.setEvent(event);
		eventTeam.setOrganizationTeam(organizationTeam);
		eventTeam.setBaseTeamName(baseTeamName);
		return eventTeam;
	}

	private static GameDate createMockGameDate(LocalDate date, Event event, Long gameLocationId1, Long gameLocationId2) {
		GameDate gameDate = new GameDate();
		gameDate.setEvent(event);
		gameDate.setGameDate(date);
		gameDate.getGameLocations().add(createMockGameLocation(gameLocationId1, gameDate));
		if (gameLocationId2 != null) {
			gameDate.getGameLocations().add(createMockGameLocation(gameLocationId2, gameDate));
		}
		return gameDate;
	}
	
	private static OrganizationTeam createMockOrganizationTeam(Long id) {
		OrganizationTeam organizationTeam = new OrganizationTeam();
		organizationTeam.setId(id);
		return organizationTeam;
	}

	private static GameLocation createMockGameLocation(Long locationId, GameDate gameDate) {
		GameLocation gameLocation = new GameLocation();
		gameLocation.setGameDate(gameDate);
		gameLocation.setOrganizationLocation(createMockOrganizationLocation(locationId));
		gameLocation.setBaseLocationName("BaseLocation1");
		gameLocation.setStartTime(LocalTime.of(8, 0, 0));
		gameLocation.getGameRounds().add(createMockGameRound(GameRoundType.GroupPlay, gameLocation));
		gameLocation.getGameRounds().add(createMockGameRound(GameRoundType.Final, gameLocation));
		return gameLocation;
	}

	private static GameRound createMockGameRound(GameRoundType gameType, GameLocation gameLocation) {
		GameRound gameRound = new GameRound();
		gameRound.setGameLocation(gameLocation);
		gameRound.setGameType(gameType);
		gameRound.setGameDuration((short)45);
		gameRound.getGames().add(createMockGame(gameRound));
		return gameRound;
	}

	private static Game createMockGame(GameRound gameRound) {
		Game game = new Game();
		game.setGameRound(gameRound);
		game.setStartTime(LocalTime.of(8, 0, 0));
		game.setGameStatus(GameStatus.Scheduled);
		game.getGameTeams().add(createMockGameTeam(game));
		game.setCreateTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		game.setLupdTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		game.setLupdUserId(4L);
		return game;
	}

	public static GameTeam createMockGameTeam(Game game) {
		GameTeam gameTeam = new GameTeam();
		gameTeam.setGame(game);
		gameTeam.setPointsScored((short)12);
		gameTeam.setHomeTeam(Boolean.TRUE);
		gameTeam.setEventTeam(game.getGameRound().getGameLocation().getGameDate().getEvent().getEventTeams().get(0));
		return gameTeam;
	}

	private static OrganizationLocation createMockOrganizationLocation(Long organizationLocationId) {
		OrganizationLocation organizationLocation = new OrganizationLocation();
		organizationLocation.setId(organizationLocationId);
		return organizationLocation;
	}

	private Event updateMockEvent(LocalDate asOfDate, EventStatus eventStatus) {
		Event event = eventRepository.findByEventNameAsOfDateTemplateType("Lombardy Halloween Invitational", asOfDate, TemplateType.four_x_four_pp);
		event.setEventStatus(eventStatus);
		return event;
	}
}