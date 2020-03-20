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
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class EventController {

	private final EventJpaService eventJpaService;
	private final OrganizationJpaService organizationJpaService;
	private final TemplateFinderService templateFinderService;
	private final Logger logger = LoggerFactory.getLogger(EventController.class);

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

				event = buildEvent(eventDTO, organization, templateDTO);

				logger.debug("createEvent - saveEvent: orgName = " + eventDTO.getOrganizationName() + ", eventName = " + eventDTO.getEventName());
				return eventJpaService.save(event);
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

	private Event buildEvent(EventDTO eventDTO, Organization organization, TemplateDTO templateDTO) {
		LocalDate currentDate = LocalDate.now();
		ModelMapper modelMapper = new ModelMapper();
		Event event = modelMapper.map(eventDTO, Event.class);
		event.setOrganization(organization);

		EventTeam eventTeam;
		event.setEventTeams(new ArrayList<>());
		for (int i = 0; i < templateDTO.getGridTeamsRound1(); i++) {
			eventTeam = new EventTeam();
			eventTeam.setEvent(event);
			OrganizationTeam organizationTeam = new OrganizationTeam();
			organizationTeam.setTeamName("Team" + i);
			organizationTeam.getEventTeams().add(eventTeam);
			eventTeam.setAvailableTeam(organizationTeam);
			event.getEventTeams().add(eventTeam);
		}
		return event;
	}
}
