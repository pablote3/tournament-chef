package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.exception.CustomException;
import com.rossotti.tournament.jpa.enumeration.TemplateType;
import com.rossotti.tournament.jpa.model.Event;
import com.rossotti.tournament.jpa.repository.EventRepositoryTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
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
		Assert.assertEquals("Campania Regional Frosh Soph Tournament", event.getEventName());
		Assert.assertEquals(2, event.getEventTeams().size());
		Assert.assertEquals(2, event.getGameDates().size());
	}

	@Test
	public void findById_NotFound() {
		Assert.assertNull(eventJpaService.getById(21L));
	}

	@Test
	public void listAll() {
		List<Event> events = (List<Event>) eventJpaService.listAll();
		Assert.assertTrue(events.size() >= 4);
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
	public void findByOrganizationNameAndAsOfDate_Found() {
		List<Event> events = eventJpaService.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2020, 9, 29));
		Assert.assertEquals(1, events.size());
	}

	@Test
	public void findByOrganizationNameAndAsOfDate_NotFound() {
		List<Event> events = eventJpaService.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2020, 9, 28));
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
		eventJpaService.save(EventRepositoryTest.createMockEvent(4L, "Florence Caput Mundi", 3L, 4L, 4L, null, LocalDate.of(2020, 1, 15), LocalDate.of(2020, 1, 15)));
		Event findEvent = eventJpaService.findByOrganizationNameAsOfDateTemplateType("Fiorentina FC", LocalDate.of(2020, 1, 15), TemplateType.four_x_four_pp);
		Assert.assertEquals("Florence Caput Mundi", findEvent.getEventName());
		Assert.assertEquals(2, findEvent.getEventTeams().size());
		Assert.assertEquals(1, findEvent.getGameDates().size());
	}

	@Test
	public void create_EventNameIsMandatory_Empty() {
		CustomException exception = assertThrows(CustomException.class, () ->
			eventJpaService.save(EventRepositoryTest.createMockEvent(4L, "", 3L, 4L, 4L, null, LocalDate.of(2020, 1, 15), LocalDate.of(2020, 1, 15))));
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("EventName is mandatory", exception.getError().getErrorMessage());
	}

	@Test
	public void create_EventNameIsMandatory_Null() {
		CustomException exception = assertThrows(CustomException.class, () ->
				eventJpaService.save(EventRepositoryTest.createMockEvent(4L, null, 3L, 4L, 4L, null, LocalDate.of(2020, 1, 15), LocalDate.of(2020, 1, 15))));
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("EventName is mandatory", exception.getError().getErrorMessage());
	}

//	@Test
//	public void update_Updated() {
//		Event event = eventJpaService.findByEventNameAndAsOfDate("AC Milan SPA", LocalDate.of(2012, 1, 15));
//		event.setContactLastName("Fusetti");
//		eventJpaService.save(event);
//		Event findEvent = eventJpaService.findByEventNameStartDateEndDate("AC Milan SPA", LocalDate.of(2012, 1, 15), LocalDate.of(9999, 12, 31));
//		Assert.assertEquals("Fusetti", findEvent.getContactLastName());
//	}
//
//	@Test
//	public void update_ContactEmailIsMandatory_Empty() {
//		Event event = eventJpaService.findByEventNameAndAsOfDate("AC Milan SPA", LocalDate.of(2012, 1, 15));
//		event.setContactEmail("");
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			eventJpaService.save(event);
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("ContactEmail is mandatory", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void update_ContactEmailIsMandatory_Null() {
//		Event event = eventJpaService.findByEventNameAndAsOfDate("AC Milan SPA", LocalDate.of(2012, 1, 15));
//		event.setContactEmail(null);
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			eventJpaService.save(event);
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("ContactEmail is mandatory", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void update_ContactEmailInvalidFormat() {
//		Event event = eventJpaService.findByEventNameAndAsOfDate("AC Milan SPA", LocalDate.of(2012, 1, 15));
//		event.setContactEmail("LauraFusetti_dada.it");
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			eventJpaService.save(event);
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("ContactEmail invalid format", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void delete_Deleted() {
//		eventJpaService.delete(7L);
//		Assert.assertNull(eventJpaService.getById(7L));
//	}
//
//	@Test
//	public void delete_NotFound() {
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			eventJpaService.delete(21L);
//		});
//		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
//		Assert.assertEquals("Server error when trying to find record for id of {}", exception.getError().getErrorMessage());
//		Assert.assertEquals("MSG_VAL_0012", exception.getError().getError());
//	}
}