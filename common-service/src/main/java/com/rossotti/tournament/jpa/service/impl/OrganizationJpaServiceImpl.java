package com.rossotti.tournament.jpa.service.impl;

import com.rossotti.tournament.exception.NoSuchEntityException;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.repository.OrganizationRepository;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationJpaServiceImpl implements OrganizationJpaService {

	private OrganizationRepository organizationRepository;

	@Override
	public Organization getById(Long id) {
		return organizationRepository.findById(id);
	}

	@Override
	public List<?> listAll() {
		return new ArrayList<>(organizationRepository.findAll());
	}

	@Autowired
	public void setOrganizationRepository(OrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	@Override
	public List<Organization> findByOrganizationName(String organizationName) {
		return organizationRepository.findByOrganizationName(organizationName);
	}

	@Override
	public Organization findByOrganizationNameAsOfDate(String organizationName, LocalDate asOfDate) {
		return organizationRepository.findByOrganizationNameAsOfDate(organizationName, asOfDate);
	}

	@Override
	public Organization findByOrganizationNameStartDateEndDate(String organizationName, LocalDate startDate, LocalDate endDate) {
		return organizationRepository.findByOrganizationNameStartDateEndDate(organizationName, startDate, endDate);
	}

	@Override
	public Organization save(Organization organization) {
		Organization findOrganization = findByOrganizationNameStartDateEndDate(organization.getOrganizationName(), organization.getStartDate(), organization.getEndDate());
		if (findOrganization != null) {
			findOrganization.setAddress1(organization.getAddress1());
			findOrganization.setAddress2(organization.getAddress2());
			findOrganization.setCity(organization.getCity());
			findOrganization.setState(organization.getState());
			findOrganization.setZipCode(organization.getZipCode());
			findOrganization.setCountry(organization.getCountry());
			findOrganization.setContactLastName(organization.getContactLastName());
			findOrganization.setContactFirstName(organization.getContactFirstName());
			findOrganization.setContactEmail(organization.getContactEmail());
			findOrganization.setContactPhone(organization.getContactPhone());
			findOrganization.setLupdUserId(organization.getLupdUserId());
			findOrganization.setLupdTs(LocalDateTime.now());
			organizationRepository.save(findOrganization);
		}
		else {
			organizationRepository.save(organization);
		}
		return organization;
	}

	@Override
	public void delete(Long id) {
		Organization organization = getById(id);
		if (organization != null) {
			organizationRepository.deleteById(organization.getId());
		}
		else {
			throw new NoSuchEntityException(Organization.class);
		}
	}
}