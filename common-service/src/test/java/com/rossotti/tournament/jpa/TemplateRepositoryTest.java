package com.rossotti.tournament.jpa;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.jpa.enumeration.GroupPlay;
import com.rossotti.tournament.jpa.enumeration.UserStatus;
import com.rossotti.tournament.jpa.enumeration.UserType;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.model.Template;
import com.rossotti.tournament.jpa.model.User;
import com.rossotti.tournament.jpa.repository.TemplateRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@ActiveProfiles(profiles = "development")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceConfig.class)
public class TemplateRepositoryTest {

	@Autowired
	private TemplateRepository templateRepository;

	@Test
	public void findById() {
		Template template = templateRepository.findById(1L);
		Assert.assertEquals("4x4Pairing+Semis+Finals", template.getTemplateName());
		Assert.assertEquals((short)4, (short)template.getGridGroupRound1());
		Assert.assertEquals((short)4, (short)template.getGridTeamsRound1());
		Assert.assertEquals(GroupPlay.Pairing, template.getGroupPlay1());
		Assert.assertEquals((short)0, (short)template.getGroupPlayoffGamesRound1());
		Assert.assertEquals((short)0, (short)template.getGridGroupRound2());
		Assert.assertEquals((short)0, (short)template.getGridTeamsRound2());
		Assert.assertEquals(GroupPlay.Pairing, template.getGroupPlay2());
		Assert.assertEquals((short)0, (short)template.getGroupPlayoffGamesRound2());
		Assert.assertEquals((short)8, (short)template.getQuarterFinalGames());
		Assert.assertEquals((short)8, (short)template.getSemiFinalGames());
//		Assert.assertEquals(5, user.getOrganization().getUsers().size());
//		Assert.assertEquals(2, user.getOrganization().getTeams().size());
//		Assert.assertEquals(2, user.getOrganization().getLocations().size());
	}

//	@Test
//	public void findAll() {
//		List<User> users = userRepository.findAll();
//		Assert.assertTrue(users.size() >= 4);
//	}
//
//	@Test
//	public void findByEmail_Found() {
//		List<User> users = userRepository.findByEmail("valentina.giacinti@telecomitalia.com");
//		Assert.assertEquals(2, users.size());
//	}
//
//	@Test
//	public void findByEmail_NotFound() {
//		List<User> users = userRepository.findByEmail("saratamborini@euro.com");
//		Assert.assertEquals(0, users.size());
//	}
//
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
//
//	@Test
//	public void create() {
//		userRepository.save(createMockUser(1L, "amserturini@gmail.com", "Serturini"));
//		User user = userRepository.findByOrganizationNameAndUserEmail("FC Juventes", "amserturini@gmail.com");
//		Assert.assertEquals("Serturini", user.getLastName());
//	}
//
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