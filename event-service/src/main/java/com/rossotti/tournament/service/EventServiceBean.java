package com.rossotti.tournament.service;

import com.rossotti.tournament.client.TemplateFinderService;
import com.rossotti.tournament.dto.EventDTO;
import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.EventStatus;
import com.rossotti.tournament.enumeration.GameRoundType;
import com.rossotti.tournament.enumeration.Sport;
import com.rossotti.tournament.enumeration.TemplateType;
import com.rossotti.tournament.exception.EntityExistsException;
import com.rossotti.tournament.exception.InitializationException;
import com.rossotti.tournament.exception.InvalidEntityException;
import com.rossotti.tournament.exception.NoSuchEntityException;
import com.rossotti.tournament.jpa.service.EventJpaService;
import com.rossotti.tournament.jpa.service.GameJpaService;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import com.rossotti.tournament.model.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceBean {

	private final EventJpaService eventJpaService;
	private final OrganizationJpaService organizationJpaService;
	private final GameJpaService gameJpaService;
	private final TemplateFinderService templateFinderService;
	private final EventServiceHelperBean eventServiceHelperBean;
	private final Logger logger = LoggerFactory.getLogger(EventServiceBean.class);
	private static final String baseTeamName = "BaseTeam";
	private static final String baseLocationName = "BaseLocation";

	@Autowired
	public EventServiceBean(EventJpaService eventJpaService, OrganizationJpaService organizationJpaService, GameJpaService gameJpaService, TemplateFinderService templateFinderService, EventServiceHelperBean eventServiceHelperBean) {
		this.eventJpaService = eventJpaService;
		this.organizationJpaService = organizationJpaService;
		this.gameJpaService = gameJpaService;
		this.templateFinderService = templateFinderService;
		this.eventServiceHelperBean = eventServiceHelperBean;
	}

	public Event createEvent(EventDTO eventDTO) throws Exception {
		logger.debug("createEvent: orgName = " + eventDTO.getOrganizationName() + ", eventName = " + eventDTO.getEventName());
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
				event.setHalfDay(EventServiceUtil.determineHalfDay(eventDTO.getStartDate(), templateDTO.getEventDays()));

				OrganizationTeam baseTeam = EventServiceUtil.getOrganizationTeam(organization.getOrganizationTeams(), baseTeamName);
				OrganizationLocation baseLocation = EventServiceUtil.getOrganizationLocation(organization.getOrganizationLocations(), baseLocationName);
				List<GameRoundType> gameRounds = EventServiceUtil.getGameRounds(templateDTO.getRoundDTO());

				if (baseTeam != null && baseLocation != null && gameRounds.size() > 0) {
					event.setEventTeams(new ArrayList<>());
					EventTeam eventTeam;
					for (int i = 1; i < templateDTO.getTotalTeams() + 1; i++) {
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
							gameLocation.setGameRounds(EventServiceUtil.buildGameRounds(
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
						throw new NoSuchEntityException(EventServiceBean.class);
					}
					else if (baseLocation == null) {
						logger.debug("createEvent - getOrganizationLocation: baseLocation does not exist");
						throw new NoSuchEntityException(EventServiceBean.class);
					}
					else {
						logger.debug("createEvent - getGameRounds: no games counted for templateType = eventDTO.getTemplateType()");
						throw new InitializationException(EventServiceBean.class);
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

	public Event updateEvent(Event requestEvent) throws Exception {
		logger.debug("updateEvent: orgName = " + requestEvent.getOrganization().getOrganizationName() + ", eventName = " + requestEvent.getEventName());
		Event dbEvent = eventJpaService.findByOrganizationNameAsOfDateTemplateType(requestEvent.getOrganization().getOrganizationName(), requestEvent.getStartDate(), requestEvent.getTemplateType());
		if (dbEvent != null) {
			if (eventServiceHelperBean.validateDatabaseEvent(dbEvent) && eventServiceHelperBean.validateRequestEvent(requestEvent)) {
				TemplateDTO templateDTO = templateFinderService.findTemplateType(requestEvent.getTemplateType().name());
				if (eventServiceHelperBean.validateTemplate(requestEvent, templateDTO)) {
					if (eventServiceHelperBean.validateTeams(requestEvent.getEventTeams())) {
						if (eventServiceHelperBean.validateLocations(requestEvent.getGameDates())) {
							List<Game> eventGames = gameJpaService.findByEventNameStartDateEndDateTemplateType(requestEvent.getEventName(), requestEvent.getStartDate(), requestEvent.getEndDate(), requestEvent.getTemplateType());
//							if (eventGames.size() ) {
//								eventJpaService.save(requestEvent);
								return requestEvent;
//							}
						}
						else {

							throw new InvalidEntityException(Event.class);
						}
					}
					else {

						throw new InvalidEntityException(Event.class);
					}
				} else {
					logger.debug("updateEvent - validateTemplate: eventName = " + requestEvent.getEventName() + ", templateType = " + requestEvent.getTemplateType());
					throw new InvalidEntityException(Event.class);
				}
			}
			else {
				throw new InvalidEntityException(Event.class);
			}
		}
		else {
			logger.debug("updateEvent - findByOrganizationNameAsOfDateTemplateType: event does not exist");
			throw new NoSuchEntityException(Event.class);
		}
	}
}