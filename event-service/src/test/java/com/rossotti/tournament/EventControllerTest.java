package com.rossotti.tournament;

import com.rossotti.tournament.client.TemplateFinderService;
import com.rossotti.tournament.controller.EventController;
import com.rossotti.tournament.dto.EventDTO;
import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.TemplateType;
import com.rossotti.tournament.exception.EntityExistsException;
import com.rossotti.tournament.exception.NoSuchEntityException;
import com.rossotti.tournament.jpa.service.EventJpaService;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import com.rossotti.tournament.model.Event;
import com.rossotti.tournament.model.Organization;
import com.rossotti.tournament.model.OrganizationLocation;
import com.rossotti.tournament.model.OrganizationTeam;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.time.LocalDate;

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

	@Mock
	private TemplateFinderService templateFinderService;

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
			.thenReturn(createMockOrganization(true, true));
		when(eventJpaService.findByOrganizationNameAsOfDateTemplateType(anyString(), any(), any()))
			.thenReturn(createMockEvent());
		EntityExistsException exception = assertThrows(EntityExistsException.class, () -> eventController.createEvent(createMockInitialEventDTO()));
		Assert.assertTrue(exception.getMessage().contains("Event already exists"));
	}

	@Test
	public void createEvent_findTemplate_fileNotFound() throws Exception {
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(createMockOrganization(true, true));
		when(eventJpaService.findByOrganizationNameAsOfDateTemplateType(anyString(), any(), any()))
			.thenReturn(null);
		when(templateFinderService.findTemplateType(anyString()))
			.thenThrow(IOException.class);
		assertThrows(IOException.class, () -> eventController.createEvent(createMockInitialEventDTO()));
	}

	@Test
	public void createEvent_findTemplate_TemplateTypeNotFound() throws Exception {
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(createMockOrganization(true, true));
		when(eventJpaService.findByOrganizationNameAsOfDateTemplateType(anyString(), any(), any()))
			.thenReturn(null);
		when(templateFinderService.findTemplateType(anyString()))
			.thenThrow(NoSuchEntityException.class);
		assertThrows(NoSuchEntityException.class, () -> eventController.createEvent(createMockInitialEventDTO()));
	}

	@Test
	public void createEvent_BaseTeamNotFound() throws Exception {
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(createMockOrganization(false, true));
		when(eventJpaService.findByOrganizationNameAsOfDateTemplateType(anyString(), any(), any()))
			.thenReturn(null);
		when(templateFinderService.findTemplateType(anyString()))
			.thenReturn(createMockTemplateDTO());
		assertThrows(NoSuchEntityException.class, () -> eventController.createEvent(createMockInitialEventDTO()));
	}

	@Test
	public void createEvent_BaseLocationNotFound() throws Exception {
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(createMockOrganization(true, false));
		when(eventJpaService.findByOrganizationNameAsOfDateTemplateType(anyString(), any(), any()))
			.thenReturn(null);
		when(templateFinderService.findTemplateType(anyString()))
			.thenReturn(createMockTemplateDTO());
		assertThrows(NoSuchEntityException.class, () -> eventController.createEvent(createMockInitialEventDTO()));
	}

	@Test
	public void createEvent_persistenceException() throws Exception {
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(createMockOrganization(true, true));
		when(eventJpaService.findByOrganizationNameAsOfDateTemplateType(anyString(), any(), any()))
			.thenReturn(null);
		when(templateFinderService.findTemplateType(anyString()))
			.thenReturn(createMockTemplateDTO());
		when(eventJpaService.save(any()))
			.thenThrow(PersistenceException.class);
		assertThrows(PersistenceException.class, () -> eventController.createEvent(createMockInitialEventDTO()));
	}

	@Test
	public void createEvent_success() throws Exception {
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(createMockOrganization(true, true));
		when(eventJpaService.findByOrganizationNameAsOfDateTemplateType(anyString(), any(), any()))
			.thenReturn(null);
		when(templateFinderService.findTemplateType(anyString()))
			.thenReturn(createMockTemplateDTO());
		when(eventJpaService.save(any()))
			.thenReturn(createMockEvent());
		Event event = eventController.createEvent(createMockInitialEventDTO());
		Assert.assertEquals("Algarve Soccer Cup", event.getEventName());
		Assert.assertEquals(16, event.getEventTeams().size());
		Assert.assertEquals(2, event.getGameDates().size());
		Assert.assertEquals(2, event.getGameDates().get(0).getGameLocations().size());
	}

	private EventDTO createMockInitialEventDTO() {
		EventDTO eventDTO = new EventDTO();
		eventDTO.setEventName("Algarve Soccer Cup");
		eventDTO.setOrganizationName("Fiesole School District");
		eventDTO.setsTemplateType("four_x_four_pp");
		eventDTO.setSport("WaterPolo");
		eventDTO.setEventDays(2);
		eventDTO.setEventLocations(2);
		eventDTO.setStartDate(LocalDate.of(2020, 9, 29));
		return eventDTO;
	}

	private Organization createMockOrganization(boolean hasBaseTeam, boolean hasBaseLocation) {
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
		if (hasBaseTeam) {
			organization.getOrganizationTeams().add(createMockOrganizationTeam("BaseTeam"));
		}
		organization.getOrganizationTeams().add(createMockOrganizationTeam("San Marino Academy"));
		if (hasBaseLocation) {
			organization.getOrganizationLocations().add(createMockOrganizationLocation("BaseLocation"));
		}
		organization.getOrganizationLocations().add(createMockOrganizationLocation("Stadio Tre Fontane"));
		return organization;
	}

	private OrganizationTeam createMockOrganizationTeam(String teamName) {
		OrganizationTeam organizationTeam = new OrganizationTeam();
		organizationTeam.setTeamName(teamName);
		return organizationTeam;
	}

	private OrganizationLocation createMockOrganizationLocation(String locationName) {
		OrganizationLocation organizationLocation = new OrganizationLocation();
		organizationLocation.setLocationName(locationName);
		return organizationLocation;
	}

	private Event createMockEvent() {
		Event event = new Event();
		event.setEventName("Cypress Cup");
		return event;
	}
	
	private TemplateDTO createMockTemplateDTO() {
		TemplateDTO templateDTO = new TemplateDTO();
		templateDTO.setTemplateType(TemplateType.four_x_four_pp);
		templateDTO.setGridGroupCount((short)4);
		templateDTO.setGridTeamCount((short)4);
		templateDTO.setRoundCount((short)4);
		templateDTO.setPlayoffGames(Boolean.FALSE);
		templateDTO.setQuarterFinalGames(Boolean.TRUE);
		templateDTO.setSemiFinalGames(Boolean.TRUE);
		return templateDTO;		
	}
}