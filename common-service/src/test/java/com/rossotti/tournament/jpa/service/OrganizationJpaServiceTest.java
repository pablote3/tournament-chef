package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.exception.CustomException;
import com.rossotti.tournament.jpa.enumeration.OrganizationStatus;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.repository.OrganizationRepositoryTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.rossotti.tournament.config.ServiceConfig.class)
public class OrganizationJpaServiceTest {

	private OrganizationJpaService organizationJpaService;

	@Autowired
	public void setOrganizationJpaService(OrganizationJpaService organizationJpaService) {
		this.organizationJpaService = organizationJpaService;
	}

	@Test
	public void getById_Found() {
		Organization organization = organizationJpaService.getById(1L);
		Assert.assertEquals("FC Juventes", organization.getOrganizationName());
		Assert.assertEquals(5, organization.getUsers().size());
	}

	@Test
	public void findById_NotFound() {
		Assert.assertNull(organizationJpaService.getById(11L));
	}

	@Test
	public void listAll() {
		List<Organization> organizations = (List<Organization>) organizationJpaService.listAll();
		Assert.assertTrue(organizations.size() >= 4);
	}

	@Test
	public void findByOrganizationNameAndAsOfDate_Found() {
		Organization organization = organizationJpaService.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2011, 02, 15));
		Assert.assertEquals("barbara.bonansea@gmail.com", organization.getContactEmail());
	}

	@Test
	public void findByOrganizationNameAndAsOfDate_NotFound() {
		Assert.assertNull(organizationJpaService.findByOrganizationNameAndAsOfDate("FC Juventes", LocalDate.of(2009, 12, 31)));
	}

	@Test
	public void findByOrganizationNameStartDateEndDate_Found() {
		Organization organization = organizationJpaService.findByOrganizationNameStartDateEndDate("FC Juventes", LocalDate.of(2011, 02, 15), LocalDate.of(2012, 02, 15));
		Assert.assertEquals("barbara.bonansea@gmail.com", organization.getContactEmail());
	}

	@Test
	public void findByOrganizationNameStartDateEndDate_NotFound() {
		Assert.assertNull(organizationJpaService.findByOrganizationNameStartDateEndDate("FC Juventes", LocalDate.of(2009, 12, 31), LocalDate.of(2010, 1, 5)));
	}

	@Test
	public void findByOrganizationName_Found() {
		List<Organization> organizations = organizationJpaService.findByOrganizationName("FC Juventes");
		Assert.assertEquals(3, organizations.size());
	}

	@Test
	public void findByOrganizationName_NotFound() {
		List<Organization> organizations = organizationJpaService.findByOrganizationName("Juventes");
		Assert.assertEquals(0, organizations.size());
	}

	@Test
	public void create_Created() {
		organizationJpaService.save(OrganizationRepositoryTest.createMockOrganization("Empoli FC", LocalDate.of(2012, 1, 15), LocalDate.of(2012, 1, 22), "Simonetti", "flaminia.simonetti@dada.it"));
		Organization findOrganization = organizationJpaService.findByOrganizationNameStartDateEndDate("Empoli FC", LocalDate.of(2012, 1, 15), LocalDate.of(2012, 1, 22));
		Assert.assertEquals("Simonetti", findOrganization.getContactLastName());
	}

	@Test
	public void create_ContactEmailIsMandatory_Empty() {
		CustomException exception = assertThrows(CustomException.class, () -> {
			organizationJpaService.save(OrganizationRepositoryTest.createMockOrganization("Empoli FC", LocalDate.of(2011, 1, 15), LocalDate.of(2011, 1, 22), "Di Guglielmo", ""));
		});
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("ContactEmail is mandatory", exception.getError().getErrorMessage());
	}

	@Test
	public void create_ContactEmailIsMandatory_Null() {
		CustomException exception = assertThrows(CustomException.class, () -> {
			organizationJpaService.save(OrganizationRepositoryTest.createMockOrganization("Empoli FC", LocalDate.of(2011, 1, 15), LocalDate.of(2011, 1, 22), "Di Guglielmo", null));
		});
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("ContactEmail is mandatory", exception.getError().getErrorMessage());
	}

	@Test
	public void create_ContactEmailInvalidFormat() {
		CustomException exception = assertThrows(CustomException.class, () -> {
			organizationJpaService.save(OrganizationRepositoryTest.createMockOrganization("Empoli FC", LocalDate.of(2011, 1, 15), LocalDate.of(2011, 1, 22), "Di Guglielmo", "di guglielmo@dada.it"));
		});
		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		Assert.assertEquals("ContactEmail invalid format", exception.getError().getErrorMessage());
	}

//	@Test
//	public void create_PasswordInvalidFormat() {
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			organizationJpaService.save(OrganizationRepositoryTest.createMockOrganization(1L, "bonetti.tatiana@hotmail.com", "Bonetti", "Sup"));
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("Password invalid format", exception.getError().getErrorMessage());
//	}

//	@Test
//	public void update_Updated() {
//		Organization updateOrganization = organizationJpaService.findByOrganizationNameAndAsOfDate("AC Milan SPA", LocalDate.of(2012, 1, 15));
//		updateOrganization.setOrganizationStatus(OrganizationStatus.Active);
//		organizationJpaService.save(updateOrganization);
//		Organization findOrganization = organizationJpaService.findByOrganizationNameAndOrganizationEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
//		Assert.assertEquals(OrganizationStatus.Active, findOrganization.getOrganizationStatus());
//	}

//	@Test
//	public void update_EmailIsMandatory_Empty() {
//		Organization updateOrganization = organizationJpaService.findByOrganizationNameAndOrganizationEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
//		updateOrganization.setOrganizationStatus(OrganizationStatus.Active);
//		updateOrganization.setEmail("");
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			organizationJpaService.save(updateOrganization);
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("Email is mandatory", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void update_LastNameIsMandatory_Null() {
//		Organization updateOrganization = organizationJpaService.findByOrganizationNameAndOrganizationEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
//		updateOrganization.setOrganizationStatus(OrganizationStatus.Active);
//		updateOrganization.setLastName(null);
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			organizationJpaService.save(updateOrganization);
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("LastName is mandatory", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void update_PasswordInvalidFormat() {
//		Organization updateOrganization = organizationJpaService.findByOrganizationNameAndOrganizationEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
//		updateOrganization.setOrganizationStatus(OrganizationStatus.Active);
//		updateOrganization.setPassword("Sup");
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			organizationJpaService.save(updateOrganization);
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("Password invalid format", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void update_EmailInvalidFormat() {
//		Organization updateOrganization = organizationJpaService.findByOrganizationNameAndOrganizationEmail("FC Juventes", "alessia.piazza@telecomitalia.com");
//		updateOrganization.setEmail("alessia.piazza_telecomitalia.com");
//		updateOrganization.setOrganizationStatus(OrganizationStatus.Active);
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			organizationJpaService.save(updateOrganization);
//		});
//		Assert.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
//		Assert.assertEquals("Email invalid format", exception.getError().getErrorMessage());
//	}
//
//	@Test
//	public void delete_Deleted() {
//		organizationJpaService.delete(7L);
//		Assert.assertNull(organizationJpaService.getById(7L));
//	}
//
//	@Test
//	public void delete_NotFound() {
//		CustomException exception = assertThrows(CustomException.class, () -> {
//			organizationJpaService.delete(21L);
//		});
//		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
//		Assert.assertEquals("Server error when trying to find record for id of {}", exception.getError().getErrorMessage());
//		Assert.assertEquals("MSG_VAL_0012", exception.getError().getError());
//	}
}