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
		Assert.assertEquals("123 Main Street", organization.getAddress1());
		Assert.assertEquals("Suite 456", organization.getAddress2());
		Assert.assertEquals("San Bernardino", organization.getCity());
		Assert.assertEquals("CA", organization.getState());
		Assert.assertEquals("92408", organization.getZipCode());
		Assert.assertEquals("Girelli", organization.getContactLastName());
		Assert.assertEquals("Cristiana", organization.getContactFirstName());
		Assert.assertEquals("cristiana.girelli@gmail.com", organization.getContactEmail());
		Assert.assertEquals("9093381808", organization.getContactPhone());
		Assert.assertEquals(LocalDate.of(2016, 2, 21), organization.getStartDate());
		Assert.assertEquals(LocalDate.of(9999, 12, 31), organization.getEndDate());
		Assert.assertEquals(LocalDateTime.of(2015, 10, 27, 20, 0), organization.getCreateTs());
		Assert.assertEquals(LocalDateTime.of(2020, 01, 18, 20, 0), organization.getUpdateTs());
//		Assert.assertTrue(team.getStandings().size() >= 1);
	}
//	@Test
//	public void findAll() {
//		List<Team> teams = teamRepository.findAll();
//		Assert.assertTrue(teams.size() >= 12);
//	}
//
//	@Test
//	public void findByKey_Found() {
//		List<Team> teams = teamRepository.findByTeamKey("st-louis-bomber's");
//		Assert.assertEquals(2, teams.size());
//	}
//
//	@Test
//	public void findByKey_NotFound() {
//		List<Team> teams = teamRepository.findByTeamKey("st-louis-bombber's");
//		Assert.assertEquals(0, teams.size());
//	}
}