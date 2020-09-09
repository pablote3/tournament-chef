package com.rossotti.tournament.service;

import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.EventStatus;
import com.rossotti.tournament.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
		if (EventServiceUtil.validateGameLocations(event.getGameDates(), templateDTO.getEventLocations())) {
			return false;
		}
		if (EventServiceUtil.validateGameRounds(event.getGameDates(), templateDTO.getRoundDTO())) {
			return false;
		}
		return true;
	}

	public boolean validateTeams(List<EventTeam> eventTeams) {
		//return false if any event teams are still using the baseTeamName
		for (EventTeam eventTeam : eventTeams) {
			if (eventTeam.getOrganizationTeam().getTeamName() == baseTeamName) {
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
				if (gameLocation.getOrganizationLocation().getLocationName() == baseLocationName) {
					logger.debug("validateLocations - baseLocation found");
					return false;
				}
			}
		}
		return true;
	}
	public boolean validateDisplayGameIds(List<Long> displayGameIds) {
		//return false if array of displayGameId is not consecutive
		Collections.sort(displayGameIds);
		for (int i = 0; i < displayGameIds.size(); ++i) {
			Integer comparitor = Integer.valueOf(i + 1);
			if (displayGameIds.get(i).compareTo(Long.valueOf(comparitor)) != 0) {
				logger.debug("validateGames - index = " + i + " displayGameId = " + displayGameIds.get(i));
				return false;
			}
		}
		return true;
	}

	public List<Long> buildDisplayGameIds(List<GameDate> gameDates) throws NullPointerException {
		List<Long> displayGameIds = new ArrayList<>();
		for (GameDate gameDate : gameDates) {
			for (GameLocation gameLocation : gameDate.getGameLocations()) {
				for (GameRound gameRound: gameLocation.getGameRounds()) {
					for (Game game : gameRound.getGames()) {
						displayGameIds.add(game.getDisplayGameId());
					}
				}
			}
		}
		return displayGameIds;
	}
}
