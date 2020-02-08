package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.exception.CustomException;
import com.rossotti.tournament.jpa.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.rossotti.tournament.config.ServiceConfig.class)
public class UserJpaServiceTest {

	private UserJpaService userJpaService;

	@Autowired
	public void setUserJpaService(UserJpaService userJpaService) {
		this.userJpaService = userJpaService;
	}

	@Test
	public void getById() {
		User user = userJpaService.getById(1L);
		Assert.assertEquals("Giacinti", user.getLastName());
		Assert.assertEquals("FC Juventes", user.getOrganization().getOrganizationName());
	}

	@Test
	public void listAll() {
		@SuppressWarnings("unchecked") List<User> users = (List<User>) userJpaService.listAll();
		Assert.assertTrue(users.size() >= 4);
	}

	@Test
	public void findByOrganizationNameAndUserEmail_Found() {
		User user = userJpaService.findByOrganizationNameAndUserEmail("FC Juventes", "valentina.giacinti@telecomitalia.com");
		Assert.assertEquals("Giacinti", user.getLastName());
		Assert.assertEquals("FC Juventes", user.getOrganization().getOrganizationName());
	}

	@Test
	public void findByOrganizationNameAndUserEmail_NotFound() {
		Exception exception = assertThrows(CustomException.class, () -> {
			userJpaService.findByOrganizationNameAndUserEmail("Juventes", "valentina.giacinti@telecomitalia.com");
		});
		Assert.assertTrue(exception.getMessage().contains("MSG_VAL_0012"));
	}

	@Test
	public void findByEmail_Found() {
		List<User> users = userJpaService.findByEmail("valentina.giacinti@telecomitalia.com");
		Assert.assertEquals(2, users.size());
	}

	@Test
	public void findByEmail_NotFound() {
		List<User> users = userJpaService.findByEmail("saratamborini@euro.com");
		Assert.assertEquals(0, users.size());
	}

//	@Test
//	public void create_Created_AsOfDate() {
//		Team createTeam = userJpaService.create(TeamRepositoryTest.createMockTeam("sacramento-hornets", LocalDate.of(2012, 7, 1), LocalDate.of(9999, 12, 31), "Sacramento Hornets"));
//		Team findTeam = userJpaService.findByTeamKeyAndAsOfDate("sacramento-hornets", LocalDate.of(2012, 7, 1));
//		Assert.assertTrue(createTeam.isCreated());
//		Assert.assertEquals("Sacramento Hornets", findTeam.getFullName());
//	}
//
//	@Test
//	public void create_Created_DateRange() {
//		Team createTeam = userJpaService.create(TeamRepositoryTest.createMockTeam("sacramento-rivercats", LocalDate.of(2006, 7, 1), LocalDate.of(2012, 7, 2), "Sacramento Rivercats"));
//		Team findTeam = userJpaService.findByTeamKeyAndAsOfDate("sacramento-rivercats", LocalDate.of(2006, 7, 1));
//		Assert.assertTrue(createTeam.isCreated());
//		Assert.assertEquals("Sacramento Rivercats", findTeam.getFullName());
//	}
//
//	@Test
//	public void create_OverlappingDates() {
//		Team createTeam = userJpaService.create(TeamRepositoryTest.createMockTeam("cleveland-rebels", LocalDate.of(2010, 7, 1), LocalDate.of(2010, 7, 1), "Cleveland Rebels"));
//		Assert.assertTrue(createTeam.isFound());
//	}
//
//	@Test(expected= DataIntegrityViolationException.class)
//	public void create_MissingRequiredData() {
//		userJpaService.create(TeamRepositoryTest.createMockTeam("chavo-del-ocho", LocalDate.of(2010, 7, 1), LocalDate.of(2010, 7, 1), null));
//	}
//
//	@Test
//	public void update_Updated() {
//		Team updateTeam = userJpaService.update(TeamRepositoryTest.createMockTeam("st-louis-bomber's", LocalDate.of(2009, 7, 1), LocalDate.of(9999, 12, 31), "St. Louis Bombier's"));
//		Team team = userJpaService.findByTeamKeyAndAsOfDate("st-louis-bomber's", LocalDate.of(9999, 12, 31));
//		Assert.assertEquals("St. Louis Bombier's", team.getFullName());
//		Assert.assertTrue(updateTeam.isUpdated());
//	}
//
//	@Test
//	public void update_NotFound() {
//		Team team = userJpaService.update(TeamRepositoryTest.createMockTeam("st-louis-bomb's", LocalDate.of(2009, 7, 1), LocalDate.of(2010, 7, 1), "St. Louis Bombier's"));
//		Assert.assertTrue(team.isNotFound());
//	}
//
//	@Test(expected= DataIntegrityViolationException.class)
//	public void update_MissingRequiredData() {
//		userJpaService.update(TeamRepositoryTest.createMockTeam("st-louis-bomber's", LocalDate.of(2009, 7, 1), LocalDate.of(2010, 6, 30), null));
//	}
//
//	@Test
//	public void delete_Deleted() {
//		Team deleteTeam = userJpaService.delete(7L);
//		Team findTeam = userJpaService.getById(7L);
//		Assert.assertNull(findTeam);
//		Assert.assertTrue(deleteTeam.isDeleted());
//	}
//
//	@Test
//	public void delete_NotFound() {
//		Team deleteTeam = userJpaService.delete(101L);
//		Assert.assertTrue(deleteTeam.isNotFound());
//	}
}
