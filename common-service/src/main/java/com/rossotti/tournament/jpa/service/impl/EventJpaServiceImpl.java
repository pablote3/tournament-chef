package com.rossotti.tournament.jpa.service.impl;

import com.rossotti.tournament.exception.NoSuchEntityException;
import com.rossotti.tournament.enumeration.TemplateType;
import com.rossotti.tournament.model.Event;
import com.rossotti.tournament.jpa.repository.EventRepository;
import com.rossotti.tournament.jpa.service.EventJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventJpaServiceImpl implements EventJpaService {

	private EventRepository eventRepository;

	@Autowired
	public void setEventRepository(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Override
	public Event getById(Long id) {
		return eventRepository.findById(id);
	}

	@Override
	public List<?> listAll() {
		return new ArrayList<>(eventRepository.findAll());
	}

	@Override
	public List<Event> findByEventName(String eventName) {
		return eventRepository.findByEventName(eventName);
	}

	@Override
	public List<Event> findByOrganizationName(String organizationName) {
		return eventRepository.findByOrganizationName(organizationName);
	}

	@Override
	public List<Event> findByEventNameAsOfDate(String eventName, LocalDate asOfDate) {
		return eventRepository.findByEventNameAsOfDate(eventName, asOfDate);
	}

	@Override
	public List<Event> findByOrganizationNameAsOfDate(String organizationName, LocalDate asOfDate) {
		return eventRepository.findByOrganizationNameAsOfDate(organizationName, asOfDate);
	}

	@Override
	public Event findByEventNameAsOfDateTemplateType(String eventName, LocalDate asOfDate, TemplateType templateType) {
		return eventRepository.findByEventNameAsOfDateTemplateType(eventName, asOfDate, templateType);
	}

	@Override
	public Event findByOrganizationNameAsOfDateTemplateType(String organizationName, LocalDate asOfDate, TemplateType templateType) {
		return eventRepository.findByOrganizationNameAsOfDateTemplateType(organizationName, asOfDate, templateType);
	}

	@Override
	public Event save(Event event) {
		Event findEvent = findByOrganizationNameAsOfDateTemplateType(event.getOrganization().getOrganizationName(), event.getStartDate(), event.getTemplate().getTemplateType());
		if (findEvent != null) {
			findEvent.setOrganization(event.getOrganization());
			findEvent.setTemplate(event.getTemplate());
			findEvent.setStartDate(event.getStartDate());
			findEvent.setEndDate(event.getEndDate());
			findEvent.setEventStatus(event.getEventStatus());
			findEvent.setEventName(event.getEventName());
			findEvent.setSport(event.getSport());
			findEvent.setEventType(event.getEventType());
			findEvent.setLupdTs(event.getLupdTs());
			findEvent.setLupdUserId(event.getLupdUserId());
			findEvent.setEventTeams(event.getEventTeams());
			findEvent.setGameDates(event.getGameDates());
			eventRepository.save(findEvent);
		}
		else {
			eventRepository.save(event);
		}
		return event;
	}

	@Override
	public void delete(Long id) {
		Event event = getById(id);
		if (event != null) {
			eventRepository.deleteById(event.getId());
		}
		else {
			throw new NoSuchEntityException(Event.class);
		}
	}
}