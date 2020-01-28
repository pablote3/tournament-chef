package com.rossotti.tournament.jpa;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.jpa.enumeration.EventStatus;
import com.rossotti.tournament.jpa.enumeration.EventType;
import com.rossotti.tournament.jpa.enumeration.Sport;
import com.rossotti.tournament.jpa.model.Event;
import com.rossotti.tournament.jpa.model.EventTeam;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.model.OrganizationTeam;
import com.rossotti.tournament.jpa.repository.EventRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
		Assert.assertEquals(LocalDate.of(2020, 9, 30), event.getStartDate());
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
	}

	@Test
	public void findAll() {
		List<Event> events = eventRepository.findAll();
		Assert.assertTrue(events.size() >= 4);
	}

//	@Test
//	public void findByTemplateName_Found() {
//		List<Event> events = eventRepository.findByTemplateName("temps");
//		Assert.assertEquals(1, events.size());
//	}

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
		Event event = eventRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2020, 9, 24));
		Assert.assertEquals("Lombardy Memorial Tournament", event.getEventName());
	}

	@Test
	public void findByOrganizationNameAndAsOfDate_NotFound() {
		Event event = eventRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2020, 8, 20));
		Assert.assertNull(event);
	}

	@Test
	public void create() {
		eventRepository.save(createMockEvent("Juventes Fall Classic"));
		Event event = eventRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2012, 9, 10));
		Assert.assertEquals("Juventes Fall Classic", event.getEventName());
		Assert.assertEquals(2, event.getEventTeams().size());
	}

	@Test(expected= DataIntegrityViolationException.class)
	public void create_MissingRequiredData() {
		eventRepository.save(createMockEvent(null));
	}

	// Create duplicate event test not possible.  Because of key organization id, performs an update instead.

//	@Test
//	public void update() {
//		userRepository.save(updateMockUser("FC Juventes", "valentina.bergamaschi@hotmail.com", "Valencia"));
//		User user = userRepository.findByOrganizationNameAndUserEmail("FC Juventes", "valentina.bergamaschi@hotmail.com");
//		Assert.assertEquals("Valencia", user.getLastName());
//	}
//
//	@Test(expected= DataIntegrityViolationException.class)
//	public void update_MissingRequiredData() {
//		userRepository.save(updateMockUser("FC Juventes", "alessia.piazza@telecomitalia.com", null));
//	}
//
//	@Test
//	public void delete() {
//		User user = userRepository.findByOrganizationNameAndUserEmail("FC Juventes", "martina.capelli@telecomitalia.com");
//		if (user != null) {
//			userRepository.deleteById(user.getId());
//		}
//		else {
//			Assert.fail("Unable to find record to delete");
//		}
//		User findUser = userRepository.findByOrganizationNameAndUserEmail("FC Juventes", "martina.capelli@telecomitalia.com");
//		Assert.assertNull(findUser);
//	}

	private Event createMockEvent(String eventName) {
		Event event = new Event();
		event.setOrganization(createMockOrganization());
		event.setEventName(eventName);
		event.setStartDate(LocalDate.of(2012, 9, 10));
		event.setEndDate(LocalDate.of(2012, 9, 11));
		event.setEventStatus(EventStatus.Sandbox);
		event.setEventType(EventType.Tournament);
		event.setSport(Sport.WaterPolo);
		event.getEventTeams().add(createMockEventTeam(3L, 1L, "Verona", event));
		event.getEventTeams().add(createMockEventTeam(4L, 4L, "Juventes", event));
		event.setCreateTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		event.setLupdTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		event.setLupdUserId(4L);
		return event;
	}

	private Organization createMockOrganization() {
		Organization organization = new Organization();
		organization.setId(2L);
		organization.setOrganizationName("FC Juventes");
		organization.setStartDate(LocalDate.of(2016, 2, 21));
		organization.setEndDate(LocalDate.of(9999, 12, 31));
		return organization;
	}

	private EventTeam createMockEventTeam(Long eventTeamId, Long organizationTeamId, String teamName, Event event) {
		EventTeam eventTeam = new EventTeam();
		eventTeam.setEvent(event);
		eventTeam.setOrganizationTeam(createMockOrganizationTeam(organizationTeamId, teamName));
		return eventTeam;
	}

	private OrganizationTeam createMockOrganizationTeam(Long organizationTeamId, String teamName) {
		OrganizationTeam organizationTeam = new OrganizationTeam();
		organizationTeam.setId(organizationTeamId);
		organizationTeam.setOrganization(createMockOrganization());
		organizationTeam.setTeamName(teamName);
		return organizationTeam;
	}
}