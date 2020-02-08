package com.rossotti.tournament.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
	private HttpStatus status;
	private ErrorResponse error;

	public CustomException() {
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public CustomException(String message) {
		super(message);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public CustomException(String message, HttpStatus status) {
		super(message);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
		this.status = status;
	}

	public CustomException(String message, HttpStatus status, ErrorResponse errorResponse) {
		super(message);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
		this.status = status;
		this.error = errorResponse;
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public CustomException(String message, Throwable cause, HttpStatus status) {
		super(message, cause);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
		this.status = status;
	}

	public CustomException(Throwable cause) {
		super(cause);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	protected CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public HttpStatus getStatus() {
		return this.status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public ErrorResponse getError() {
		if (this.error == null) {
			this.error = new ErrorResponse();
			this.error.setDescription(this.getMessage());
			this.error.setError("CustomException");
			this.error.setErrorMessage(getErrorMessage(this.getMessage()));
		}
		return this.error;
	}

	private String getErrorMessage(String message) {
		String messageTemplate = "messageTemplate='";
		int index = message.indexOf(messageTemplate) + messageTemplate.length();
		return message.substring(index, message.length()-4);
	}

	public void setError(ErrorResponse error) {
		this.error = error;
	}
}