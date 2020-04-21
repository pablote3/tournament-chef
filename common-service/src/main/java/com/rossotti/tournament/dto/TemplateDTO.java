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

	private Short gridGroups;
	public Short getGridGroups() {
		return gridGroups;
	}
	public void setGridGroups(Short gridGroups) {
		this.gridGroups = gridGroups;
	}

	private Short gridTeams;
	public Short getGridTeams() {
		return gridTeams;
	}
	public void setGridTeams(Short gridTeams) {
		this.gridTeams = gridTeams;
	}

	private Short preliminaryRounds;
	public Short getPreliminaryRounds() {
		return preliminaryRounds;
	}
	public void setPreliminaryRounds(Short preliminaryRounds) {
		this.preliminaryRounds = preliminaryRounds;
	}

	private Boolean playoffs;
	public Boolean getPlayoffs() {
		return playoffs;
	}
	public void setPlayoffs(Boolean playoffs) {
		this.playoffs = playoffs;
	}

	private Boolean quarterFinals;
	public Boolean getQuarterFinals() {
		return quarterFinals;
	}
	public void setQuarterFinals(Boolean quarterFinals) {
		this.quarterFinals = quarterFinals;
	}

	private Boolean semiFinals;
	public Boolean getSemiFinals() {
		return semiFinals;
	}
	public void setSemiFinals(Boolean semiFinals) {
		this.semiFinals = semiFinals;
	}

	private Boolean finals;
	public Boolean getFinals() {
		return finals;
	}
	public void setFinals(Boolean finals) {
		this.finals = finals;
	}
}