package com.rossotti.tournament.exception;

/**
 * Exception thrown when attempting to use an entity that already exists.
 */
public class EntityExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final Class<?> entityClass;

	public EntityExistsException(Class<?> entityClass) {
		super("The entity " + entityClass + " already exists.");
		this.entityClass = entityClass;
	}
	public Class<?> getEntityClass() {
		return entityClass;
	}
}
