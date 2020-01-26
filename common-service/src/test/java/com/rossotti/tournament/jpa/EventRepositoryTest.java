package com.rossotti.tournament.jpa;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.jpa.enumeration.EventStatus;
import com.rossotti.tournament.jpa.enumeration.EventType;
import com.rossotti.tournament.jpa.enumeration.Sport;
import com.rossotti.tournament.jpa.model.Event;
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
//		Assert.assertEquals(5, event.getOrganization().getUsers().size());
//		Assert.assertEquals(2, event.getOrganization().getTeams().size());
//		Assert.assertEquals(2, event.getOrganization().getLocations().size());
	}

//	@Test
//	public void findAll() {
//		List<Event> events = eventRepository.findAll();
//		Assert.assertTrue(events.size() >= 4);
//	}


//	@Test
//	public void findByTemplateName_Found() {
//		List<Event> events = eventRepository.findByTemplateName("temps");
//		Assert.assertEquals(1, events.size());
//	}



//	@Test
//	public void findByOrganizationName_Found() {
//		List<Event> events = eventRepository.findByOrganizationName("FC Juventes");
//		Assert.assertEquals(3, events.size());
//	}
//
//	@Test
//	public void findByOrganizationName_NotFound() {
//		List<Event> events = eventRepository.findByOrganizationName("Juventes");
//		Assert.assertEquals(0, events.size());
//	}

//	@Test
//	public void findByOrganizationNameAndEmail_Found() {
//		User user = userRepository.findByOrganizationNameAndUserEmail("FC Juventes", "valentina.giacinti@telecomitalia.com");
//		Assert.assertEquals("Giacinti", user.getLastName());
//	}
//
//	@Test
//	public void findByOrganizationNameAndEmail_NotFound() {
//		User user = userRepository.findByOrganizationNameAndUserEmail("FC Juventes", "saratamborini@euro.com");
//		Assert.assertNull(user);
//	}

//	@Test
//	public void create() {
//		eventRepository.save(createMockUser(1L, "amserturini@gmail.com", "Serturini"));
//		Event event = eventRepository.findByOrganizationNameAndUserEmail("FC Juventes", "amserturini@gmail.com");
//		Assert.assertEquals("Serturini", user.getLastName());
//	}

//	@Test(expected= DataIntegrityViolationException.class)
//	public void create_MissingRequiredData() {
//		userRepository.save(createMockUser(1L, "claudia.ciccotti@hotmailcom", null));
//	}
//
//	@Test(expected= DataIntegrityViolationException.class)
//	public void create_Duplicate() {
//		userRepository.save(createMockUser(1L, "valentina.giacinti@telecomitalia.com", "Giacinti"));
//	}
//
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
//
//	private User createMockUser(Long orgId, String email, String lastName) {
//		User user = new User();
//		user.setOrganization(createMockOrganization(orgId));
//		user.setEmail(email);
//		user.setUserType(UserType.Administrator);
//		user.setUserStatus(UserStatus.Active);
//		user.setLastName(lastName);
//		user.setFirstName("Annamaria");
//		user.setPassword("superpass");
//		user.setCreateTs(LocalDateTime.of(2019, 10, 27, 20, 30));
//		user.setLupdTs(LocalDateTime.of(2019, 10, 27, 20, 30));
//		user.setLupdUserId(4L);
//		return user;
//	}
//
//	private User updateMockUser(String orgName, String email, String lastName) {
//		User user = userRepository.findByOrganizationNameAndUserEmail(orgName, email);
//		user.setLastName(lastName);
//		return user;
//	}
//
//	private Organization createMockOrganization(Long orgId) {
//		Organization organization = new Organization();
//		organization.setId(orgId);
//		return organization;
//	}
}