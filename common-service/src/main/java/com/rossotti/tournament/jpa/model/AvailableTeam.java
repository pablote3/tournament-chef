package com.rossotti.tournament.jpa.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "teamType")
public class AvailableTeam extends BaseEntity {

	@OneToMany(mappedBy="availableTeam", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<EventTeam> eventTeams = new ArrayList<>();
	public List<EventTeam> getEventTeams()  {
		return eventTeams;
	}
	public void setEventTeams(List<EventTeam> eventTeams)  {
		this.eventTeams = eventTeams;
	}

	@Column(length=50, nullable=false)
	@NotBlank(message="TeamName is mandatory")
	private String teamName;
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
}
