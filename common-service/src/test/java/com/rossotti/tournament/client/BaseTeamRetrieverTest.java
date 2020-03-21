package com.rossotti.tournament.client;

import com.rossotti.tournament.dto.BaseTeamDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.rossotti.tournament.config.ServiceConfig.class)
public class BaseTeamRetrieverTest {

	private BaseTeamRetrieveService baseTeamRetrieveService;

	@Autowired
	public void setBaseTeamRetrieveService(BaseTeamRetrieveService baseTeamRetrieveService) {
		this.baseTeamRetrieveService = baseTeamRetrieveService;
	}

	@Test
	public void testRetrieveBaseTeam_found() {
		List<BaseTeamDTO> baseTeams = null;
		try {
			baseTeams = baseTeamRetrieveService.retrieveBaseTeams(4);
		} catch (Exception e) {
			System.out.println("failed to create baseTeams from json file " + e.getMessage());
		}
		Assert.assertNotNull(baseTeams);
		Assert.assertEquals(4, baseTeams.size());
		Assert.assertEquals("BaseTeam4", baseTeams.get(3).getTeamName());
	}

	@Test
	public void testRetrieveBaseTeam_notFound() {
		IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> baseTeamRetrieveService.retrieveBaseTeams(50));
		Assert.assertTrue(exception.getMessage().contains("toIndex = 50"));
	}
}