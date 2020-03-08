package com.rossotti.tournament.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"eventId", "availableTeamId"}))
public class EventTeam {
	@ManyToOne
	@JoinColumn(name="eventId", referencedColumnName="id", nullable=false)
	private Event event;
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}

	@ManyToOne
	@JoinColumn(name="availableTeamId", referencedColumnName="id", nullable=false)
	private AvailableTeam availableTeam;
	public AvailableTeam getAvailableTeam() {
		return availableTeam;
	}
	public void setAvailableTeam(AvailableTeam availableTeam) {
		this.availableTeam = availableTeam;
	}

	@OneToMany(mappedBy="eventTeam", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<EventTeamRanking> eventTeamRankings = new ArrayList<>();
	public List<EventTeamRanking> getEventTeamRankings()  {
		return eventTeamRankings;
	}
	public void setEventTeamRankings(List<EventTeamRanking> eventTeamRankings)  {
		this.eventTeamRankings = eventTeamRankings;
	}

	@OneToMany(mappedBy="eventTeam", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<GameTeam> gameTeams = new ArrayList<>();
	public List<GameTeam> getGameTeams()  {
		return gameTeams;
	}
	public void setGameTeams(List<GameTeam> gameTeams)  {
		this.gameTeams = gameTeams;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
