package com.rossotti.tournament.jpa;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.model.Organization.OrganizationStatus;
import com.rossotti.tournament.jpa.repository.OrganizationRepository;
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
public class OrganizationRepositoryTest {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Test
	public void findById() {
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
		Assert.assertEquals(LocalDateTime.of(2020, 1, 18, 20, 0), organization.getUpdateTs());
//		Assert.assertTrue(team.getStandings().size() >= 1);
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
		Assert.assertEquals(Organization.OrganizationStatus.Inactive, organization.getOrganizationStatus());
	}

	@Test
	public void findByOrgNameAndAsOfDate_Found_EqualEndDate() {
		Organization organization = organizationRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(9999, 12, 31));
		Assert.assertEquals(Organization.OrganizationStatus.Active, organization.getOrganizationStatus());
	}

	@Test
	public void findByOrgNameAndAsOfDate_Found_BetweenStartAndEndDate() {
		Organization organization = organizationRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2012, 10, 29));
		Assert.assertEquals(Organization.OrganizationStatus.Inactive, organization.getOrganizationStatus());
	}

	@Test
	public void findByOrgNameAndAsOfDate_NotFound_BeforeStartDate() {
		Organization organization = organizationRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2010, 1, 14));
		Assert.assertNull(organization);
	}

	@Test
	public void findByOrgNameAndAsOfDate_NotFound_AfterEndDate() {
		Organization organization = organizationRepository.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(10000, 1, 1));
		Assert.assertNull(organization);
	}

	@Test
	public void findByOrgNameAndAsOfDate_NotFound_OrganizationName() {
		Organization organization = organizationRepository.findByOrganizationNameAndAsOfDate("Orobica", LocalDate.of(9999, 12, 31));
		Assert.assertNull(organization);
	}

	@Test
	public void create() {
		organizationRepository.save(createMockOrganization("AS Roma", LocalDate.of(2010, 1, 11), LocalDate.of(9999, 12, 31), "Giugliano"));
		Organization findOrganization = organizationRepository.findByOrganizationNameAndAsOfDate("AS Roma", LocalDate.of(2010, 1, 21));
		Assert.assertEquals("Giugliano", findOrganization.getContactLastName());
		Assert.assertEquals("Manuela", findOrganization.getContactFirstName());
	}

	@Test(expected= DataIntegrityViolationException.class)
	public void create_MissingRequiredData() {
		organizationRepository.save(createMockOrganization("AS Roma", LocalDate.of(2010, 1, 11), LocalDate.of(9999, 12, 31), null));
	}

	@Test
	public void update() {
		organizationRepository.save(updateMockOrganization("Fiorentina FC", LocalDate.of(2012, 1, 15), "Mauro"));
		Organization findOrg = organizationRepository.findByOrganizationNameAndAsOfDate("Fiorentina FC", LocalDate.of(2012, 1, 15));
		Assert.assertEquals("Mauro", findOrg.getContactLastName());
		Assert.assertEquals("Ilaria", findOrg.getContactFirstName());
	}

	@Test(expected= DataIntegrityViolationException.class)
	public void update_MissingRequiredData() {
		organizationRepository.save(updateMockOrganization("Fiorentina FC", LocalDate.of(2012, 1, 15), null));
	}

	private Organization createMockOrganization(String orgName, LocalDate startDate, LocalDate endDate, String contactLastName) {
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
		organization.setContactEmail("manuela.giugliano@gmail.com");
		organization.setContactPhone("390665951");
		organization.setCreateTs(LocalDateTime.of(2015, 10, 27, 20, 30));
		organization.setUpdateTs(LocalDateTime.of(2015, 10, 27, 20, 30));
		return organization;
	}

	private Organization updateMockOrganization(String orgName, LocalDate asOfDate, String contactLastName) {
		Organization organization = organizationRepository.findByOrganizationNameAndAsOfDate(orgName, asOfDate);
		organization.setContactLastName(contactLastName);
		organization.setContactFirstName("Ilaria");
		return organization;
	}
}