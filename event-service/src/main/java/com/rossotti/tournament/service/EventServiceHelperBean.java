package com.rossotti.tournament.service;

import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.EventStatus;
import com.rossotti.tournament.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EventServiceHelperBean {
	private final Logger logger = LoggerFactory.getLogger(EventServiceHelperBean.class);

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
}
