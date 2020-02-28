package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.jpa.enumeration.UserStatus;
import com.rossotti.tournament.jpa.enumeration.UserType;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.model.User;
import com.rossotti.tournament.jpa.model.UserOrganization;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles(profiles = "development")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceConfig.class)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void findById_Found() {
		User user = userRepository.findById(1L);
		Assert.assertEquals("valentina.giacinti@telecomitalia.com", (user.getEmail()));
		Assert.assertEquals(UserType.Administrator, user.getUserType());
		Assert.assertEquals(UserStatus.Active, user.getUserStatus());
		Assert.assertEquals("Giacinti", user.getLastName());
		Assert.assertEquals("Valentina", user.getFirstName());
		Assert.assertEquals("valentina.giacinti@telecomitalia.com", user.getEmail());
		Assert.assertEquals("Rosson1", user.getPassword());
		Assert.assertEquals(LocalDateTime.of(2020, 1, 16, 20, 0), user.getCreateTs());
		Assert.assertEquals(LocalDateTime.of(2020, 1, 19, 20, 0), user.getLupdTs());
		Assert.assertEquals(1, user.getLupdUserId().longValue());
		Assert.assertEquals("FC Juventes", user.getUserOrganization().get(0).getOrganization().getOrganizationName());
		Assert.assertEquals(1, user.getUserOrganization().size());
		Assert.assertEquals(2, user.getUserOrganization().get(0).getOrganization().getOrganizationTeams().size());
		Assert.assertEquals(2, user.getUserOrganization().get(0).getOrganization().getLocations().size());
	}

	@Test
	public void findById_NotFound() {
		Assert.assertNull(userRepository.findById(20L));
	}

	@Test
	public void findAll() {
		List<User> users = userRepository.findAll();
		Assert.assertTrue(users.size() >= 4);
	}

	@Test
	public void findByEmail_Found() {
		Assert.assertNotNull(userRepository.findByEmail("valentina.giacinti@telecomitalia.com"));
	}

	@Test
	public void findByEmail_NotFound() {
		Assert.assertNull(userRepository.findByEmail("saratamborini@euro.com"));
	}

	@Test
	public void create() {
		userRepository.save(createMockUser("amserturini@gmail.com", "Serturini", "Brescia3"));
		User user = userRepository.findByEmail("amserturini@gmail.com");
		Assert.assertEquals("Serturini", user.getLastName());
	}

	@Test
	public void create_ConstraintViolation_EmailMissing() {
		Exception exception = assertThrows(ConstraintViolationException.class, () -> userRepository.save(createMockUser("", "Bonfantini", "Brescia3")));
		Assert.assertTrue(exception.getMessage().contains("Email is mandatory"));
	}

	@Test
	public void create_ConstraintViolation_EmailInvalid() {
		Exception exception = assertThrows(ConstraintViolationException.class, () -> userRepository.save(createMockUser("bonfantini.com", "Bonfantini", "Brescia3")));
		Assert.assertTrue(exception.getMessage().contains("Email invalid format"));
	}

	@Test
	public void create_ConstraintViolation_PasswordInvalid_Short() {
		Exception exception = assertThrows(ConstraintViolationException.class, () -> userRepository.save(createMockUser("angelica.soffia@gmail.com", "Soffia", "Br3")));
		Assert.assertTrue(exception.getMessage().contains("Password invalid format"));
	}

	@Test
	public void create_Duplicate() {
		Exception exception = assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(createMockUser("valentina.giacinti@telecomitalia.com", "Giacinti", "Brescia3")));
		Assert.assertTrue(exception.getMessage().contains("could not execute statement"));
	}

	@Test
	public void update() {
		userRepository.save(updateMockUser("valentina.bergamaschi@hotmail.com", "Valencia"));
		User user = userRepository.findByEmail("valentina.bergamaschi@hotmail.com");
		Assert.assertEquals("Valencia", user.getLastName());
	}

	@Test
	public void update_TransactionSystemException_LastNameMissing() {
		Exception exception = assertThrows(TransactionSystemException.class, () -> userRepository.save(updateMockUser("alessia.piazza@telecomitalia.com", null)));
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("LastName is mandatory"));
	}

	@Test
	public void delete() {
		User user = userRepository.findByEmail("martina.capelli@telecomitalia.com");
		if (user != null) {
			userRepository.deleteById(user.getId());
		}
		else {
			Assert.fail("Unable to find record to delete");
		}
		Assert.assertNull(userRepository.findByEmail("martina.capelli@telecomitalia.com"));
	}

	public static User createMockUser(String email, String lastName, String password) {
		User user = new User();
		user.getUserOrganization().add(createMockUserOrganization(user));
		user.setEmail(email);
		user.setUserType(UserType.Administrator);
		user.setUserStatus(UserStatus.Active);
		user.setLastName(lastName);
		user.setFirstName("Annamaria");
		user.setPassword(password);
		user.setCreateTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		user.setLupdTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		user.setLupdUserId(4L);
		return user;
	}

	private User updateMockUser(String email, String lastName) {
		User user = userRepository.findByEmail(email);
		user.setLastName(lastName);
		return user;
	}

	private static UserOrganization createMockUserOrganization(User user) {
		UserOrganization userOrganization = new UserOrganization();
		userOrganization.setOrganization(createMockOrganization());
		userOrganization.setUser(user);
		return userOrganization;
	}

	private static Organization createMockOrganization() {
		Organization organization = new Organization();
		organization.setId(11L);
		return organization;
	}
}