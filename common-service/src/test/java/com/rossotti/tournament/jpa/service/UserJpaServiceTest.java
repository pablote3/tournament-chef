package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.exception.CustomException;
import com.rossotti.tournament.jpa.enumeration.UserStatus;
import com.rossotti.tournament.jpa.enumeration.UserType;
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
	public void create_EmailIsMandatory_Empty() {
		CustomException exception = assertThrows(CustomException.class, () -> {
			userJpaService.save(UserRepositoryTest.createMockUser(1L, "", "Bonetti", "Super3"));
		});
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("Email is mandatory", exception.getError().getErrorMessage());
	}

	@Test
	public void create_LastNameIsMandatory_Null() {
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

	@Test
	public void update_Updated() {
		User updateUser = userJpaService.findByOrganizationNameAndUserEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
		updateUser.setUserStatus(UserStatus.Active);
		userJpaService.save(updateUser);
		User findUser = userJpaService.findByOrganizationNameAndUserEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
		Assert.assertEquals(UserStatus.Active, findUser.getUserStatus());
	}

	@Test
	public void update_EmailIsMandatory_Empty() {
		User updateUser = userJpaService.findByOrganizationNameAndUserEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
		updateUser.setUserStatus(UserStatus.Active);
		updateUser.setEmail("");
		CustomException exception = assertThrows(CustomException.class, () -> {
			userJpaService.save(updateUser);
		});
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("Email is mandatory", exception.getError().getErrorMessage());
	}

	@Test
	public void update_LastNameIsMandatory_Null() {
		User updateUser = userJpaService.findByOrganizationNameAndUserEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
		updateUser.setUserStatus(UserStatus.Active);
		updateUser.setLastName(null);
		CustomException exception = assertThrows(CustomException.class, () -> {
			userJpaService.save(updateUser);
		});
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("LastName is mandatory", exception.getError().getErrorMessage());
	}

	@Test
	public void update_PasswordInvalidFormat() {
		User updateUser = userJpaService.findByOrganizationNameAndUserEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
		updateUser.setUserStatus(UserStatus.Active);
		updateUser.setPassword("Sup");
		CustomException exception = assertThrows(CustomException.class, () -> {
			userJpaService.save(updateUser);
		});
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("Password invalid format", exception.getError().getErrorMessage());
	}

	@Test
	public void update_EmailInvalidFormat() {
		User updateUser = userJpaService.findByOrganizationNameAndUserEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
		updateUser.setEmail("alessia.piazza_telecomitalia.com");
		updateUser.setUserStatus(UserStatus.Active);
		CustomException exception = assertThrows(CustomException.class, () -> {
			userJpaService.save(updateUser);
		});
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("Email invalid format", exception.getError().getErrorMessage());
	}

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
