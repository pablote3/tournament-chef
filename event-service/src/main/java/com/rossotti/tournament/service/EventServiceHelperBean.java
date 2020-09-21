package com.rossotti.tournament.service;

import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.EventStatus;
import com.rossotti.tournament.enumeration.TournamentType;
import com.rossotti.tournament.model.*;
import com.rossotti.tournament.util.EventUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Collections;
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

	public boolean validateTeams(List<EventTeam> eventTeams) {
		for (EventTeam eventTeam : eventTeams) {
			if (eventTeam.getOrganizationTeam().getTeamName().equals(baseTeamName)) {
				//return false if any event teams are still using the baseTeamName
				logger.debug("validateTeams - baseTeam found");
				return false;
			}

		}
		return true;
	}

	public boolean validateLocations(List<GameDate> gameDates) {
		//return false if any game locations are still using the baseLocationName
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

	public List<Long> validateGames(List<GameDate> gameDates) {
		List<Long> displayGameIds;
		try {
			displayGameIds = EventUtil.buildDisplayGameIds(gameDates);
		} catch (NullPointerException e) {
			return null;
		}
		if (displayGameIds.size() > 0) {
			//return false if array of displayGameId is not consecutive
			if (!EventUtil.validateConsecutive(displayGameIds)) {
				return null;
			}
		}
		return displayGameIds;
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
