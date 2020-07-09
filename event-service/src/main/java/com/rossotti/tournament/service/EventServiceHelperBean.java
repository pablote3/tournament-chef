package com.rossotti.tournament.service;

import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.EventStatus;
import com.rossotti.tournament.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
		for (EventTeam eventTeam : eventTeams) {
			if (eventTeam.getOrganizationTeam().getTeamName() == baseTeamName) {
				logger.debug("validateTeams - baseTeam found");
				return false;
			}
		}
		return true;
	}

	public boolean validateLocations(List<GameDate> gameDates) {
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
}
