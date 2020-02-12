package com.rossotti.tournament.jpa.service.impl;

import com.rossotti.tournament.exception.CustomException;
import com.rossotti.tournament.exception.ValidationMessages;
import com.rossotti.tournament.jpa.enumeration.TemplateType;
import com.rossotti.tournament.jpa.model.Event;
import com.rossotti.tournament.jpa.repository.EventRepository;
import com.rossotti.tournament.jpa.service.EventJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.ConstraintViolationException;
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
	public List<Event> findByOrganizationName(String organizationName) {
		return eventRepository.findByOrganizationName(organizationName);
	}

	@Override
	public List<Event> findByOrganizationNameAndAsOfDate(String organizationName, LocalDate asOfDate) {
		return eventRepository.findByOrganizationNameAndAsOfDate(organizationName, asOfDate);
	}

	@Override
	public Event findByOrganizationNameAsOfDateTemplateType(String organizationName, LocalDate asOfDate, TemplateType templateType) {
		return eventRepository.findByOrganizationNameAsOfDateTemplateType(organizationName, asOfDate, templateType);
	}

	@Override
	public Event save(Event event) throws ResponseStatusException {
		try {
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
		}
		catch (Exception e) {
			if (e instanceof TransactionSystemException) {
				throw new CustomException(e.getCause().getCause().getMessage(), HttpStatus.BAD_REQUEST);
			}
			else if (e instanceof ConstraintViolationException) {
				throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
			else {
				throw new CustomException(ValidationMessages.MSG_VAL_0000, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return event;
	}

	@Override
	public Event delete(Long id) {
		try {
			Event event = getById(id);
			if (event != null) {
				eventRepository.deleteById(event.getId());
				return event;
			}
			else {
				throw new CustomException(ValidationMessages.MSG_VAL_0012, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch(CustomException ce) {
			throw ce;
		}
		catch(Exception e) {
			throw new CustomException(ValidationMessages.MSG_VAL_0007, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}