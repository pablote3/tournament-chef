package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.exception.CustomException;
import com.rossotti.tournament.jpa.enumeration.UserStatus;
import com.rossotti.tournament.jpa.model.User;
import com.rossotti.tournament.jpa.repository.UserRepositoryTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolationException;
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
	public void getById_Found() {
		User user = userJpaService.getById(1L);
		Assert.assertEquals("Giacinti", user.getLastName());
		Assert.assertEquals("FC Juventes", user.getUserOrganization().get(0).getOrganization().getOrganizationName());
	}

	@Test
	public void getById_NotFound() {
		Assert.assertNull(userJpaService.getById(31L));
	}

	@Test
	public void listAll() {
		List<User> users = (List<User>) userJpaService.listAll();
		Assert.assertTrue(users.size() >= 4);
	}

	@Test
	public void findByEmail_Found() {
		User user = userJpaService.findByEmail("valentina.giacinti@telecomitalia.com");
		Assert.assertEquals("Giacinti", user.getLastName());
		Assert.assertEquals("FC Juventes", user.getUserOrganization().get(0).getOrganization().getOrganizationName());
	}

	@Test
	public void findByEmail_NotFound() {
		Assert.assertNull(userJpaService.findByEmail("valentina.giacinti@yahoo.com"));
	}

	@Test
	public void create_Created() {
		userJpaService.save(UserRepositoryTest.createMockUser("bonetti.tatiana@hotmail.com", "Bonetti", "Super3"));
		User findUser = userJpaService.findByEmail("bonetti.tatiana@hotmail.com");
		Assert.assertEquals("Super3", findUser.getPassword());
	}

	@Test
	public void create_EmailIsMandatory_Empty() {
		ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userJpaService.save(UserRepositoryTest.createMockUser("", "Bonetti", "Super3")));
		Assert.assertTrue(exception.getMessage().contains("Email is mandatory"));
	}

	@Test
	public void create_LastNameIsMandatory_Null() {
		TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> userJpaService.save(UserRepositoryTest.createMockUser("bonetti.tatiana@hotmail.com", null, "Super3")));
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("LastName is mandatory"));
	}

	@Test
	public void create_EmailInvalidFormat() {
		ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userJpaService.save(UserRepositoryTest.createMockUser("bonetti.tatiana.hotmail.com", "Bonetti", "Super3")));
		Assert.assertTrue(exception.getMessage().contains("Email invalid format"));
	}

	@Test
	public void create_PasswordInvalidFormat() {
		ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> userJpaService.save(UserRepositoryTest.createMockUser("bonetti.tatiana@hotmail.com", "Bonetti", "Sup")));
		Assert.assertTrue(exception.getMessage().contains("Password invalid format"));
	}

	@Test
	public void update_Updated() {
		User updateUser = userJpaService.findByEmail("alessia.piazza@telecomitalia.com");
		updateUser.setUserStatus(UserStatus.Active);
		userJpaService.save(updateUser);
		User findUser = userJpaService.findByEmail("alessia.piazza@telecomitalia.com");
		Assert.assertEquals(UserStatus.Active, findUser.getUserStatus());
	}

	@Test
	public void update_EmailIsMandatory_Empty() {
		User updateUser = userJpaService.findByEmail("alessia.piazza@telecomitalia.com");
		updateUser.setUserStatus(UserStatus.Active);
		updateUser.setEmail("");
		TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> userJpaService.save(updateUser));
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("Email is mandatory"));
	}

	@Test
	public void update_LastNameIsMandatory_Null() {
		User updateUser = userJpaService.findByEmail("alessia.piazza@telecomitalia.com");
		updateUser.setUserStatus(UserStatus.Active);
		updateUser.setLastName(null);
		TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> userJpaService.save(updateUser));
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("LastName is mandatory"));
	}

	@Test
	public void update_PasswordInvalidFormat() {
		User updateUser = userJpaService.findByEmail("alessia.piazza@telecomitalia.com");
		updateUser.setUserStatus(UserStatus.Active);
		updateUser.setPassword("Sup");
		TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> userJpaService.save(updateUser));
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("Password invalid format"));
	}

	@Test
	public void update_EmailInvalidFormat() {
		User updateUser = userJpaService.findByEmail("alessia.piazza@telecomitalia.com");
		updateUser.setEmail("alessia.piazza_telecomitalia.com");
		updateUser.setUserStatus(UserStatus.Active);
		TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> userJpaService.save(updateUser));
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("Email invalid format"));
	}

	@Test
	public void delete_Deleted() {
		userJpaService.delete(7L);
		Assert.assertNull(userJpaService.getById(7L));
	}

	@Test
	public void delete_NotFound() {
		CustomException exception = assertThrows(CustomException.class, () -> userJpaService.delete(21L));
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
		Assert.assertEquals("Server error when trying to find record for id of {}", exception.getError().getErrorMessage());
		Assert.assertEquals("MSG_VAL_0012", exception.getError().getError());
	}
}