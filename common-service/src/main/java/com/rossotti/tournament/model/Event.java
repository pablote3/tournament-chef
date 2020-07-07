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
	public Boolean is2x4_PP_10D_1L() {
		return templateType == TemplateType.two_x_four_pp_10D_1L;
	}
	public Boolean is2x4_PP_15D_1L() {
		return templateType == TemplateType.two_x_four_pp_15D_1L;
	}
	public Boolean is2x4_RR_20D_1L() {
		return templateType == TemplateType.two_x_four_rr_20D_1L;
	}
	public Boolean is2x4_RR_10D_2L() {
		return templateType == TemplateType.two_x_four_rr_10D_2L;
	}
	public Boolean is4x3_RR_20D_1L() {
		return templateType == TemplateType.four_x_three_rr_20D_1L;
	}
	public Boolean is4x3_RR_20D_2L() {
		return templateType == TemplateType.four_x_three_rr_20D_2L;
	}
	public Boolean is4x3_RR_10D_2L() {
		return templateType == TemplateType.four_x_three_rr_10D_2L;
	}
	public Boolean is4x4_PP_20D_2L() {
		return templateType == TemplateType.four_x_four_pp_20D_2L;
	}
	public Boolean is4x4_PP_15D_2L() {
		return templateType == TemplateType.four_x_four_pp_15D_2L;
	}
	public Boolean is4x4_RR_40D_1L() {
		return templateType == TemplateType.four_x_four_rr_40D_1L;
	}
	public Boolean is4x4_RR_15D_2L() { return templateType == TemplateType.four_x_four_rr_15D_2L; }
	public Boolean is8x2_PP_20D_2L() { return templateType == TemplateType.eight_x_two_pp_20D_2L; }

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
	public Boolean isFirst() {
		return halfDay == HalfDay.First;
	}
	public Boolean isLast() {
		return halfDay == HalfDay.Last;
	}
	public Boolean isNone() {
		return halfDay == HalfDay.None;
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
	public Boolean isWaterPolo() {
		return sport == Sport.WaterPolo;
	}
	public Boolean isLacrosse() {
		return sport == Sport.Lacrosse;
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
	public Boolean isTournament() {
		return eventType == EventType.Tournament;
	}
}
