package com.rossotti.tournament.jpa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class EventTeam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

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
	@JoinColumn(name="organizationTeamId", referencedColumnName="id", nullable=false)
	private OrganizationTeam organizationTeam;
	public OrganizationTeam getOrganizationTeam() {
		return organizationTeam;
	}
	public void setOrganizationTeam(OrganizationTeam organizationTeam) {
		this.organizationTeam = organizationTeam;
	}

	@OneToMany(mappedBy="eventTeam", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<EventTeamRanking> eventTeamRankings = new ArrayList<>();
	public List<EventTeamRanking> getEventTeamRankings()  {
		return eventTeamRankings;
	}
	public void setEventTeamRankings(List<EventTeamRanking> eventTeamRankings)  {
		this.eventTeamRankings = eventTeamRankings;
	}
}
