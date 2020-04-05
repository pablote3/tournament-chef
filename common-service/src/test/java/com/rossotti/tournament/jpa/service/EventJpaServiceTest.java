package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.enumeration.EventType;
import com.rossotti.tournament.enumeration.Sport;
import com.rossotti.tournament.exception.NoSuchEntityException;
import com.rossotti.tournament.enumeration.EventStatus;
import com.rossotti.tournament.enumeration.TemplateType;
import com.rossotti.tournament.model.Event;
import com.rossotti.tournament.jpa.repository.EventRepositoryTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.rossotti.tournament.config.ServiceConfig.class)
public class EventJpaServiceTest {

	private EventJpaService eventJpaService;

	@Autowired
	public void setEventJpaService(EventJpaService eventJpaService) {
		this.eventJpaService = eventJpaService;
	}

	@Test
	public void getById_Found() {
		Event event = eventJpaService.getById(1L);
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
		Assert.assertEquals(2, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().size());
		Assert.assertEquals(1, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().get(0).getGames().size());
		Assert.assertEquals(2, event.getGameDates().get(0).getGameLocations().get(0).getGameRounds().get(0).getGames().get(0).getGameTeams().size());
		Assert.assertEquals(TemplateType.four_x_four_pp, event.getTemplateType());
	}

	@Test
	public void findById_NotFound() {
		Assert.assertNull(eventJpaService.getById(21L));
	}

	@Test
	public void listAll() {
		List<Event> events = (List<Event>) eventJpaService.listAll();
		Assert.assertTrue(events.size() >= 3);
	}

	@Test
	public void findByEventNameAsOfDateTemplateType_Found() {
		Event event = eventJpaService.findByEventNameAsOfDateTemplateType("Lombardy Halloween Invitational", LocalDate.of(2020, 10, 24), TemplateType.four_x_four_pp);
		Assert.assertEquals("Lombardy Halloween Invitational", event.getEventName());
	}

	@Test
	public void findByEventNameAsOfDateTemplateType_NotFound() {
		Assert.assertNull(eventJpaService.findByEventNameAsOfDateTemplateType("Lombardy Halloween Invitational", LocalDate.of(2020, 9, 28), TemplateType.four_x_four_pp));
	}

	@Test
	public void findByOrganizationNameAsOfDateTemplateType_Found() {
		Event event = eventJpaService.findByOrganizationNameAsOfDateTemplateType("FC Juventes", LocalDate.of(2020, 9, 29), TemplateType.four_x_four_pp);
		Assert.assertEquals("Campania Regional Frosh Soph Tournament", event.getEventName());
	}

	@Test
	public void findByOrganizationNameAsOfDateTemplateType_NotFound() {
		Assert.assertNull(eventJpaService.findByOrganizationNameAsOfDateTemplateType("FC Juventes", LocalDate.of(2020, 9, 28), TemplateType.four_x_four_pp));
	}

	@Test
	public void findByEventNameAsOfDate_Found() {
		List<Event> events = eventJpaService.findByEventNameAsOfDate("Lombardy Halloween Invitational", LocalDate.of(2020, 10, 24));
		Assert.assertEquals(1, events.size());
	}

	@Test
	public void findByEventNameAsOfDate_NotFound() {
		List<Event> events = eventJpaService.findByEventNameAsOfDate("Lombardy Halloween Invitational", LocalDate.of(2020, 9, 28));
		Assert.assertEquals(0, events.size());
	}

	@Test
	public void findByOrganizationNameAsOfDate_Found() {
		List<Event> events = eventJpaService.findByOrganizationNameAsOfDate("FC Juventes", LocalDate.of(2020, 9, 29));
		Assert.assertEquals(1, events.size());
	}

	@Test
	public void findByOrganizationNameAsOfDate_NotFound() {
		List<Event> events = eventJpaService.findByOrganizationNameAsOfDate("FC Juventes", LocalDate.of(2020, 9, 28));
		Assert.assertEquals(0, events.size());
	}

	@Test
	public void findByEventName_Found() {
		List<Event> events = eventJpaService.findByEventName("Lombardy Halloween Invitational");
		Assert.assertEquals(2, events.size());
	}

	@Test
	public void findByEventName_NotFound() {
		List<Event> events = eventJpaService.findByEventName("Lombardy Halloween");
		Assert.assertEquals(0, events.size());
	}

	@Test
	public void findByOrganizationName_Found() {
		List<Event> events = eventJpaService.findByOrganizationName("FC Juventes");
		Assert.assertEquals(4, events.size());
	}

	@Test
	public void findByOrganizationName_NotFound() {
		List<Event> events = eventJpaService.findByOrganizationName("Juventes");
		Assert.assertEquals(0, events.size());
	}

	@Test
	public void create_Created() {
		eventJpaService.save(EventRepositoryTest.createMockEvent(4L, "Florence Caput Mundi", 7L, 1L, 4L, null, LocalDate.of(2020, 1, 15), LocalDate.of(2020, 1, 15)));
		Event findEvent = eventJpaService.findByEventNameAsOfDateTemplateType("Florence Caput Mundi", LocalDate.of(2020, 1, 15), TemplateType.four_x_four_pp);
		Assert.assertEquals(EventStatus.Sandbox, findEvent.getEventStatus());
		Assert.assertEquals(2, findEvent.getEventTeams().size());
		Assert.assertEquals(1, findEvent.getGameDates().size());
	}

	@Test
	public void create_EventNameIsMandatory_Empty() {
		ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () ->
			eventJpaService.save(EventRepositoryTest.createMockEvent(4L, "", 3L, 4L, 4L, null, LocalDate.of(2020, 1, 15), LocalDate.of(2020, 1, 15))));
		Assert.assertTrue(exception.getMessage().contains("EventName is mandatory"));
	}

	@Test
	public void create_EventNameIsMandatory_Null() {
		ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () ->
			eventJpaService.save(EventRepositoryTest.createMockEvent(4L, null, 3L, 4L, 4L, null, LocalDate.of(2020, 1, 15), LocalDate.of(2020, 1, 15))));
		Assert.assertTrue(exception.getMessage().contains("EventName is mandatory"));
	}

	@Test
	public void update_Updated() {
		Event event = eventJpaService.findByEventNameAsOfDateTemplateType("Lombardy Halloween Invitational", LocalDate.of(2019, 9, 24), TemplateType.four_x_four_pp);
		Assert.assertEquals(EventStatus.Scheduled, event.getEventStatus());
		event.setEventStatus(EventStatus.InProgress);
		event.setLupdUserId(3L);
		event.setLupdTs(LocalDateTime.now());
		eventJpaService.save(event);
		Event findEvent = eventJpaService.findByEventNameAsOfDateTemplateType("Lombardy Halloween Invitational", LocalDate.of(2019, 9, 24), TemplateType.four_x_four_pp);
		Assert.assertEquals(EventStatus.InProgress, findEvent.getEventStatus());
	}

	@Test
	public void update_EventTypeIsMandatory_Empty() {
		Event event = eventJpaService.findByEventNameAsOfDateTemplateType("Lombardy Halloween Invitational", LocalDate.of(2019, 9, 24), TemplateType.four_x_four_pp);
		event.setEventType(null);
		TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> eventJpaService.save(event));
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("EventType is mandatory"));
	}

	@Test
	public void delete_Deleted() {
		eventJpaService.delete(6L);
		Assert.assertNull(eventJpaService.getById(6L));
	}

	@Test
	public void delete_NotFound() {
		NoSuchEntityException exception = assertThrows(NoSuchEntityException.class, () -> eventJpaService.delete(21L));
		Assert.assertTrue(exception.getMessage().contains("Event does not exist"));
		Assert.assertEquals(Event.class, exception.getEntityClass());
	}
}