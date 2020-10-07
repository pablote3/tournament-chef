package com.rossotti.tournament.service;

import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.EventStatus;
import com.rossotti.tournament.enumeration.GameRoundType;
import com.rossotti.tournament.enumeration.RankingType;
import com.rossotti.tournament.enumeration.TournamentType;
import com.rossotti.tournament.model.*;
import com.rossotti.tournament.util.EventUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceHelperBean {
	private final Logger logger = LoggerFactory.getLogger(EventServiceHelperBean.class);
	private static final String baseTeamName = "BaseTeam";
	private static final String baseLocationName = "BaseLocation";

	public boolean validateDatabaseEvent(Event event) {
		if (event.getEventStatus() == EventStatus.InProgress || event.getEventStatus() == EventStatus.Complete) {
			logger.debug("validateDatabaseEvent - database event invalid status: " + event.getEventStatus());
			return false;
		}
		return true;
	}

	public boolean validateRequestEvent(Event event) {
		if (event.getEventStatus() != EventStatus.Scheduled) {
			logger.debug("validateRequestEvent - request event invalid status: " + event.getEventStatus());
			return false;
		}
		return true;
	}

	public boolean validateTemplate(Event event, TemplateDTO templateDTO) {
		if (event.getEventTeams().size() != templateDTO.getTotalTeams()) {
			logger.debug("validateTemplate - eventTeams: " + event.getEventTeams().size() + " not equal template.totalTeams: " + templateDTO.getTotalTeams());
			return false;
		}
		if (event.getGameDates().size() != (int) Math.ceil(templateDTO.getEventDays())) {
			logger.debug("validateTemplate - gameDates: " + event.getGameDates().size() + " not equal template.eventDays: " + (int) Math.ceil(templateDTO.getEventDays()));
			return false;
		}
		if (EventUtil.validateGameLocations(event.getGameDates(), templateDTO.getEventLocations())) {
			return false;
		}
		if (EventUtil.validateGameRounds(event.getGameDates(), templateDTO.getRoundDTO())) {
			return false;
		}
		return true;
	}

	public boolean validateTeams(Event event, TemplateDTO templateDTO, RankingType rankingType) {
		List<EventTeam> eventTeams = event.getEventTeams();
		List<Short> eventTeamRankings = new ArrayList<>();
		for (EventTeam eventTeam : eventTeams) {
			if (eventTeam.getOrganizationTeam().getTeamName().equals(baseTeamName)) {
				logger.debug("validateTeams - baseTeam found");
				return false;
			}
			for (EventTeamRanking eventTeamRanking : eventTeam.getEventTeamRankings()) {
				if (eventTeamRanking.getRankingType().equals(rankingType)) {
					eventTeamRankings.add(eventTeamRanking.getRanking());
				}
			}
		}
		if (eventTeamRankings.size() != templateDTO.getGameDTO().getTotal()) {
			logger.debug("validateTeams - count of event rankings not equal to template games total");
			return false;
		}
		else if (!EventUtil.validateConsecutive(eventTeamRankings)) {
			logger.debug("validateTeams - event rankings not in consecutive order");
			return false;
		}
		return true;
	}

	public boolean validateLocations(Event event) {
		List<GameDate> gameDates = event.getGameDates();
		List<GameLocation> gameLocations;
		for (GameDate gameDate : gameDates) {
			gameLocations = gameDate.getGameLocations();
			for (GameLocation gameLocation : gameLocations) {
				if (gameLocation.getOrganizationLocation().getLocationName().equals(baseLocationName)) {
					logger.debug("validateLocations - baseLocation found");
					return false;
				}
			}
		}
		return true;
	}

	public boolean validateGames(Event event, TemplateDTO templateDTO) {
		List<Game> games = EventUtil.extractGames(event);
		if (games.size() > 0 && games.size() == templateDTO.getGameDTO().getTotal()) {
			//validate games per day/location/round
			//validate startTime vs round
			for (GameDate gameDate : event.getGameDates()) {
				for (GameLocation gameLocation : gameDate.getGameLocations()) {
					for (GameRound gameRound : gameLocation.getGameRounds()) {
						if (gameRound.getGames() != null && gameRound.getGames().size() > 0) {
							if (gameRound.getGameRoundType().equals(GameRoundType.GroupPlay1) ||
								gameRound.getGameRoundType().equals(GameRoundType.GroupPlay2) ||
								gameRound.getGameRoundType().equals(GameRoundType.GroupPlay3) ||
								gameRound.getGameRoundType().equals(GameRoundType.GroupPlay4));
						}
					}
				}
			}
			//validate gameTeams per round
			//validate pointsScored/gameStatus
		}
		return true;
	}

	public Event createGames (Event event, TemplateDTO templateDTO) {

		for (GameDate gameDate : event.getGameDates()) {
			for (GameLocation gameLocation : gameDate.getGameLocations()) {
				for (GameRound gameRound : gameLocation.getGameRounds()) {
					for (EventTeam eventTeam : event.getEventTeams()) {

					}
				}
			}
		}
		int gridGroups = templateDTO.getGridGroups();
		int gridTeams = templateDTO.getGridTeams();

		// create games

		if (templateDTO.getTournamentType() == TournamentType.round_robin) {
			int gameCount = EventUtil.getGameCount_RR(event.getEventTeams());
		}
		return event;
	}
}
