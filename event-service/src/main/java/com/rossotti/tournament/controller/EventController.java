package com.rossotti.tournament.controller;

import com.rossotti.tournament.client.TemplateFinderService;
import com.rossotti.tournament.dto.EventDTO;
import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.exception.EntityExistsException;
import com.rossotti.tournament.exception.NoSuchEntityException;
import com.rossotti.tournament.enumeration.TemplateType;
import com.rossotti.tournament.model.Event;
import com.rossotti.tournament.model.Organization;
import com.rossotti.tournament.jpa.service.EventJpaService;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
		Organization organization = organizationJpaService.findByOrganizationNameAsOfDate(eventDTO.getOrganizationName(), eventDTO.getEndDate());
		if (organization != null) {
			TemplateType templateType = TemplateType.valueOf(eventDTO.getTemplateType());
			Event event = eventJpaService.findByOrganizationNameAsOfDateTemplateType(eventDTO.getOrganizationName(), eventDTO.getEndDate(), templateType);
			if (event == null) {
				TemplateDTO templateDTO = templateFinderService.findTemplateType(eventDTO.getTemplateType().toString());

				event = buildEvent(eventDTO, templateDTO);

				logger.debug("createEvent - saveEvent: orgName = " + eventDTO.getOrganizationName() + ", eventName = " + eventDTO.getEventName());
				return eventJpaService.save(event);
			}
			else {
				logger.debug("createEvent - findByOrganizationNameAsOfDateTemplateType: orgName = " + eventDTO.getOrganizationName() +
							 " endDate = " + eventDTO.getEndDate() +
							 " templateType = " + eventDTO.getTemplateType() + " exists");
				throw new EntityExistsException(Event.class);
			}
		}
		else {
			logger.debug("createEvent - findByOrganizationNameAsOfDate: orgName = " + eventDTO.getOrganizationName() +
						 " endDate = " + eventDTO.getEndDate() + " does not exist");
			throw new NoSuchEntityException(Organization.class);
		}
	}

	private Event buildEvent(EventDTO eventDTO, TemplateDTO templateDTO) {
		LocalDate currentDate = LocalDate.now();

		ModelMapper modelMapper = new ModelMapper();
		Event event = modelMapper.map(eventDTO, Event.class);

		return event;
	}
}
