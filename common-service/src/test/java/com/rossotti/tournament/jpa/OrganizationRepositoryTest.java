package com.rossotti.tournament.jpa;

import com.rossotti.tournament.config.PersistenceConfig;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.repository.OrganizationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
		Assert.assertEquals(Organization.OrganizationStatus.Active, organization.getOrganizationStatus());
		Assert.assertEquals(LocalDate.of(2016, 2, 21), organization.getStartDate());
		Assert.assertEquals(LocalDate.of(9999, 12, 31), organization.getEndDate());
		Assert.assertEquals("123 Main Street", organization.getAddress1());
		Assert.assertEquals("Suite 456", organization.getAddress2());
		Assert.assertEquals("San Bernardino", organization.getCity());
		Assert.assertEquals("CA", organization.getState());
		Assert.assertEquals("92408", organization.getZipCode());
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
		Assert.assertEquals(3, organizations.size());
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
}