package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.model.Organization;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface OrganizationJpaService extends CrudService<Organization> {
	List<Organization> findByOrganizationName(String organizationName);
	Organization findByOrganizationNameAsOfDate(String organizationName, LocalDate asOfDate);
	Organization findByOrganizationNameStartDateEndDate(String organizationName, LocalDate startDate, LocalDate endDate);
}