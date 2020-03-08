package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.jpa.enumeration.TemplateType;
import com.rossotti.tournament.model.Event;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public interface EventJpaService extends CrudService<Event> {
	List<Event> findByEventName(String eventName);
	List<Event> findByOrganizationName(String organizationName);
	List<Event> findByEventNameAsOfDate(String eventName, LocalDate asOfDate);
	List<Event> findByOrganizationNameAsOfDate(String organizationName, LocalDate asOfDate);
	Event findByEventNameAsOfDateTemplateType(String eventName, LocalDate asOfDate, TemplateType templateType);
	Event findByOrganizationNameAsOfDateTemplateType(String organizationName, LocalDate asOfDate, TemplateType templateType);
}