package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.exception.CustomException;
import com.rossotti.tournament.jpa.model.User;
import com.rossotti.tournament.jpa.repository.UserRepositoryTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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
		Assert.assertNull(userJpaService.findByOrganizationNameAndUserEmail("Juventes", "valentina.giacinti@telecomitalia.com"));
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

	@Test
	public void create_Created() {
		userJpaService.save(UserRepositoryTest.createMockUser(1L, "bonetti.tatiana@hotmail.com", "Bonetti", "Super3"));
		User findUser = userJpaService.findByOrganizationNameAndUserEmail("FC Juventes", "bonetti.tatiana@hotmail.com");
		Assert.assertEquals("Super3", findUser.getPassword());
	}

	@Test
	public void create_EmailIsMandatory() {
		CustomException exception = assertThrows(CustomException.class, () -> {
			userJpaService.save(UserRepositoryTest.createMockUser(1L, "", "Bonetti", "Super3"));
		});
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("Email is mandatory", exception.getError().getErrorMessage());
	}

	@Test
	public void create_LastNameIsMandatory() {
		CustomException exception = assertThrows(CustomException.class, () -> {
			userJpaService.save(UserRepositoryTest.createMockUser(1L, "bonetti.tatiana@hotmail.com", null, "Super3"));
		});
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("LastName is mandatory", exception.getError().getErrorMessage());
	}

	@Test
	public void create_EmailInvalidFormat() {
		CustomException exception = assertThrows(CustomException.class, () -> {
			userJpaService.save(UserRepositoryTest.createMockUser(1L, "bonetti.tatiana.hotmail.com", "Bonetti", "Super3"));
		});
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("Email invalid format", exception.getError().getErrorMessage());
	}

	@Test
	public void create_PasswordInvalidFormat() {
		CustomException exception = assertThrows(CustomException.class, () -> {
			userJpaService.save(UserRepositoryTest.createMockUser(1L, "bonetti.tatiana@hotmail.com", "Bonetti", "Sup"));
		});
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("Password invalid format", exception.getError().getErrorMessage());
	}

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
