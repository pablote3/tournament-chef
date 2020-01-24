package com.rossotti.tournament.jpa.model;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedBy;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime createTs;
	public LocalDateTime getCreateTs() {
		return createTs;
	}
	public void setCreateTs(LocalDateTime createTs) {
		this.createTs = createTs;
	}

	@Column(nullable=false)
	@UpdateTimestamp
	private LocalDateTime lupdTs;
	public LocalDateTime getLupdTs() {
		return lupdTs;
	}
	public void setLupdTs(LocalDateTime lupdTs) {
		this.lupdTs = lupdTs;
	}

	@Column(nullable=false)
	@LastModifiedBy
	private Long lupdUserId;
	public Long getLupdUserId() {
		return lupdUserId;
	}
	public void setLupdUserId(Long lupdUserId) {
		this.lupdUserId = lupdUserId;
	}
}
