package com.rossotti.tournament.util;

import com.rossotti.tournament.dto.RoundDTO;
import com.rossotti.tournament.enumeration.GameRoundType;
import com.rossotti.tournament.enumeration.HalfDay;
import com.rossotti.tournament.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventUtil {
	private static final Logger logger = LoggerFactory.getLogger(EventUtil.class);

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

	public static boolean validateGameLocations(List<GameDate> gameDates, int eventLocations) {
		for (GameDate gameDate : gameDates) {
			if (gameDate.getGameLocations().size() != eventLocations) {
				logger.debug("validateGameLocations: " + gameDate.getGameLocations().size() + " not equal template.eventLocations: " + eventLocations);
				return false;
			}
		}
		return true;
	}

	public static boolean validateGameRounds(List<GameDate> gameDates, RoundDTO roundDTO) {
		int gameRoundCount = 0;
		for (GameDate gameDate : gameDates) {
			for (GameLocation gameLocation: gameDate.getGameLocations()) {
				gameRoundCount = gameRoundCount + gameLocation.getGameRounds().size();
			}
		}
		int templateRoundCount = getGameRounds(roundDTO).size();
		if (gameRoundCount != templateRoundCount) {
			logger.debug("validateGameRounds: gameRoundCount: " + gameRoundCount + " not equal template.roundCount: " + templateRoundCount);
			return false;
		}
		return true;
	}

	public static List<Game> getTotalGames(List<GameDate> gameDates) {
		List<Game> totalGames = new ArrayList<>();
		for (GameDate gameDate : gameDates) {
			for (GameLocation gameLocation : gameDate.getGameLocations()) {
				for (GameRound gameRound: gameLocation.getGameRounds()) {
					if (gameRound.getGames() != null && gameRound.getGames().size() > 0) {
						for (Game game : gameRound.getGames()) {
							totalGames.add(game);
						}
					}
				}
			}
		}
		return totalGames;
	}

	public static boolean validateConsecutive(List<Short> shorts) {
		Collections.sort(shorts);
		for (short i = 0; i < shorts.size(); ++i) {
			short comparitor = (short)(i + 1);
			if (shorts.get(i) != null &&
					shorts.get(i).compareTo(Short.valueOf(comparitor)) != 0) {
				logger.debug("validateConsecutive - comparitor = " + comparitor + " value = " + shorts.get(i));
				return false;
			}
		}
		return true;
	}

	public static int getGameCount_RR(List<EventTeam> eventTeams) {
		return (eventTeams.size() * (eventTeams.size()-1)) / 2;
	}
}
