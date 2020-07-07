package com.rossotti.tournament.service;

import com.rossotti.tournament.dto.RoundDTO;
import com.rossotti.tournament.enumeration.GameRoundType;
import com.rossotti.tournament.enumeration.HalfDay;
import com.rossotti.tournament.model.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventServiceUtil {

	public static OrganizationTeam getOrganizationTeam(List<OrganizationTeam> organizationTeams, String baseTeamName) {
		OrganizationTeam baseTeam = null;
		for (OrganizationTeam organizationTeam : organizationTeams) {
			if (organizationTeam.getTeamName().equals(baseTeamName)) {
				baseTeam = organizationTeam;
				break;
			}
		}
		return baseTeam;
	}

	public static OrganizationLocation getOrganizationLocation(List<OrganizationLocation> organizationLocations, String baseLocationName) {
		OrganizationLocation baseLocation = null;
		for (OrganizationLocation organizationLocation : organizationLocations) {
			if (organizationLocation.getLocationName().equals(baseLocationName)) {
				baseLocation = organizationLocation;
				break;
			}
		}
		return baseLocation;
	}

	public static List<GameRoundType> getGameRounds(RoundDTO roundDTO) {
		List<GameRoundType> gameRounds = new ArrayList<>();
		for (int k = 1; k <= roundDTO.getPreliminary(); k++) {
			gameRounds.add(GameRoundType.GroupPlay);
		}
		if (roundDTO.getQuarterFinal())
			gameRounds.add(GameRoundType.QuarterFinal);
		if (roundDTO.getSemiFinal())
			gameRounds.add(GameRoundType.SemiFinal);
		if (roundDTO.getChampionship())
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

	public static List<GameRound> buildGameRounds(int eventDay,
												  int eventDuration,
												  int gameRoundCount,
												  HalfDay halfDay,
												  RoundDTO roundDTO,
												  List<GameRoundType> gameRoundTypes,
												  GameLocation gameLocation)
	{
		GameRound gameRound;
		List<GameRound> gameRounds = new ArrayList<>();

		if ((halfDay.equals(HalfDay.First) && eventDay == 1) || (halfDay.equals(HalfDay.Last) && eventDay == eventDuration)) {
			for (int i = 1; i <= roundDTO.getDayHalf(); i++) {
				gameRound = new GameRound();
				gameRound.setGameLocation(gameLocation);
				gameRound.setGameRoundType(gameRoundTypes.get(gameRoundCount));
				gameRound.setGameDuration((short) 45);
				gameRounds.add(gameRound);
				gameRoundCount++;
			}
		}
		else {
			for (int i = 1; i <= roundDTO.getDayFull(); i++) {
				gameRound = new GameRound();
				gameRound.setGameLocation(gameLocation);
				gameRound.setGameRoundType(gameRoundTypes.get(gameRoundCount));
				gameRound.setGameDuration((short) 45);
				gameRounds.add(gameRound);
				gameRoundCount++;
			}
		}
		return gameRounds;
	}
}
