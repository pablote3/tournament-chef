package com.rossotti.tournament.jpa.model;

import javax.persistence.*;

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
}
