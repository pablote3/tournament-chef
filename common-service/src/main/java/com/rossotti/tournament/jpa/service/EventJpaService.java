package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.jpa.enumeration.TemplateType;
import com.rossotti.tournament.jpa.model.Event;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public interface EventJpaService extends CrudService<Event> {
	List<Event> findByOrganizationName(String organizationName);
	List<Event> findByOrganizationNameAndAsOfDate(String organizationName, LocalDate asOfDate);
	Event findByOrganizationNameAsOfDateTemplateType(String organizationName, LocalDate asOfDate, TemplateType templateType);
}