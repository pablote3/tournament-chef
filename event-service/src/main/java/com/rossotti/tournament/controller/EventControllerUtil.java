package com.rossotti.tournament.controller;

import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.GameRoundType;
import com.rossotti.tournament.enumeration.HalfDay;
import com.rossotti.tournament.model.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventControllerUtil {

	public static OrganizationTeam getOrganizationTeam(List<OrganizationTeam> organizationTeams, String baseTeamName) {
		OrganizationTeam baseTeam = null;
		for (int i = 0; i < organizationTeams.size(); i++) {
			if (organizationTeams.get(i).getTeamName().equals(baseTeamName)) {
				baseTeam = organizationTeams.get(i);
				break;
			}
		}
		return baseTeam;
	}

	public static OrganizationLocation getOrganizationLocation(List<OrganizationLocation> organizationLocations, String baseLocationName) {
		OrganizationLocation baseLocation = null;
		for (int i = 0; i < organizationLocations.size(); i++) {
			if (organizationLocations.get(i).getLocationName().equals(baseLocationName)) {
				baseLocation = organizationLocations.get(i);
				break;
			}
		}
		return baseLocation;
	}

	public static List<GameRoundType> getGameRounds(TemplateDTO templateDTO) {
		List<GameRoundType> gameRounds = new ArrayList<>();
		for (int k = 1; k <= templateDTO.getRoundDTO().getPreliminary(); k++) {
			gameRounds.add(GameRoundType.GroupPlay);
		}
		if (templateDTO.getRoundDTO().getQuarterFinal())
			gameRounds.add(GameRoundType.QuarterFinal);
		if (templateDTO.getRoundDTO().getSemiFinal())
			gameRounds.add(GameRoundType.SemiFinal);
		if (templateDTO.getRoundDTO().getChampionship())
			gameRounds.add(GameRoundType.Championship);
		return gameRounds;
	}

	public static HalfDay determineHalfDay(LocalDate eventStartDate, float eventDays) {
		if (eventDays == Math.round(eventDays)) {
			if (eventStartDate.getDayOfWeek().equals(DayOfWeek.FRIDAY))
				return HalfDay.First;
			else
				return HalfDay.Last;
		}
		else {
			return HalfDay.None;
		}
	}

//	private List<GameRound> buildGameRounds(GameLocation gameLocation, List gameRoundTypes, int roundsPerDay) {
//		GameRound gameRound;
//		List<GameRound> gameRounds = new ArrayList<>();
//		for (int k = 1; k < roundsPerDay; k++) {
//			gameRound = new GameRound();
//			gameRound.setGameLocation(gameLocation);
//			gameRound.setGameRoundType((GameRoundType) gameRoundTypes.get(k));
//			gameRound.setGameDuration((short) 45);
//			gameLocation.getGameRounds().add(gameRound);
//		}
//		return gameRounds;
//	}
}
