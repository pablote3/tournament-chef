package com.rossotti.tournament.model;

import com.rossotti.tournament.enumeration.*;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"organizationId", "templateType", "startDate", "endDate"}))
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

	@OneToMany(mappedBy="event", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<EventTeam> eventTeams = new ArrayList<>();
	public List<EventTeam> getEventTeams()  {
		return eventTeams;
	}
	public void setEventTeams(List<EventTeam> eventTeams)  {
		this.eventTeams = eventTeams;
	}

	@OneToMany(mappedBy="event", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<GameDate> gameDates = new ArrayList<>();
	public List<GameDate> getGameDates()  {
		return gameDates;
	}
	public void setGameDates(List<GameDate> gameDates)  {
		this.gameDates = gameDates;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=25, nullable=false)
	@NotNull(message="TemplateType is mandatory")
	private TemplateType templateType;
	public TemplateType getTemplateType() {
		return templateType;
	}
	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}

	@Column(nullable=false)
	@NotNull(message="StartDate is mandatory")
	@FutureOrPresent(message="StartDate must be present or future")
	private LocalDate startDate;
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Column(nullable=false)
	@NotNull(message="EndDate is mandatory")
	@FutureOrPresent(message="EndDate must be present or future")
	private LocalDate endDate;
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=10, nullable=false)
	@NotNull(message="EventStatus is mandatory")
	private EventStatus eventStatus;
	public EventStatus getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=5, nullable=false)
	@NotNull(message="HalfDay is mandatory")
	private HalfDay halfDay;
	public HalfDay getHalfDay() {
		return halfDay;
	}
	public void setHalfDay(HalfDay halfDay) {
		this.halfDay = halfDay;
	}

	@Column(length=60, nullable=false)
	@NotBlank(message="EventName is mandatory")
	private String eventName;
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=15, nullable=false)
	@NotNull(message="Sport is mandatory")
	private Sport sport;
	public Sport getSport() {
		return sport;
	}
	public void setSport(Sport sport) {
		this.sport = sport;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=10, nullable=false)
	@NotNull(message="EventType is mandatory")
	private EventType eventType;
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
}
