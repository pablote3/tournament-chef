package com.rossotti.tournament.dto;

import com.rossotti.tournament.enumeration.TemplateType;
import com.rossotti.tournament.enumeration.TournamentType;

public class TemplateDTO {
	private TemplateType templateType;
	private TournamentType tournamentType;
	private int gridGroups;
	private int gridTeams;
	private int totalTeams;
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

	public TournamentType getTournamentType() {
		return tournamentType;
	}
	public void setTournamentType(TournamentType tournamentType) {
		this.tournamentType = tournamentType;
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

	public int getTotalTeams() {
		return totalTeams;
	}
	public void setTotalTeams(int totalTeams) {
		this.totalTeams = totalTeams;
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