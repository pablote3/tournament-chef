package com.rossotti.tournament.jpa.service;

import java.util.List;

public interface CrudService<T> {
	List<?> listAll();

	T getById(Long id);

	T save(T domainObject);

	T delete(Long id);
}
