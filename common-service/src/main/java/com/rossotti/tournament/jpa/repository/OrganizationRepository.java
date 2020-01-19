package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.jpa.model.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrganizationRepository extends Repository<Organization, Long> {

	Organization findById(Long id);

	List<Organization> findAll();

	void save(Organization team);

	void deleteById(Long id);

	List<Organization> findByOrganizationName(String organizationName);

	String findByOrganizationNameAndAsOfDate =
			"select o from Organization o " +
			"where o.organizationName = :organizationName " +
			"and o.startDate <= :asOfDate " +
			"and o.endDate >= :asOfDate";
	@Query(findByOrganizationNameAndAsOfDate)
	Organization findByOrganizationNameAndAsOfDate(@Param("organizationName") String organizationName, @Param("asOfDate") LocalDate asOfDate);
}