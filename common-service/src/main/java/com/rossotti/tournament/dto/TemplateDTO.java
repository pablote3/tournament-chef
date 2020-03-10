package com.rossotti.tournament.dto;

import com.rossotti.tournament.enumeration.GroupPlay;
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

	private Short gridGroupRound1;
	public Short getGridGroupRound1() {
		return gridGroupRound1;
	}
	public void setGridGroupRound1(Short gridGroupRound1) {
		this.gridGroupRound1 = gridGroupRound1;
	}

	private Short gridTeamsRound1;
	public Short getGridTeamsRound1() {
		return gridTeamsRound1;
	}
	public void setGridTeamsRound1(Short gridTeamsRound1) {
		this.gridTeamsRound1 = gridTeamsRound1;
	}

	private GroupPlay groupPlay1;
	public GroupPlay getGroupPlay1() {
		return groupPlay1;
	}
	public void setGroupPlay1(GroupPlay groupPlay1) {
		this.groupPlay1 = groupPlay1;
	}
	public Boolean isGroupPlay1Pairing() {
		return groupPlay1 == GroupPlay.PoolPlay;
	}
	public Boolean isGroupPlay1RoundRobin() {
		return groupPlay1 == GroupPlay.RoundRobin;
	}
	public Boolean isGroupPlay1None() {
		return groupPlay1 == GroupPlay.None;
	}

	private Short groupPlayoffGamesRound1;
	public Short getGroupPlayoffGamesRound1() {
		return groupPlayoffGamesRound1;
	}
	public void setGroupPlayoffGamesRound1(Short groupPlayoffGamesRound1) {
		this.groupPlayoffGamesRound1 = groupPlayoffGamesRound1;
	}

	private Short gridGroupRound2;
	public Short getGridGroupRound2() {
		return gridGroupRound2;
	}
	public void setGridGroupRound2(Short gridGroupRound2) {
		this.gridGroupRound2 = gridGroupRound2;
	}

	private Short gridTeamsRound2;
	public Short getGridTeamsRound2() {
		return gridTeamsRound2;
	}
	public void setGridTeamsRound2(Short gridTeamsRound2) {
		this.gridTeamsRound2 = gridTeamsRound2;
	}

	private GroupPlay groupPlay2;
	public GroupPlay getGroupPlay2() {
		return groupPlay2;
	}
	public void setGroupPlay2(GroupPlay groupPlay2) {
		this.groupPlay2 = groupPlay2;
	}
	public Boolean isGroupPlay2Pairing() {
		return groupPlay2 == GroupPlay.PoolPlay;
	}
	public Boolean isGroupPlay2RoundRobin() {
		return groupPlay2 == GroupPlay.RoundRobin;
	}
	public Boolean isGroupPlay2None() {
		return groupPlay2 == GroupPlay.None;
	}

	private Short groupPlayoffGamesRound2;
	public Short getGroupPlayoffGamesRound2() {
		return groupPlayoffGamesRound2;
	}
	public void setGroupPlayoffGamesRound2(Short groupPlayoffGamesRound2) {
		this.groupPlayoffGamesRound2 = groupPlayoffGamesRound2;
	}

	private Short quarterFinalGames;
	public Short getQuarterFinalGames() {
		return quarterFinalGames;
	}
	public void setQuarterFinalGames(Short quarterFinalGames) {
		this.quarterFinalGames = quarterFinalGames;
	}

	private Short semiFinalGames;
	public Short getSemiFinalGames() {
		return semiFinalGames;
	}
	public void setSemiFinalGames(Short semiFinalGames) {
		this.semiFinalGames = semiFinalGames;
	}
}