package com.rossotti.tournament.exception;

/**
 * Exception thrown when attempting to use an entity without required user permissions.
 */
public class UnauthorizedEntityException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final Class<?> entityClass;

	public UnauthorizedEntityException(Class<?> entityClass) {
		super("The entity " + entityClass + " requires additional permissions.");
		this.entityClass = entityClass;
	}
	public Class<?> getEntityClass() {
		return entityClass;
	}
}
