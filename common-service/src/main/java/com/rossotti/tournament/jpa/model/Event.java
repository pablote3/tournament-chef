package com.rossotti.tournament.jpa.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"organizationId", "startDate", "endDate"}))
public class Event extends BaseEntity {
//	@OneToMany(mappedBy="organization", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
//	private List<User> users = new ArrayList<>();
//	public List<User> getUsers()  {
//		return users;
//	}
//	public void setUsers(List<User> users)  {
//		this.users = users;
//	}
//
//	@OneToMany(mappedBy="organization", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
//	private List<OrganizationTeam> teams = new ArrayList<>();
//	public List<OrganizationTeam> getTeams()  {
//		return teams;
//	}
//	public void setTeams(List<OrganizationTeam> teams)  {
//		this.teams = teams;
//	}
//
//	@OneToMany(mappedBy="organization", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
//	private List<OrganizationLocation> locations = new ArrayList<>();
//	public List<OrganizationLocation> getLocations()  {
//		return locations;
//	}
//	public void setLocations(List<OrganizationLocation> locations)  {
//		this.locations = locations;
//	}

	@Column(nullable=false)
	private Long organizationId;
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
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
	@Column(length=9, nullable=false)
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
	public enum EventStatus {
		Sandbox, Scheduled, InProgress, Complete
	}

	@Column(length=80, nullable=false)
	private String eventName;
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=9, nullable=false)
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
	public enum Sport {
		WaterPolo, Lacrosse
	}

	@Enumerated(EnumType.STRING)
	@Column(length=9, nullable=false)
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
	public enum EventType {
		Tournament
	}
}
