package com.rossotti.tournament.dto;

import com.rossotti.tournament.enumeration.TemplateType;

public class TemplateDTO {
	private TemplateType templateType;
	private int gridGroups;
	private int gridTeams;
	private int eventDays;
	private int eventLocations;
	private int preliminaryRounds;
	private boolean playoffs1;
	private boolean playoffs2;
	private boolean quarterFinals;
	private boolean semiFinals;
	private boolean finals;

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

	public int getPreliminaryRounds() {
		return preliminaryRounds;
	}
	public void setPreliminaryRounds(int preliminaryRounds) {
		this.preliminaryRounds = preliminaryRounds;
	}

	public boolean getPlayoffs1() {
		return playoffs1;
	}
	public void setPlayoffs1(boolean playoffs1) {
		this.playoffs1 = playoffs1;
	}

	public boolean getPlayoffs2() {
		return playoffs2;
	}
	public void setPlayoffs2(boolean playoffs2) {
		this.playoffs2 = playoffs2;
	}

	public boolean getQuarterFinals() {
		return quarterFinals;
	}
	public void setQuarterFinals(boolean quarterFinals) {
		this.quarterFinals = quarterFinals;
	}

	public boolean getSemiFinals() {
		return semiFinals;
	}
	public void setSemiFinals(boolean semiFinals) {
		this.semiFinals = semiFinals;
	}

	public boolean getFinals() {
		return finals;
	}
	public void setFinals(boolean finals) {
		this.finals = finals;
	}
}