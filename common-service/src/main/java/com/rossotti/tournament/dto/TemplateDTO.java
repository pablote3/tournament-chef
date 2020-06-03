package com.rossotti.tournament.dto;

import com.rossotti.tournament.enumeration.TemplateType;

public class TemplateDTO {
	private TemplateType templateType;
	private int gridGroups;
	private int gridTeams;
	private float eventDays;
	private int eventLocations;
	private RoundDTO roundDTO;
	private GameDTO gameDTO;

	public TemplateType getTemplateType() {
		return templateType;
	}
	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}
	public Boolean isFour_x_four_rr_15D_2L() {
		return templateType == TemplateType.four_x_four_rr_15D_2L;
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

	public float getEventDays() {
		return eventDays;
	}
	public void setEventDays(float eventDays) {
		this.eventDays = eventDays;
	}

	public int getEventLocations() {
		return eventLocations;
	}
	public void setEventLocations(int eventLocations) {
		this.eventLocations = eventLocations;
	}

	public RoundDTO getRoundDTO() {
		return roundDTO;
	}
	public void setRoundDTO(RoundDTO roundDTO) {
		this.roundDTO = roundDTO;
	}

	public GameDTO getGameDTO() {
		return gameDTO;
	}
	public void setGameDTO(GameDTO gameDTO) {
		this.gameDTO = gameDTO;
	}
}