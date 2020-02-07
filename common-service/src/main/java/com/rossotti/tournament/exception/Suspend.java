package com.rossotti.tournament.exception;

import java.io.Serializable;

public class Suspend implements Serializable {
	private String code;
	private String description;

	public Suspend() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}