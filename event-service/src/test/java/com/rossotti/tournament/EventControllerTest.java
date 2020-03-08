package com.rossotti.tournament;

import com.rossotti.tournament.controller.EventController;
import com.rossotti.tournament.dto.EventDTO;
import com.rossotti.tournament.exception.EntityExistsException;
import com.rossotti.tournament.exception.NoSuchEntityException;
import com.rossotti.tournament.model.Event;
import com.rossotti.tournament.model.Organization;
import com.rossotti.tournament.jpa.service.EventJpaService;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import javax.persistence.PersistenceException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {
	@Mock
	private OrganizationJpaService organizationJpaService;

	@Mock
	private EventJpaService eventJpaService;

	@InjectMocks
	private EventController eventController;

	@Test
	public void findByOrganizationNameAsOfDate_notFound() {
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(null);
		NoSuchEntityException exception = assertThrows(NoSuchEntityException.class, () -> eventController.createEvent(createMockInitialEventDTO()));
		Assert.assertTrue(exception.getMessage().contains("Organization does not exist"));
	}

	@Test
	public void findByEventNameAsOfDateTemplateType_found() {
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(createMockOrganization());
		when(eventJpaService.findByOrganizationNameAsOfDateTemplateType(anyString(), any(), any()))
			.thenReturn(createMockEvent());
		EntityExistsException exception = assertThrows(EntityExistsException.class, () -> eventController.createEvent(createMockInitialEventDTO()));
		Assert.assertTrue(exception.getMessage().contains("Event already exists"));
	}

	@Test
	public void createEvent_persistenceException() {
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(createMockOrganization());
		when(eventJpaService.findByOrganizationNameAsOfDateTemplateType(anyString(), any(), any()))
			.thenReturn(null);
		when(eventJpaService.save(any()))
			.thenThrow(PersistenceException.class);
		assertThrows(PersistenceException.class, () -> eventController.createEvent(createMockInitialEventDTO()));
	}

	@Test
	public void createEvent_success() {
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(createMockOrganization());
		when(eventJpaService.findByOrganizationNameAsOfDateTemplateType(anyString(), any(), any()))
			.thenReturn(null);
		when(eventJpaService.save(any()))
			.thenReturn(createMockEvent());
		Event event = eventController.createEvent(createMockInitialEventDTO());
		Assert.assertEquals("Cypress Cup", event.getEventName());
	}

	private EventDTO createMockInitialEventDTO() {
		EventDTO event = new EventDTO();
		event.setEventName("Algarve Soccer Cup");
		event.setOrganizationName("Fiesole School District");
		event.setsTemplateType("four_x_four_rr");
		event.setEventLocationCount(2);
		return event;
	}

	private Organization createMockOrganization() {
		Organization organization = new Organization();
		organization.setOrganizationName("Fiesole School District");
		organization.setAddress1("123 Main Street");
		organization.setAddress2("Suite 101");
		organization.setCity("Fiesole");
		organization.setState("Tuscany");
		organization.setCountry("Italy");
		organization.setZipCode("50014");
		organization.setContactEmail("bonfantini.agnes@telitaly.com");
		organization.setContactLastName("Bonfantini");
		organization.setContactFirstName("Agnes");
		organization.setContactPhone("520-158-1258");
		return organization;
	}

	private Event createMockEvent() {
		Event event = new Event();
		event.setEventName("Cypress Cup");
		return event;
	}
}