package com.rossotti.tournament.jpa.model;

import javax.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
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

	@LastModifiedDate
	private LocalDateTime updateTs;
	public LocalDateTime getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(LocalDateTime updateTs) {
		this.updateTs = updateTs;
	}
}
