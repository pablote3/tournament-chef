package com.rossotti.tournament.jpa;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.jpa.model.User;
import com.rossotti.tournament.jpa.model.User.UserStatus;
import com.rossotti.tournament.jpa.model.User.UserType;
import com.rossotti.tournament.jpa.repository.UserRepository;
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
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void findById() {
		User user = userRepository.findById(1L);
		Assert.assertEquals("valentina.giacinti@telecomitalia.com", (user.getEmail()));
		Assert.assertEquals(UserType.Administrator, user.getUserType());
		Assert.assertEquals(UserStatus.Active, user.getUserStatus());
		Assert.assertEquals("Giacinti", user.getLastName());
		Assert.assertEquals("Valentina", user.getFirstName());
		Assert.assertEquals("valentina.giacinti@telecomitalia.com", user.getEmail());
		Assert.assertEquals("Rossonere", user.getPassword());
		Assert.assertEquals(LocalDateTime.of(2020, 1, 16, 20, 0), user.getCreateTs());
		Assert.assertEquals(LocalDateTime.of(2020, 1, 19, 20, 0), user.getUpdateTs());
//		Assert.assertTrue(team.getStandings().size() >= 1);
	}

	@Test
	public void findAll() {
		List<User> users = userRepository.findAll();
		Assert.assertTrue(users.size() >= 4);
	}

	@Test
	public void findByEmail_Found() {
		User user = userRepository.findByEmail("valentina.giacinti@telecomitalia.com");
		Assert.assertEquals("Giacinti", user.getLastName());
	}

	@Test
	public void findByEmail_NotFound() {
		User user = userRepository.findByEmail("saratamborini@euro.com");
		Assert.assertNull(user);
	}

	@Test
	public void create() {
		userRepository.save(createMockUser("amserturini@gmail.com", "Serturini"));
		User user = userRepository.findByEmail("amserturini@gmail.com");
		Assert.assertEquals("Serturini", user.getLastName());
	}

	@Test(expected= DataIntegrityViolationException.class)
	public void create_MissingRequiredData() {
		userRepository.save(createMockUser("claudia.ciccotti@hotmailcom", null));
	}

	@Test(expected= DataIntegrityViolationException.class)
	public void create_Duplicate() {
		userRepository.save(createMockUser("valentina.giacinti@telecomitalia.com", "Giacinti"));
	}

//	@Test
//	public void update() {
//		userRepository.save(updateMockUser("Fiorentina FC", LocalDate.of(2012, 1, 15), "Mauro"));
//		User user = userRepository.findByUserNameAndAsOfDate("Fiorentina FC", LocalDate.of(2012, 1, 15));
//		Assert.assertEquals("Mauro", user.getContactLastName());
//		Assert.assertEquals("Ilaria", user.getContactFirstName());
//	}
//
//	@Test(expected= DataIntegrityViolationException.class)
//	public void update_MissingRequiredData() {
//		userRepository.save(updateMockUser("Fiorentina FC", LocalDate.of(2012, 1, 15), null));
//	}
//
//	@Test
//	public void delete() {
//		User user = userRepository.findByUserNameAndAsOfDate("US Sassuolo", LocalDate.of(2012, 1, 15));
//		if (user != null) {
//			userRepository.deleteById(user.getId());
//		}
//		else {
//			Assert.fail("Unable to find record to delete");
//		}
//		User findOrg = userRepository.findByUserNameAndAsOfDate("US Sassuolo", LocalDate.of(2012, 1, 15));
//		Assert.assertNull(findOrg);
//	}

	private User createMockUser(String email, String lastName) {
		User user = new User();
		user.setEmail(email);
		user.setUserType(UserType.Administrator);
		user.setUserStatus(UserStatus.Active);
		user.setLastName(lastName);
		user.setFirstName("Annamaria");
		user.setPassword("superpass");
		user.setCreateTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		user.setUpdateTs(LocalDateTime.of(2019, 10, 27, 20, 30));
		return user;
	}

//	private User updateMockUser(String orgName, LocalDate asOfDate, String contactLastName) {
//		User user = userRepository.findByUserNameAndAsOfDate(orgName, asOfDate);
//		user.setContactLastName(contactLastName);
//		user.setContactFirstName("Ilaria");
//		return user;
//	}
}