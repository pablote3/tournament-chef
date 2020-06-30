package com.rossotti.tournament.controller;

import com.rossotti.tournament.client.TemplateFinderService;
import com.rossotti.tournament.jpa.service.EventJpaService;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}