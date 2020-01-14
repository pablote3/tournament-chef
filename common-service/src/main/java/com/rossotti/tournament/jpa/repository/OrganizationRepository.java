package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.jpa.model.Organization;
import org.springframework.data.repository.Repository;
import java.util.List;

public interface OrganizationRepository extends Repository<Organization, Long> {

	List<Organization> findAll();

	Organization findById(Long id);

	void save(Organization team);

	void deleteById(Long id);

	Organization findByOrganizationName(String organizationName);
}