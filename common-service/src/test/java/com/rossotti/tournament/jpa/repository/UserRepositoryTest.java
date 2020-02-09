package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.jpa.enumeration.UserStatus;
import com.rossotti.tournament.jpa.enumeration.UserType;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.model.User;
import com.rossotti.tournament.jpa.repository.UserRepository;
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
		Assert.assertEquals("FC Juventes", user.getOrganization().getOrganizationName());
		Assert.assertEquals(5, user.getOrganization().getUsers().size());
		Assert.assertEquals(2, user.getOrganization().getTeams().size());
		Assert.assertEquals(2, user.getOrganization().getLocations().size());
	}

	@Test
	public void findById_NotFound() {
		Assert.assertNull(userRepository.findById(10L));
	}

	@Test
	public void findAll() {
		List<User> users = userRepository.findAll();
		Assert.assertTrue(users.size() >= 4);
	}

	@Test
	public void findByEmail_Found() {
		List<User> users = userRepository.findByEmail("valentina.giacinti@telecomitalia.com");
		Assert.assertEquals(2, users.size());
	}

	@Test
	public void findByEmail_NotFound() {
		List<User> users = userRepository.findByEmail("saratamborini@euro.com");
		Assert.assertEquals(0, users.size());
	}

	@Test
	public void findByOrganizationNameAndEmail_Found() {
		User user = userRepository.findByOrganizationNameAndUserEmail("FC Juventes", "valentina.giacinti@telecomitalia.com");
		Assert.assertEquals("Giacinti", user.getLastName());
	}

	@Test
	public void findByOrganizationNameAndEmail_NotFound() {
		Assert.assertNull(userRepository.findByOrganizationNameAndUserEmail("FC Juventes", "saratamborini@euro.com"));
	}

	@Test
	public void create() {
		userRepository.save(createMockUser(1L, "amserturini@gmail.com", "Serturini", "Brescia3"));
		User user = userRepository.findByOrganizationNameAndUserEmail("FC Juventes", "amserturini@gmail.com");
		Assert.assertEquals("Serturini", user.getLastName());
	}

	@Test
	public void create_ConstraintViolation_EmailMissing() {
		Exception exception = assertThrows(ConstraintViolationException.class, () -> {
			userRepository.save(createMockUser(1L, "", "Bonfantini", "Brescia3"));
		});
		Assert.assertTrue(exception.getMessage().contains("Email is mandatory"));
	}

	@Test
	public void create_ConstraintViolation_EmailInvalid() {
		Exception exception = assertThrows(ConstraintViolationException.class, () -> {
			userRepository.save(createMockUser(1L, "bonfantini.com", "Bonfantini", "Brescia3"));
		});
		Assert.assertTrue(exception.getMessage().contains("Email invalid format"));
	}

	@Test
	public void create_ConstraintViolation_PasswordInvalid_Short() {
		Exception exception = assertThrows(ConstraintViolationException.class, () -> {
			userRepository.save(createMockUser(1L, "angelica.soffia@gmail.com", "Soffia", "Br3"));
		});
		Assert.assertTrue(exception.getMessage().contains("Password invalid format"));
	}

	@Test
	public void create_Duplicate() {
		Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
			userRepository.save(createMockUser(1L, "valentina.giacinti@telecomitalia.com", "Giacinti", "Brescia3"));
		});
		Assert.assertTrue(exception.getMessage().contains("could not execute statement"));
	}

	@Test
	public void update() {
		userRepository.save(updateMockUser("FC Juventes", "valentina.bergamaschi@hotmail.com", "Valencia"));
		User user = userRepository.findByOrganizationNameAndUserEmail("FC Juventes", "valentina.bergamaschi@hotmail.com");
		Assert.assertEquals("Valencia", user.getLastName());
	}

	@Test
	public void update_TransactionSystemException_LastNameMissing() {
		Exception exception = assertThrows(TransactionSystemException.class, () -> {
			userRepository.save(updateMockUser("FC Juventes", "alessia.piazza@telecomitalia.com", null));
		});
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("LastName is mandatory"));
	}

	@Test
	public void delete() {
		User user = userRepository.findByOrganizationNameAndUserEmail("FC Juventes", "martina.capelli@telecomitalia.com");
		if (user != null) {
			userRepository.deleteById(user.getId());
		}
		else {
			Assert.fail("Unable to find record to delete");
		}
		Assert.assertNull(userRepository.findByOrganizationNameAndUserEmail("FC Juventes", "martina.capelli@telecomitalia.com"));
	}

	public static User createMockUser(Long orgId, String email, String lastName, String password) {
		User user = new User();
		user.setOrganization(createMockOrganization(orgId));
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

	private User updateMockUser(String orgName, String email, String lastName) {
		User user = userRepository.findByOrganizationNameAndUserEmail(orgName, email);
		user.setLastName(lastName);
		return user;
	}

	private static Organization createMockOrganization(Long orgId) {
		Organization organization = new Organization();
		organization.setId(orgId);
		return organization;
	}
}