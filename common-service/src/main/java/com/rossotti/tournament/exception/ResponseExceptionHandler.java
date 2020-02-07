package com.rossotti.tournament.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(ResponseExceptionHandler.class);
	private static final MediaType ERROR_RESPONSE = new MediaType("application", "vnd.vsp.error+json");

	public ResponseExceptionHandler() {
	}

	@ExceptionHandler
	protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
		HttpStatus status;
		ErrorResponse err;
		if (ex instanceof CustomException) {
			status = ((CustomException)ex).getStatus();
			err = ((CustomException)ex).getError();
		} else {
			logger.error("Uncaught Exception ....", ex);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			err = new ErrorResponse();
			err.setDescription(ex.getMessage());
			err.setError(ex.getClass().getName());
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(ERROR_RESPONSE);
		return this.handleExceptionInternal(ex, err, httpHeaders, status, request);
	}
}