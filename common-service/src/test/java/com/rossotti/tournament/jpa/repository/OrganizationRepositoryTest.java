package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.model.*;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles(profiles = "development")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceConfig.class)
public class OrganizationRepositoryTest {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Test
	public void findById_Found() {
		Organization organization = organizationRepository.findById(1L);
		Assert.assertEquals("FC Juventes", organization.getOrganizationName());
		Assert.assertEquals(LocalDate.of(2016, 2, 21), organization.getStartDate());
		Assert.assertEquals(LocalDate.of(9999, 12, 31), organization.getEndDate());
		Assert.assertEquals("Via Stupinigi 182", organization.getAddress1());
		Assert.assertEquals("Suite 456", organization.getAddress2());
		Assert.assertEquals("Vinovo", organization.getCity());
		Assert.assertEquals("Piedmont", organization.getState());
		Assert.assertEquals("10048", organization.getZipCode());
		Assert.assertEquals("Italy", organization.getCountry());
		Assert.assertEquals("Girelli", organization.getContactLastName());
		Assert.assertEquals("Cristiana", organization.getContactFirstName());
		Assert.assertEquals("cristiana.girelli@gmail.com", organization.getContactEmail());
		Assert.assertEquals("9093381808", organization.getContactPhone());
		Assert.assertEquals(LocalDateTime.of(2015, 10, 27, 20, 0), organization.getCreateTs());
		Assert.assertEquals(LocalDateTime.of(2020, 1, 18, 20, 0), organization.getLupdTs());
		Assert.assertEquals(4, organization.getUserOrganizations().size());
		Assert.assertEquals(4, organization.getOrganizationTeams().size());
		Assert.assertEquals(2, organization.getOrganizationLocations().size());
	}

	@Test
	public void findById_NotFound() {
		Assert.assertNull(organizationRepository.findById(21L));
	}

	@Test
	public void findAll() {
		List<Organization> organizations = organizationRepository.findAll();
		Assert.assertTrue(organizations.size() >= 4);
	}

	@Test
	public void findByOrgName_Found() {
		List<Organization> organizations = organizationRepository.findByOrganizationName("FC Juventes");
		Assert.assertEquals(3, organizations.size());
	}

	@Test
	public void findByOrgName_NotFound() {
		List<Organization> organizations = organizationRepository.findByOrganizationName("AC Milan");
		Assert.assertEquals(0, organizations.size());
	}

	@Test
	public void findByOrgNameAndAsOfDate_Found_EqualStartDate() {
		Organization organization = organizationRepository.findByOrganizationNameAsOfDate("FC Juventes", LocalDate.of(2010, 1, 15));
		Assert.assertEquals("barbara.bonansea@gmail.com", organization.getContactEmail());
	}

	@Test
	public void findByOrgNameAndAsOfDate_Found_EqualEndDate() {
		Organization organization = organizationRepository.findByOrganizationNameAsOfDate("FC Juventes", LocalDate.of(9999, 12, 31));
		Assert.assertEquals("cristiana.girelli@gmail.com", organization.getContactEmail());
	}

	@Test
	public void findByOrgNameAndAsOfDate_Found_BetweenStartAndEndDate() {
		Organization organization = organizationRepository.findByOrganizationNameAsOfDate("FC Juventes", LocalDate.of(2012, 10, 29));
		Assert.assertEquals("barbara.bonansea@gmail.com", organization.getContactEmail());
	}

	@Test
	public void findByOrgNameAndAsOfDate_NotFound_BeforeStartDate() {
		Assert.assertNull(organizationRepository.findByOrganizationNameAsOfDate("FC Juventes", LocalDate.of(2010, 1, 14)));
	}

	@Test
	public void findByOrgNameAndAsOfDate_NotFound_AfterEndDate() {
		Assert.assertNull(organizationRepository.findByOrganizationNameAsOfDate("FC Juventes", LocalDate.of(10000, 1, 1)));
	}

	@Test
	public void findByOrgNameStartDateEndDate_Found_EqualStartDate() {
		Organization organization = organizationRepository.findByOrganizationNameStartDateEndDate("FC Juventes", LocalDate.of(2010, 1, 15), LocalDate.of(2011, 12, 31));
		Assert.assertEquals("barbara.bonansea@gmail.com", organization.getContactEmail());
	}

	@Test
	public void findByOrgNameStartDateEndDate_Found_EqualEndDate() {
		Organization organization = organizationRepository.findByOrganizationNameStartDateEndDate("FC Juventes", LocalDate.of(2010, 12, 31), LocalDate.of(2016, 2, 20));
		Assert.assertEquals("barbara.bonansea@gmail.com", organization.getContactEmail());
	}

	@Test
	public void findByOrgNameStartDateEndDate_Found_EqualStartDateEndDate() {
		Organization organization = organizationRepository.findByOrganizationNameStartDateEndDate("FC Juventes", LocalDate.of(2010, 1, 15), LocalDate.of(2016, 2, 20));
		Assert.assertEquals("barbara.bonansea@gmail.com", organization.getContactEmail());
	}

	@Test
	public void findByOrgNameStateDateEndDate_Found_BetweenStartAndEndDate() {
		Organization organization = organizationRepository.findByOrganizationNameStartDateEndDate("FC Juventes", LocalDate.of(2012, 10, 29), LocalDate.of(2012, 12, 29));
		Assert.assertEquals("barbara.bonansea@gmail.com", organization.getContactEmail());
	}

	@Test
	public void findByOrgNameStartDateEndDate_NotFound_BeforeStartDate() {
		Assert.assertNull(organizationRepository.findByOrganizationNameStartDateEndDate("FC Juventes", LocalDate.of(2010, 1, 14), LocalDate.of(2010, 1, 20)));
	}

	@Test
	public void findByOrgNameStartDateEndDate_NotFound_AfterEndDate() {
		Assert.assertNull(organizationRepository.findByOrganizationNameStartDateEndDate("FC Juventes", LocalDate.of(2010, 1, 20), LocalDate.of(2016, 2, 21)));
	}

	@Test
	public void findByOrgNameAndAsOfDate_NotFound_OrganizationName() {
		Assert.assertNull(organizationRepository.findByOrganizationNameAsOfDate("Orobica", LocalDate.of(9999, 12, 31)));
	}

	@Test
	public void create() {
		organizationRepository.save(createMockOrganization("AS Roma", LocalDate.of(2010, 1, 11), LocalDate.of(9999, 12, 31), "Giugliano", "manuela.giugliano@gmail.com"));
		Organization organization = organizationRepository.findByOrganizationNameAsOfDate("AS Roma", LocalDate.of(2010, 1, 21));
		Assert.assertEquals(1, organization.getUserOrganizations().size());
		Assert.assertEquals(1, organization.getOrganizationTeams().size());
		Assert.assertEquals(1, organization.getOrganizationLocations().size());
		Assert.assertEquals("Giugliano", organization.getContactLastName());
		Assert.assertEquals("manuela.giugliano@gmail.com", organization.getContactEmail());
	}

	@Test
	public void create_ConstraintViolation_ContactLastNameMissing() {
		Exception exception = assertThrows(ConstraintViolationException.class, () -> organizationRepository.save(createMockOrganization("AS Roma", LocalDate.of(2010, 1, 11), LocalDate.of(9999, 12, 31), null, "manuela.giugliano@gmail.com")));
		Assert.assertTrue(exception.getMessage().contains("ContactLastName is mandatory"));
	}

	@Test
	public void create_Duplicate() {
		Exception exception = assertThrows(DataIntegrityViolationException.class, () -> organizationRepository.save(createMockOrganization("FC Juventes", LocalDate.of(2010, 1, 15), LocalDate.of(2016, 2, 20), "Bonansea", "barbara.bonansea@gmail.com")));
		Assert.assertTrue(exception.getMessage().contains("could not execute statement"));
	}

	@Test
	public void update() {
		Organization organization = organizationRepository.findByOrganizationNameAsOfDate("Fiorentina FC", LocalDate.of(2012, 1, 15));
		Assert.assertEquals("Bonetti", organization.getContactLastName());
		organization.setContactLastName("Mauro");
		organizationRepository.save(organization);
		Organization findOrganization = organizationRepository.findByOrganizationNameAsOfDate("Fiorentina FC", LocalDate.of(2012, 1, 15));
		Assert.assertEquals("Mauro", findOrganization.getContactLastName());
	}

	@Test
	public void update_TransactionSystemException_ContactLastNameMissing() {
		Organization organization = organizationRepository.findByOrganizationNameAsOfDate("Fiorentina FC", LocalDate.of(2012, 1, 15));
		organization.setContactLastName(null);
		Exception exception = assertThrows(TransactionSystemException.class, () -> organizationRepository.save(organization));
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("ContactLastName is mandatory"));
	}

	@Test
	public void delete() {
		Organization organization = organizationRepository.findByOrganizationNameAsOfDate("Lazio ARL", LocalDate.of(2016, 1, 1));
		if (organization != null) {
			organizationRepository.deleteById(organization.getId());
		}
		else {
			Assert.fail("Unable to find record to delete");
		}
		Organization findOrg = organizationRepository.findByOrganizationNameAsOfDate("Lazio ARL", LocalDate.of(2016, 1, 1));
		Assert.assertNull(findOrg);
	}

	public static Organization createMockOrganization(String orgName, LocalDate startDate, LocalDate endDate, String contactLastName, String contactEmail) {
		Organization organization = new Organization();
		organization.setOrganizationName(orgName);
		organization.setStartDate(startDate);
		organization.setEndDate(endDate);
		organization.setAddress1("Via delle Tre Fontane 5");
		organization.setAddress2("Suite 789");
		organization.setCity("Roma");
		organization.setState("Lazio");
		organization.setZipCode("00144");
		organization.setCountry("Italy");
		organization.setContactLastName(contactLastName);
		organization.setContactFirstName("Manuela");
		organization.setContactEmail(contactEmail);
		organization.setContactPhone("390665951");
		organization.getUserOrganizations().add(createMockUserOrganization(organization));
		organization.getOrganizationTeams().add(createMockOrganizationTeam(organization));
		organization.getOrganizationLocations().add(createMockOrganizationLocation(organization));
		organization.setCreateTs(LocalDateTime.of(2015, 10, 27, 20, 30));
		organization.setLupdTs(LocalDateTime.of(2015, 10, 27, 20, 30));
		organization.setLupdUserId(1L);
		return organization;
	}

	private static UserOrganization createMockUserOrganization(Organization organization) {
		UserOrganization userOrganization = new UserOrganization();
		userOrganization.setOrganization(organization);
		userOrganization.setUser(createMockUser(userOrganization));
		return userOrganization;
	}

	private static User createMockUser(UserOrganization userOrganization) {
		User user = new User();
		user.setId(5L);
		user.getUserOrganization().add(userOrganization);
		return user;
	}

	private static OrganizationTeam createMockOrganizationTeam(Organization organization) {
		OrganizationTeam organizationTeam = new OrganizationTeam();
		organizationTeam.setOrganization(organization);
		organizationTeam.setTeamName("Cuneo");
		organizationTeam.setCity("Cuneo");
		organizationTeam.setState("Piedmont");
		organizationTeam.setCountry("Italy");
		organizationTeam.setZipCode("12100");
		organizationTeam.setLupdUserId(6L);
		return organizationTeam;
	}

	private static OrganizationLocation createMockOrganizationLocation(Organization organization) {
		OrganizationLocation organizationLocation = new OrganizationLocation();
		organizationLocation.setOrganization(organization);
		organizationLocation.setLocationName("Campo Sportivo CSM Soprani");
		organizationLocation.setAddress1("Via delle Tre Fontane");
		organizationLocation.setAddress2("5");
		organizationLocation.setCity("Roma");
		organizationLocation.setState("Lazio");
		organizationLocation.setCountry("Italy");
		organizationLocation.setZipCode("00144");
		organizationLocation.setLupdUserId(4L);
		return organizationLocation;
	}
}