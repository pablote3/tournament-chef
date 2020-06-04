package com.rossotti.tournament.controller;

import com.rossotti.tournament.client.TemplateFinderService;
import com.rossotti.tournament.dto.EventDTO;
import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.*;
import com.rossotti.tournament.exception.EntityExistsException;
import com.rossotti.tournament.exception.InitializationException;
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
import java.util.List;

@Service
public class EventControllerBean {

	private final EventJpaService eventJpaService;
	private final OrganizationJpaService organizationJpaService;
	private final TemplateFinderService templateFinderService;
	private final Logger logger = LoggerFactory.getLogger(EventControllerBean.class);
	private static final String baseTeamName = "BaseTeam";
	private static final String baseLocationName = "BaseLocation";

	@Autowired
	public EventControllerBean(EventJpaService eventJpaService, OrganizationJpaService organizationJpaService, TemplateFinderService templateFinderService) {
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
				int eventDuration = Math.round(templateDTO.getEventDays());

				ModelMapper modelMapper = new ModelMapper();
				event = modelMapper.map(eventDTO, Event.class);
				event.setOrganization(organization);
				event.setTemplateType(templateType);
				event.setStartDate(eventDTO.getStartDate());
				event.setEndDate(eventDTO.getStartDate().plusDays(eventDuration));
				event.setEventStatus(EventStatus.Sandbox);
				event.setEventName(eventDTO.getEventName());
				event.setSport(Sport.valueOf(eventDTO.getSport()));
				event.setHalfDay(EventControllerUtil.determineHalfDay(eventDTO.getStartDate(), templateDTO.getEventDays()));

				OrganizationTeam baseTeam = EventControllerUtil.getOrganizationTeam(organization.getOrganizationTeams(), baseTeamName);
				OrganizationLocation baseLocation = EventControllerUtil.getOrganizationLocation(organization.getOrganizationLocations(), baseLocationName);
				List<GameRoundType> gameRounds = EventControllerUtil.getGameRounds(templateDTO.getRoundDTO());

				if (baseTeam != null && baseLocation != null && gameRounds.size() > 0) {
					event.setEventTeams(new ArrayList<>());
					EventTeam eventTeam;
					int teamCount = templateDTO.getGridGroups() * templateDTO.getGridTeams();
					for (int i = 1; i < teamCount + 1; i++) {
						eventTeam = new EventTeam();
						eventTeam.setEvent(event);
						eventTeam.setOrganizationTeam(baseTeam);
						eventTeam.setBaseTeamName(baseTeamName + i);
						baseTeam.getEventTeams().add(eventTeam);
						event.getEventTeams().add(eventTeam);
					}

					event.setGameDates(new ArrayList<>());
					GameDate gameDate;
					int gameRoundCount = 0;
					for (int i = 1; i <= eventDuration; i++) {
						gameDate = new GameDate();
						gameDate.setEvent(event);
						gameDate.setGameDate(eventDTO.getStartDate().plusDays(i));
						event.getGameDates().add(gameDate);

						GameLocation gameLocation;
						boolean gameLocationCountUsed = false;
						for (int j = 1; j <= templateDTO.getEventLocations(); j++) {
							gameLocation = new GameLocation();
							gameLocation.setGameDate(gameDate);
							gameLocation.setOrganizationLocation(baseLocation);
							gameLocation.setBaseLocationName(baseLocationName + j);
							gameLocation.setStartTime(LocalTime.of(8, 0, 0));
							gameLocation.setGameRounds(EventControllerUtil.buildGameRounds(
									i,
									eventDuration,
									gameRoundCount,
									event.getHalfDay(),
									templateDTO.getRoundDTO(),
									gameRounds,
									gameLocation)
							);
							baseLocation.getGameLocations().add(gameLocation);
							gameDate.getGameLocations().add(gameLocation);
							if (!gameLocationCountUsed) {
								gameRoundCount = gameRoundCount + gameLocation.getGameRounds().size();
								gameLocationCountUsed = true;
							}
						}
					}
				}
				else {
					if (baseTeam == null) {
						logger.debug("createEvent - getOrganizationTeam: baseTeam does not exist");
						throw new NoSuchEntityException(EventControllerBean.class);
					}
					else if (baseLocation == null) {
						logger.debug("createEvent - getOrganizationLocation: baseLocation does not exist");
						throw new NoSuchEntityException(EventControllerBean.class);
					}
					else {
						logger.debug("createEvent - getGameRounds: no games counted for templateType = eventDTO.getTemplateType()");
						throw new InitializationException(EventControllerBean.class);
					}
				}
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
}
