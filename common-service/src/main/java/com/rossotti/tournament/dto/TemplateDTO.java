package com.rossotti.tournament.dto;

import com.rossotti.tournament.enumeration.TemplateType;

public class TemplateDTO {
	private TemplateType templateType;
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

	private int gridGroups;
	public int getGridGroups() {
		return gridGroups;
	}
	public void setGridGroups(int gridGroups) {
		this.gridGroups = gridGroups;
	}

	private int gridTeams;
	public int getGridTeams() {
		return gridTeams;
	}
	public void setGridTeams(int gridTeams) {
		this.gridTeams = gridTeams;
	}

	private int preliminaryRounds;
	public int getPreliminaryRounds() {
		return preliminaryRounds;
	}
	public void setPreliminaryRounds(int preliminaryRounds) {
		this.preliminaryRounds = preliminaryRounds;
	}

	private boolean playoffs;
	public boolean getPlayoffs() {
		return playoffs;
	}
	public void setPlayoffs(boolean playoffs) {
		this.playoffs = playoffs;
	}

	private boolean quarterFinals;
	public boolean getQuarterFinals() {
		return quarterFinals;
	}
	public void setQuarterFinals(boolean quarterFinals) {
		this.quarterFinals = quarterFinals;
	}

	private boolean semiFinals;
	public boolean getSemiFinals() {
		return semiFinals;
	}
	public void setSemiFinals(boolean semiFinals) {
		this.semiFinals = semiFinals;
	}

	private boolean finals;
	public boolean getFinals() {
		return finals;
	}
	public void setFinals(boolean finals) {
		this.finals = finals;
	}
}