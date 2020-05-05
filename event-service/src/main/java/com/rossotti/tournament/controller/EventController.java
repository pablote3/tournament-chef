package com.rossotti.tournament.controller;

import com.rossotti.tournament.client.TemplateFinderService;
import com.rossotti.tournament.dto.EventDTO;
import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.*;
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

import java.time.DayOfWeek;
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

				ModelMapper modelMapper = new ModelMapper();
				event = modelMapper.map(eventDTO, Event.class);
				event.setOrganization(organization);
				event.setTemplateType(templateType);
				event.setStartDate(eventDTO.getStartDate());
				event.setEndDate(eventDTO.getStartDate().plusDays(templateDTO.getEventDays()));
				event.setHalfDay(HalfDay.valueOf(eventDTO.getHalfDay()));
				event.setEventStatus(EventStatus.Sandbox);
				event.setEventName(eventDTO.getEventName());
				event.setSport(Sport.valueOf(eventDTO.getSport()));

				OrganizationTeam baseTeam = null;
				for (int i = 0; i < organization.getOrganizationTeams().size(); i++) {
					if (organization.getOrganizationTeams().get(i).getTeamName().equals(baseTeamName)) {
						baseTeam = organization.getOrganizationTeams().get(i);
						break;
					}
				}

				if (baseTeam != null) {
					OrganizationLocation baseLocation = null;
					for (int i = 0; i < organization.getOrganizationLocations().size(); i++) {
						if (organization.getOrganizationLocations().get(i).getLocationName().equals(baseLocationName)) {
							baseLocation = organization.getOrganizationLocations().get(i);
							break;
						}
					}

					if (baseLocation != null) {
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
						for (int i = 0; i < templateDTO.getEventDays(); i++) {
							gameDate = new GameDate();
							gameDate.setEvent(event);
							gameDate.setGameDate(eventDTO.getStartDate().plusDays(i));

							List gameRoundTypes = new ArrayList<GameRoundType>();
							for (int k = 1; k < templateDTO.getPreliminaryRounds(); k++) {
								gameRoundTypes.add(GameRoundType.GroupPlay);
							}
							if (templateDTO.getQuarterFinals())
								gameRoundTypes.add(GameRoundType.QuarterFinal);
							if (templateDTO.getSemiFinals())
								gameRoundTypes.add(GameRoundType.SemiFinal);
							if (templateDTO.getFinals())
								gameRoundTypes.add(GameRoundType.Final);
							int roundsPerDay = gameRoundTypes.size() / templateDTO.getEventDays();
							int totalTeams = templateDTO.getGridTeams() * templateDTO.getGridGroups();
							int gamesPerDay = roundsPerDay * (totalTeams / 2);
					//logging

							GameLocation gameLocation;
							for (int j = 1; j < templateDTO.getEventLocations() + 1; j++) {
								gameLocation = new GameLocation();
								gameLocation.setGameDate(gameDate);
								gameLocation.setOrganizationLocation(baseLocation);
								gameLocation.setBaseLocationName(baseLocationName + j);
								gameLocation.setStartTime(LocalTime.of(8, 0, 0));
								gameLocation.setGameRounds(buildGameRounds(gameLocation, gameRoundTypes, roundsPerDay));
								baseLocation.getGameLocations().add(gameLocation);
								gameDate.getGameLocations().add(gameLocation);

								if (gameRoundTypes.size() % templateDTO.getEventDays() == 0) {
									gameLocation.setGameRounds(buildGameRounds(gameLocation, gameRoundTypes, roundsPerDay));
								}
								else {
									if (eventDTO.getStartDate().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {

									}
								}
							}
							event.getGameDates().add(gameDate);
						}
					}
					else {
						logger.debug("buildEvent - findOrganizationLocation: baseLocation does not exist");
						throw new NoSuchEntityException(EventController.class);
					}
				}
				else {
					logger.debug("buildEvent - findOrganizationTeam: baseTeam does not exist");
					throw new NoSuchEntityException(EventController.class);
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
