package com.rossotti.tournament.exception;

/**
 * Exception thrown when attempting to use when an object is not properly initialized.
 */
public class InitializationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final Class<?> entityClass;

	public InitializationException(Class<?> entityClass) {
		super("The object " + entityClass + " is in an inactive status.");
		this.entityClass = entityClass;
	}
	public Class<?> getEntityClass() {
		return entityClass;
	}
}
