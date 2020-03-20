package com.rossotti.tournament.client;

import com.rossotti.tournament.dto.BaseTeamDTO;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BaseTeamRetrieveService {

	private static List<BaseTeamDTO> baseTeams = null;

	public List<BaseTeamDTO> retrieveBaseTeams(int count) throws Exception {
		if (baseTeams == null) {
			initializeBaseTeams();
		}
		return baseTeams.subList(0, count);
	}

	private void initializeBaseTeams() throws IOException {
		baseTeams = new ArrayList<>();
		InputStream json = this.getClass().getClassLoader().getResourceAsStream("mockClient/baseTeamClient.json");
		BaseTeamDTO[] baseTeamsIn = JsonProvider.deserializeJson(BaseTeamDTO[].class, json);
		baseTeams = Arrays.asList(baseTeamsIn);
	}
}