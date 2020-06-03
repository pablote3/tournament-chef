package com.rossotti.tournament;

import com.rossotti.tournament.controller.EventControllerUtil;
import com.rossotti.tournament.model.OrganizationTeam;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class EventControllerUtilTest {

	private static final String baseTeamName = "BaseTeam";
	private static final String baseLocationName = "BaseLocation";

	@Test
	public void getOrganizationTeam_notFound_emptyList() {
		List<OrganizationTeam> organizationTeams = new ArrayList<>();
		OrganizationTeam organizationTeam = EventControllerUtil.getOrganizationTeam(organizationTeams, baseTeamName);
		Assert.assertNull(organizationTeam);
	}

	@Test
	public void getOrganizationTeam_notFound_validList() {
		List<OrganizationTeam> organizationTeams = new ArrayList<>();
		organizationTeams.add(getOrganizationTeam("team1"));
		organizationTeams.add(getOrganizationTeam("teambase"));
		organizationTeams.add(getOrganizationTeam("BaseTeam2"));
		organizationTeams.add(getOrganizationTeam("baseTeam"));
		OrganizationTeam organizationTeam = EventControllerUtil.getOrganizationTeam(organizationTeams, baseTeamName);
		Assert.assertNull(organizationTeam);
	}

	@Test
	public void getOrganizationTeam_found() {
		List<OrganizationTeam> organizationTeams = new ArrayList<>();
		organizationTeams.add(getOrganizationTeam("team1"));
		organizationTeams.add(getOrganizationTeam("teambase"));
		organizationTeams.add(getOrganizationTeam("BaseTeam"));
		organizationTeams.add(getOrganizationTeam("baseTeam"));
		OrganizationTeam organizationTeam = EventControllerUtil.getOrganizationTeam(organizationTeams, baseTeamName);
		Assert.assertNotNull(organizationTeam);
		Assert.assertEquals(baseTeamName, organizationTeam.getTeamName());
	}

	private OrganizationTeam getOrganizationTeam(String teamName) {
		OrganizationTeam organizationTeam = new OrganizationTeam();
		organizationTeam.setTeamName(teamName);
		return organizationTeam;
	}
}