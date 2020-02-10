package com.rossotti.tournament.jpa.service.impl;

import com.rossotti.tournament.exception.CustomException;
import com.rossotti.tournament.exception.ValidationMessages;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.repository.OrganizationRepository;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationJpaServiceImpl implements OrganizationJpaService {

	private OrganizationRepository organizationRepository;

	@Autowired
	public void setOrganizationRepository(OrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
	}

	@Override
	public List<Organization> findByOrganizationName(String organizationName) {
		return organizationRepository.findByOrganizationName(organizationName);
	}

	@Override
	public Organization findByOrganizationNameAndAsOfDate(String organizationName, LocalDate asOfDate) {
		return organizationRepository.findByOrganizationNameAndAsOfDate(organizationName, asOfDate);
	}

	@Override
	public Organization findByOrganizationNameStartDateEndDate(String organizationName, LocalDate startDate, LocalDate endDate) {
		return organizationRepository.findByOrganizationNameStartDateEndDate(organizationName, startDate, endDate);
	}

	@Override
	public List<?> listAll() {
        return new ArrayList<>(organizationRepository.findAll());
	}

	@Override
	public Organization getById(Long id) {
		return organizationRepository.findById(id);
	}

	@Override
	public Organization save(Organization organization) throws ResponseStatusException {
//		try {
//			Organization findOrganization = findByOrganizationNameAndAsOfDate(organization.getOrganizationName(), organization.getStartDate(), organization.getEndDate());
//			if (findOrganization != null) {
//				findOrganization.setOrganizationType(organization.getOrganizationType());
//				findOrganization.setOrganizationStatus(organization.getOrganizationStatus());
//				findOrganization.setLastName(organization.getLastName());
//				findOrganization.setFirstName(organization.getFirstName());
//				findOrganization.setPassword(organization.getPassword());
//				organizationRepository.save(findOrganization);
//			}
//			else {
//				organizationRepository.save(organization);
//			}
//		}
//		catch (Exception e) {
//			if (e instanceof TransactionSystemException) {
//				throw new CustomException(e.getCause().getCause().getMessage(), HttpStatus.BAD_REQUEST);
//			}
//			else if (e instanceof ConstraintViolationException) {
//				throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
//			}
//			else {
//				throw new CustomException(ValidationMessages.MSG_VAL_0000, HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		}
		return organization;
	}

	@Override
	public Organization delete(Long id) {
		try {
			Organization organization = getById(id);
			if (organization != null) {
				organizationRepository.deleteById(organization.getId());
				return organization;
			}
			else {
				throw new CustomException(ValidationMessages.MSG_VAL_0012, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch(CustomException ce) {
			throw ce;
		}
		catch(Exception e) {
			throw new CustomException(ValidationMessages.MSG_VAL_0007, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}