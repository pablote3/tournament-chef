package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.jpa.enumeration.OrganizationStatus;
import com.rossotti.tournament.jpa.model.Organization;
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
		Assert.assertEquals(OrganizationStatus.Active, organization.getOrganizationStatus());
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
		Assert.assertEquals(5, organization.getUsers().size());
		Assert.assertEquals(2, organization.getTeams().size());
		Assert.assertEquals(2, organization.getLocations().size());
	}

	@Test
	public void findById_NotFound() {
		Assert.assertNull(organizationRepository.findById(11L));
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
		Organization organization = organizationRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2010, 1, 15));
		Assert.assertEquals(OrganizationStatus.Inactive, organization.getOrganizationStatus());
	}

	@Test
	public void findByOrgNameAndAsOfDate_Found_EqualEndDate() {
		Organization organization = organizationRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(9999, 12, 31));
		Assert.assertEquals(OrganizationStatus.Active, organization.getOrganizationStatus());
	}

	@Test
	public void findByOrgNameAndAsOfDate_Found_BetweenStartAndEndDate() {
		Organization organization = organizationRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2012, 10, 29));
		Assert.assertEquals(OrganizationStatus.Inactive, organization.getOrganizationStatus());
	}

	@Test
	public void findByOrgNameAndAsOfDate_NotFound_BeforeStartDate() {
		Assert.assertNull(organizationRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2010, 1, 14)));
	}

	@Test
	public void findByOrgNameAndAsOfDate_NotFound_AfterEndDate() {
		Assert.assertNull(organizationRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(10000, 1, 1)));
	}

	@Test
	public void findByOrgNameStartDateEndDate_Found_EqualStartDate() {
		Organization organization = organizationRepository.findByOrganizationNameStartDateEndDate("FC Juventes", LocalDate.of(2010, 1, 15), LocalDate.of(2011, 12, 31));
		Assert.assertEquals(OrganizationStatus.Inactive, organization.getOrganizationStatus());
	}

	@Test
	public void findByOrgNameStartDateEndDate_Found_EqualEndDate() {
		Organization organization = organizationRepository.findByOrganizationNameStartDateEndDate("FC Juventes", LocalDate.of(2010, 12, 31), LocalDate.of(2016, 2, 20));
		Assert.assertEquals(OrganizationStatus.Inactive, organization.getOrganizationStatus());
	}

	@Test
	public void findByOrgNameStartDateEndDate_Found_EqualStartDateEndDate() {
		Organization organization = organizationRepository.findByOrganizationNameStartDateEndDate("FC Juventes", LocalDate.of(2010, 1, 15), LocalDate.of(2016, 2, 20));
		Assert.assertEquals(OrganizationStatus.Inactive, organization.getOrganizationStatus());
	}

	@Test
	public void findByOrgNameStateDateEndDate_Found_BetweenStartAndEndDate() {
		Organization organization = organizationRepository.findByOrganizationNameStartDateEndDate("FC Juventes", LocalDate.of(2012, 10, 29), LocalDate.of(2012, 12, 29));
		Assert.assertEquals(OrganizationStatus.Inactive, organization.getOrganizationStatus());
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
		Assert.assertNull(organizationRepository.findByOrganizationNameAndAsOfDate("Orobica", LocalDate.of(9999, 12, 31)));
	}

	@Test
	public void create() {
		organizationRepository.save(createMockOrganization("AS Roma", LocalDate.of(2010, 1, 11), LocalDate.of(9999, 12, 31), "Giugliano", "manuela.giugliano@gmail.com"));
		Organization organization = organizationRepository.findByOrganizationNameAndAsOfDate("AS Roma", LocalDate.of(2010, 1, 21));
		Assert.assertEquals("Giugliano", organization.getContactLastName());
		Assert.assertEquals("Manuela", organization.getContactFirstName());
	}

	@Test
	public void create_ConstraintViolation_ContactLastNameMissing() {
		Exception exception = assertThrows(ConstraintViolationException.class, () -> {
			organizationRepository.save(createMockOrganization("AS Roma", LocalDate.of(2010, 1, 11), LocalDate.of(9999, 12, 31), null, "manuela.giugliano@gmail.com"));
		});
		Assert.assertTrue(exception.getMessage().contains("ContactLastName is mandatory"));
	}

	@Test
	public void create_Duplicate() {
		Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
			organizationRepository.save(createMockOrganization("FC Juventes", LocalDate.of(2010, 1, 15), LocalDate.of(2016, 2, 20), "Bonansea", "barbara.bonansea@gmail.com"));
		});
		Assert.assertTrue(exception.getMessage().contains("could not execute statement"));
	}

	@Test
	public void update() {
		organizationRepository.save(updateMockOrganization("Fiorentina FC", LocalDate.of(2012, 1, 15), "Mauro"));
		Organization organization = organizationRepository.findByOrganizationNameAndAsOfDate("Fiorentina FC", LocalDate.of(2012, 1, 15));
		Assert.assertEquals("Mauro", organization.getContactLastName());
		Assert.assertEquals("Ilaria", organization.getContactFirstName());
	}

	@Test
	public void update_TransactionSystemException_ContactLastNameMissing() {
		Exception exception = assertThrows(TransactionSystemException.class, () -> {
			organizationRepository.save(updateMockOrganization("Fiorentina FC", LocalDate.of(2012, 1, 15), null));
		});
		Assert.assertTrue(exception.getCause().getCause().getMessage().contains("ContactLastName is mandatory"));
	}

	@Test
	public void delete() {
		Organization organization = organizationRepository.findByOrganizationNameAndAsOfDate("US Sassuolo", LocalDate.of(2012, 1, 15));
		if (organization != null) {
			organizationRepository.deleteById(organization.getId());
		}
		else {
			Assert.fail("Unable to find record to delete");
		}
		Organization findOrg = organizationRepository.findByOrganizationNameAndAsOfDate("US Sassuolo", LocalDate.of(2012, 1, 15));
		Assert.assertNull(findOrg);
	}

	public static Organization createMockOrganization(String orgName, LocalDate startDate, LocalDate endDate, String contactLastName, String contactEmail) {
		Organization organization = new Organization();
		organization.setOrganizationName(orgName);
		organization.setOrganizationStatus(OrganizationStatus.Active);
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
		organization.setCreateTs(LocalDateTime.of(2015, 10, 27, 20, 30));
		organization.setLupdTs(LocalDateTime.of(2015, 10, 27, 20, 30));
		organization.setLupdUserId(1L);
		return organization;
	}

	private Organization updateMockOrganization(String orgName, LocalDate asOfDate, String contactLastName) {
		Organization organization = organizationRepository.findByOrganizationNameAndAsOfDate(orgName, asOfDate);
		organization.setContactLastName(contactLastName);
		organization.setContactFirstName("Ilaria");
		return organization;
	}
}