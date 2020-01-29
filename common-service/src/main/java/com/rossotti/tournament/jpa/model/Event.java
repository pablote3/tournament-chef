package com.rossotti.tournament.jpa.model;

import com.rossotti.tournament.jpa.enumeration.EventStatus;
import com.rossotti.tournament.jpa.enumeration.EventType;
import com.rossotti.tournament.jpa.enumeration.Sport;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"organizationId", "startDate", "endDate"}))
public class Event extends BaseEntity {
	@ManyToOne
	@JoinColumn(name="organizationId", referencedColumnName="id", nullable=false)
	private Organization organization;
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@ManyToOne
	@JoinColumn(name="templateId", referencedColumnName="id", nullable=false)
	private Template template;
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}

	@OneToMany(mappedBy="event", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<EventTeam> eventTeams = new ArrayList<>();
	public List<EventTeam> getEventTeams()  {
		return eventTeams;
	}
	public void setEventTeams(List<EventTeam> eventTeams)  {
		this.eventTeams = eventTeams;
	}

	@Column(nullable=false)
	private LocalDate startDate;
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Column(nullable=false)
	private LocalDate endDate;
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=10, nullable=false)
	private EventStatus eventStatus;
	public EventStatus getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}
	public Boolean isSandbox() {
		return eventStatus == EventStatus.Sandbox;
	}
	public Boolean isScheduled() {
		return eventStatus == EventStatus.Scheduled;
	}
	public Boolean isInProgress() {
		return eventStatus == EventStatus.InProgress;
	}
	public Boolean isComplete() {
		return eventStatus == EventStatus.Complete;
	}

	@Column(length=60, nullable=false)
	private String eventName;
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=15, nullable=false)
	private Sport sport;
	public Sport getSport() {
		return sport;
	}
	public void setSport(Sport sport) {
		this.sport = sport;
	}
	public Boolean isWaterPolo() {
		return sport == Sport.WaterPolo;
	}
	public Boolean isLacrosse() {
		return sport == Sport.Lacrosse;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=10, nullable=false)
	private EventType eventType;
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	public Boolean isTournament() {
		return eventType == EventType.Tournament;
	}
}
