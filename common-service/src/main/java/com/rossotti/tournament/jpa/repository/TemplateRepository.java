package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.jpa.model.Template;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemplateRepository extends Repository<Template, Long> {

	Template findById(Long id);

	List<Template> findAll();

	void save(Template template);

	void deleteById(Long id);

//	List<Template> findByEmail(String email);

//	String findByOrganizationNameAndTemplateEmail =
//			"select u from Template u " +
//			"inner join u.organization o " +
//			"where u.email = :email " +
//			"and o.organizationName = :organizationName";
//	@Query(findByOrganizationNameAndTemplateEmail)
//	Template findByOrganizationNameAndTemplateEmail(@Param("organizationName") String organizationName, @Param("email") String email);
}