package com.rossotti.tournament.exception;

/**
 * Exception thrown when attempting to use an entity that is in an inactive status.
 */
public class InactiveEntityException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final Class<?> entityClass;

	public InactiveEntityException(Class<?> entityClass) {
		super("The entity " + entityClass + " is in an inactive status.");
		this.entityClass = entityClass;
	}
	public Class<?> getEntityClass() {
		return entityClass;
	}
}
