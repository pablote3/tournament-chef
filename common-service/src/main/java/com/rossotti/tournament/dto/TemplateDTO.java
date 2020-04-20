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

	private Short gridGroupCount;
	public Short getGridGroupCount() {
		return gridGroupCount;
	}
	public void setGridGroupCount(Short gridGroupCount) {
		this.gridGroupCount = gridGroupCount;
	}

	private Short gridTeamCount;
	public Short getGridTeamCount() {
		return gridTeamCount;
	}
	public void setGridTeamCount(Short gridTeamCount) {
		this.gridTeamCount = gridTeamCount;
	}

	private Short roundCount;
	public Short getRoundCount() {
		return roundCount;
	}
	public void setRoundCount(Short roundCount) {
		this.roundCount = roundCount;
	}

	private Boolean playoffGames;
	public Boolean getPlayoffGames() {
		return playoffGames;
	}
	public void setPlayoffGames(Boolean playoffGames) {
		this.playoffGames = playoffGames;
	}

	private Boolean quarterFinalGames;
	public Boolean getQuarterFinalGames() {
		return quarterFinalGames;
	}
	public void setQuarterFinalGames(Boolean quarterFinalGames) {
		this.quarterFinalGames = quarterFinalGames;
	}

	private Boolean semiFinalGames;
	public Boolean getSemiFinalGames() {
		return semiFinalGames;
	}
	public void setSemiFinalGames(Boolean semiFinalGames) {
		this.semiFinalGames = semiFinalGames;
	}
}