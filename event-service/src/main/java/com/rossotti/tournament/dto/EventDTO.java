package com.rossotti.tournament.dto;

import com.rossotti.tournament.enumeration.HalfDay;
import java.time.LocalDate;

public class EventDTO {
	private String eventName;
	private String organizationName;
	private String templateType;
	private LocalDate startDate;
	private HalfDay halfDay;
	private String sport;

	public HalfDay getHalfDay() {
		return halfDay;
	}
	public void setHalfDay(HalfDay halfDay) {
		this.halfDay = halfDay;	}
	public Boolean isFirst() {
		return halfDay == HalfDay.first;
	}
	public Boolean isLast() {
		return halfDay == HalfDay.last;
	}
	public Boolean isNone() {
		return halfDay == HalfDay.none;
	}

	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getTemplateType() {
		return templateType;
	}
	public void setsTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getSport() {
		return sport;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}
}
