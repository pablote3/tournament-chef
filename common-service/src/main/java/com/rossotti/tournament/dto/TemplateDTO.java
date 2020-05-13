package com.rossotti.tournament.dto;

import com.rossotti.tournament.enumeration.TemplateType;

public class TemplateDTO {
	private TemplateType templateType;
	private int gridGroups;
	private int gridTeams;
	private int eventDays;
	private int eventLocations;
	private Round round;

	public TemplateType getTemplateType() {
		return templateType;
	}
	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}
	public Boolean isTwoByFourPP() {
		return templateType == TemplateType.two_x_four_pp;
	}
	public Boolean isTwoByFourRR() {
		return templateType == TemplateType.two_x_four_rr;
	}
	public Boolean isFourByThreeRR() {
		return templateType == TemplateType.four_x_three_rr;
	}
	public Boolean isFourByFourPP() {
		return templateType == TemplateType.four_x_four_pp;
	}
	public Boolean isFourByFourRR() {
		return templateType == TemplateType.four_x_four_rr;
	}

	public int getGridGroups() {
		return gridGroups;
	}
	public void setGridGroups(int gridGroups) {
		this.gridGroups = gridGroups;
	}

	public int getGridTeams() {
		return gridTeams;
	}
	public void setGridTeams(int gridTeams) {
		this.gridTeams = gridTeams;
	}

	public int getEventDays() {
		return eventDays;
	}
	public void setEventDays(int eventDays) {
		this.eventDays = eventDays;
	}

	public int getEventLocations() {
		return eventLocations;
	}
	public void setEventLocations(int eventLocations) {
		this.eventLocations = eventLocations;
	}

	public Round getRound() {
		return round;
	}
	public void setRound(Round round) {
		this.round = round;
	}
}