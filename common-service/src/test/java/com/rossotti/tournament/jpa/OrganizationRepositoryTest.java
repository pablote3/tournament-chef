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

@ActiveProfiles(profiles = "development")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceConfig.class)
public class OrganizationRepositoryTest {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Test
	public void findById() {
		Organization organization = organizationRepository.findById(1L);
		Assert.assertEquals("Washington Redskins", organization.getOrganizationName());
		Assert.assertEquals("123 Main Street", organization.getAddress1());
//		Assert.assertEquals(Organization.OrganizationStatus.Active, organization.getOrganizationStatus());
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