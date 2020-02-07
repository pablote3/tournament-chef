package com.rossotti.tournament.exception;

public class ErrorResponse {
	private String error;
	private String description;
	private Suspend[] details;

	public ErrorResponse() {
	}

	public Suspend[] getDetails() {
		if (this.details == null) {
			this.details = new Suspend[0];
		}

		return this.details;
	}

	public void setDetails(Suspend[] details) {
		this.details = details;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
