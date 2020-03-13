package com.rossotti.tournament.dto;

import com.rossotti.tournament.model.AvailableLocation;
import com.rossotti.tournament.model.AvailableTeam;
import java.time.LocalDate;
import java.util.List;

public class EventDTO {
	private String eventName;
	private String organizationName;
	private String templateType;
	private LocalDate startDate;
	private LocalDate endDate;
	private int eventLocationCount;
	private String sport;
	private List<AvailableTeam> availableTeams;
	private List<AvailableLocation> availableLocations;

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

	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getEventLocationCount() {
		return eventLocationCount;
	}
	public void setEventLocationCount(int eventLocationCount) {
		this.eventLocationCount = eventLocationCount;
	}

	public String getSport() {
		return sport;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}

	public List<AvailableTeam> getTeams() {
		return availableTeams;
	}
	public void setTeams(List<AvailableTeam> availableTeams) {
		this.availableTeams = availableTeams;
	}

	public List<AvailableLocation> getAvailableLocations() {
		return availableLocations;
	}
	public void setAvailableLocations(List<AvailableLocation> availableLocations) {
		this.availableLocations = availableLocations;
	}
}