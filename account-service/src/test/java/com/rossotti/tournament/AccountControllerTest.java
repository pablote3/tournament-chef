package com.rossotti.tournament;

import com.rossotti.tournament.controller.AccountController;
import com.rossotti.tournament.dto.OrganizationDTO;
import com.rossotti.tournament.dto.UserDTO;
import com.rossotti.tournament.exception.EntityExistsException;
import com.rossotti.tournament.exception.InactiveEntityException;
import com.rossotti.tournament.exception.UnauthorizedEntityException;
import com.rossotti.tournament.enumeration.UserStatus;
import com.rossotti.tournament.enumeration.UserType;
import com.rossotti.tournament.model.Organization;
import com.rossotti.tournament.model.User;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import com.rossotti.tournament.jpa.service.UserJpaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {
	@Mock
	private UserJpaService userJpaService;

	@Mock
	private OrganizationJpaService organizationJpaService;

	@InjectMocks
	private AccountController accountController;

	@Test
	public void findByUserEmail_foundInactive() {
		when(userJpaService.findByEmail(anyString()))
			.thenReturn(createMockUser(UserStatus.Inactive, UserType.Event));
		InactiveEntityException exception = assertThrows(InactiveEntityException.class, () -> accountController.createAccount(createMockOrganizationDTO()));
		Assert.assertTrue(exception.getMessage().contains("User is in an inactive status"));
	}

	@Test
	public void findByUserEmail_foundUnauthorized() {
		when(userJpaService.findByEmail(anyString()))
			.thenReturn(createMockUser(UserStatus.Active, UserType.Event));
		UnauthorizedEntityException exception = assertThrows(UnauthorizedEntityException.class, () -> accountController.createAccount(createMockOrganizationDTO()));
		Assert.assertTrue(exception.getMessage().contains("User requires additional permissions"));
	}

	@Test
	public void findByOrganizationNameAsOfDate_foundActive() {
		when(userJpaService.findByEmail(anyString()))
			.thenReturn(createMockUser(UserStatus.Active, UserType.Organization));
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(createMockOrganization());
		EntityExistsException exception = assertThrows(EntityExistsException.class, () -> accountController.createAccount(createMockOrganizationDTO()));
		Assert.assertTrue(exception.getMessage().contains("Organization already exists"));
	}

	@Test
	public void findByOrganizationName_foundInactive() {
		when(userJpaService.findByEmail(anyString()))
			.thenReturn(createMockUser(UserStatus.Active, UserType.Organization));
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(null);
		when(organizationJpaService.findByOrganizationName(anyString()))
			.thenReturn(createMockOrganizations());
		InactiveEntityException exception = assertThrows(InactiveEntityException.class, () -> accountController.createAccount(createMockOrganizationDTO()));
		Assert.assertTrue(exception.getMessage().contains("Organization is in an inactive status"));
	}

	@Test
	public void createAccount_persistenceException() {
		when(userJpaService.findByEmail(anyString()))
			.thenReturn(createMockUser(UserStatus.Active, UserType.Organization));
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(null);
		when(organizationJpaService.findByOrganizationName(anyString()))
			.thenReturn(new ArrayList<>());
		when(organizationJpaService.save(any()))
			.thenThrow(PersistenceException.class);
		assertThrows(PersistenceException.class, () -> accountController.createAccount(createMockOrganizationDTO()));
	}

	@Test
	public void createAccount_success() {
		when(userJpaService.findByEmail(anyString()))
			.thenReturn(createMockUser(UserStatus.Active, UserType.Organization));
		when(organizationJpaService.findByOrganizationNameAsOfDate(anyString(), any()))
			.thenReturn(null);
		when(organizationJpaService.findByOrganizationName(anyString()))
			.thenReturn(new ArrayList<>());
		when(organizationJpaService.save(any()))
			.thenReturn(createMockOrganization());
		Organization organization = accountController.createAccount(createMockOrganizationDTO());
		Assert.assertEquals("Bonfantini", organization.getContactLastName());
	}

	private OrganizationDTO createMockOrganizationDTO() {
		OrganizationDTO organization = new OrganizationDTO();
		UserDTO user = new UserDTO();
		user.setUserEmail("elena.linari@gmail.com");
		user.setUserLastName("Linari");
		user.setUserFirstName("Elena");
		user.setUserPassword("elena123");
		organization.setUserDTO(user);
		organization.setOrganizationName("Fiesole School District");
		organization.setOrgAddress1("123 Main Street");
		organization.setOrgAddress2("Suite 101");
		organization.setOrgCity("Fiesole");
		organization.setOrgState("Tuscany");
		organization.setOrgCountry("Italy");
		organization.setOrgZipCode("50014");
		organization.setOrgContactEmail("bonfantini.agnes@telitaly.com");
		organization.setOrgContactLastName("Bonfantini");
		organization.setOrgContactFirstName("Agnes");
		organization.setOrgContactPhone("520-158-1258");
		return organization;
	}

	private User createMockUser(UserStatus userStatus, UserType userType) {
		User user = new User();
		user.setEmail("elena.linari@gmail.com");
		user.setLastName("Linari");
		user.setFirstName("Elena");
		user.setPassword("elena123");
		user.setUserStatus(userStatus);
		user.setUserType(userType);
		return user;
	}

	private Organization createMockOrganization() {
		Organization organization = new Organization();
		organization.setOrganizationName("Fiesole School District");
		organization.setAddress1("123 Main Street");
		organization.setAddress2("Suite 101");
		organization.setCity("Fiesole");
		organization.setState("Tuscany");
		organization.setCountry("Italy");
		organization.setZipCode("50014");
		organization.setContactEmail("bonfantini.agnes@telitaly.com");
		organization.setContactLastName("Bonfantini");
		organization.setContactFirstName("Agnes");
		organization.setContactPhone("520-158-1258");
		return organization;
	}

	private List<Organization> createMockOrganizations() {
		List<Organization> organizations = new ArrayList<>();
		organizations.add(createMockOrganization());
		return organizations;
	}
}