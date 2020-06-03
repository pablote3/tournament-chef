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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
				event.setHalfDay(determineHalfDay(eventDTO.getStartDate(), templateDTO.getEventDays()));

				OrganizationTeam baseTeam = getOrganizationTeam(organization);
				OrganizationLocation baseLocation = getOrganizationLocation(organization);
				List<GameRoundType> gameRounds = getGameRounds(templateDTO);
//				int totalTeams = templateDTO.getGridTeams() * templateDTO.getGridGroups();
//				int eventDay = 1;

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
					for (int i = 1; i <= eventDuration; i++) {
						gameDate = new GameDate();
						gameDate.setEvent(event);
						gameDate.setGameDate(eventDTO.getStartDate().plusDays(i));
						event.getGameDates().add(gameDate);

						GameLocation gameLocation;

						for (int j = 1; j <= templateDTO.getEventLocations(); j++) {
							gameLocation = new GameLocation();
							gameLocation.setGameDate(gameDate);
							gameLocation.setOrganizationLocation(baseLocation);
							gameLocation.setBaseLocationName(baseLocationName + j);
							gameLocation.setStartTime(LocalTime.of(8, 0, 0));
//							gameLocation.setGameRounds(buildGameRounds(gameLocation, gameRounds, roundsPerDay));
							baseLocation.getGameLocations().add(gameLocation);
							gameDate.getGameLocations().add(gameLocation);

//							if (gameRounds.size() % templateDTO.getEventDays() == 0) {
//								gameLocation.setGameRounds(buildGameRounds(gameLocation, gameRounds, roundsPerDay));
//							}
//							else {
//								if (eventDTO.getStartDate().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
//
//								}
//							}
						}
					}
				}
				else {
					if (baseTeam == null) {
						logger.debug("createEvent - getOrganizationTeam: baseTeam does not exist");
						throw new NoSuchEntityException(EventController.class);
					}
					else if (baseLocation == null) {
						logger.debug("createEvent - getOrganizationLocation: baseLocation does not exist");
						throw new NoSuchEntityException(EventController.class);
					}
					else if (gameRounds.size() == 0) {
						logger.debug("createEvent - getGameRounds: no games counted for templateType = eventDTO.getTemplateType()");
						throw new InitializationException(EventController.class);
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

	private OrganizationTeam getOrganizationTeam(Organization organization) {
		OrganizationTeam baseTeam = null;
		for (int i = 0; i < organization.getOrganizationTeams().size(); i++) {
			if (organization.getOrganizationTeams().get(i).getTeamName().equals(baseTeamName)) {
				baseTeam = organization.getOrganizationTeams().get(i);
				break;
			}
		}
		return baseTeam;
	}

	private OrganizationLocation getOrganizationLocation(Organization organization) {
		OrganizationLocation baseLocation = null;
		for (int i = 0; i < organization.getOrganizationLocations().size(); i++) {
			if (organization.getOrganizationLocations().get(i).getLocationName().equals(baseLocationName)) {
				baseLocation = organization.getOrganizationLocations().get(i);
				break;
			}
		}
		return baseLocation;
	}

	private List<GameRoundType> getGameRounds(TemplateDTO templateDTO) {
		List<GameRoundType> gameRounds = new ArrayList<>();
		for (int k = 1; k < templateDTO.getRoundDTO().getPreliminary(); k++) {
			gameRounds.add(GameRoundType.GroupPlay);
		}
		if (templateDTO.getRoundDTO().getQuarterFinal())
			gameRounds.add(GameRoundType.QuarterFinal);
		if (templateDTO.getRoundDTO().getSemiFinal())
			gameRounds.add(GameRoundType.SemiFinal);
		if (templateDTO.getRoundDTO().getChampionship())
			gameRounds.add(GameRoundType.Final);
		return gameRounds;
	}

	private HalfDay determineHalfDay(LocalDate eventStartDate, float eventDays) {
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

	private List<GameRound> buildGameRounds(GameLocation gameLocation, List gameRoundTypes, int roundsPerDay) {
		GameRound gameRound;
		List<GameRound> gameRounds = new ArrayList<>();
		for (int k = 1; k < roundsPerDay; k++) {
			gameRound = new GameRound();
			gameRound.setGameLocation(gameLocation);
			gameRound.setGameRoundType((GameRoundType) gameRoundTypes.get(k));
			gameRound.setGameDuration((short) 45);
			gameLocation.getGameRounds().add(gameRound);
		}
		return gameRounds;
	}
}
