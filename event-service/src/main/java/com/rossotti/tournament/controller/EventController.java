package com.rossotti.tournament.controller;

import com.rossotti.tournament.client.TemplateFinderService;
import com.rossotti.tournament.dto.EventDTO;
import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.TemplateType;
import com.rossotti.tournament.exception.EntityExistsException;
import com.rossotti.tournament.exception.NoSuchEntityException;
import com.rossotti.tournament.jpa.service.EventJpaService;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import com.rossotti.tournament.model.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;

@Service
public class EventController {

	private final EventJpaService eventJpaService;
	private final OrganizationJpaService organizationJpaService;
	private final TemplateFinderService templateFinderService;
	private final Logger logger = LoggerFactory.getLogger(EventController.class);
	private static final String baseTeamName = "BaseTeam";
	private static final String baseLocationName = "BaseLocation";

	@Autowired
	public EventController(EventJpaService eventJpaService, OrganizationJpaService organizationJpaService, TemplateFinderService templateFinderService) {
		this.eventJpaService = eventJpaService;
		this.organizationJpaService = organizationJpaService;
		this.templateFinderService = templateFinderService;
	}

	public Event createEvent(EventDTO eventDTO) throws Exception {
		Organization organization = organizationJpaService.findByOrganizationNameAsOfDate(eventDTO.getOrganizationName(), eventDTO.getStartDate());
		if (organization != null) {
			TemplateType templateType = TemplateType.valueOf(eventDTO.getTemplateType());
			Event event = eventJpaService.findByOrganizationNameAsOfDateTemplateType(eventDTO.getOrganizationName(), eventDTO.getStartDate(), templateType);
			if (event == null) {
				TemplateDTO templateDTO = templateFinderService.findTemplateType(eventDTO.getTemplateType());

				event = buildEvent(eventDTO, organization);

				logger.debug("createEvent - saveEvent: orgName = " + eventDTO.getOrganizationName() + ", eventName = " + eventDTO.getEventName());
				eventJpaService.save(event);
				return event;
			}
			else {
				logger.debug("createEvent - findByOrganizationNameAsOfDateTemplateType: orgName = " + eventDTO.getOrganizationName() +
							 " startDate = " + eventDTO.getStartDate() +
							 " templateType = " + eventDTO.getTemplateType() + " exists");
				throw new EntityExistsException(Event.class);
			}
		}
		else {
			logger.debug("createEvent - findByOrganizationNameAsOfDate: orgName = " + eventDTO.getOrganizationName() +
						 " startDate = " + eventDTO.getStartDate() + " does not exist");
			throw new NoSuchEntityException(Organization.class);
		}
	}

	private Event buildEvent(EventDTO eventDTO, Organization organization) {
		ModelMapper modelMapper = new ModelMapper();
		Event event = modelMapper.map(eventDTO, Event.class);
		event.setOrganization(organization);

		OrganizationTeam baseTeam = null;
		for (int i = 0; i < organization.getOrganizationTeams().size(); i++) {
			if (organization.getOrganizationTeams().get(i).getTeamName().equals(baseTeamName)) {
				baseTeam = organization.getOrganizationTeams().get(i);
				break;
			}
		}

		OrganizationLocation baseLocation = null;
		for (int i = 0; i < organization.getOrganizationLocations().size(); i++) {
			if (organization.getOrganizationLocations().get(i).getLocationName().equals(baseLocationName)) {
				baseLocation = organization.getOrganizationLocations().get(i);
				break;
			}
		}

		if (baseTeam != null && baseLocation != null) {
			event.setEventTeams(new ArrayList<>());
			EventTeam eventTeam;
			for (int i = 0; i < eventDTO.getEventTeams(); i++) {
				eventTeam = new EventTeam();
				eventTeam.setEvent(event);
				eventTeam.setOrganizationTeam(baseTeam);
				eventTeam.setBaseTeamName(baseTeamName + i + 1);
				baseTeam.getEventTeams().add(eventTeam);
				event.getEventTeams().add(eventTeam);
			}

			event.setGameDates(new ArrayList<>());
			GameDate gameDate;
			GameLocation gameLocation;
			for (int i = 0; i < eventDTO.getEventDays(); i++) {
				gameDate = new GameDate();
				gameDate.setEvent(event);
				gameDate.setGameDate(eventDTO.getStartDate().plusDays(i));
				for (int j = 0; j < eventDTO.getEventLocations(); j++) {
					gameLocation = new GameLocation();
					gameLocation.setGameDate(gameDate);
					gameLocation.setOrganizationLocation(baseLocation);
					gameLocation.setBaseLocationName(baseLocationName + j + 1);
					gameLocation.setStartTime(LocalTime.of(j, 0, 0));
					baseLocation.getGameLocations().add(gameLocation);
					gameDate.getGameLocations().add(gameLocation);
				}
			}
		}
		else {
			logger.debug("buildEvent - findOrganizationTeam: baseTeam or baseLocation does not exist");
			throw new NoSuchEntityException(OrganizationTeam.class);
		}

		return event;
	}
}
