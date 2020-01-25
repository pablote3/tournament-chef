package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.jpa.model.Event;
import com.rossotti.tournament.jpa.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EventRepository extends Repository<Event, Long> {

	Event findById(Long id);

	List<Event> findAll();

	void save(Event event);

	void deleteById(Long id);

	String findByOrganizationName =
			"select e from Event e " +
			"inner join e.organization o " +
			"where o.organizationName = :organizationName";
	@Query(findByOrganizationName)
	List<Event> findByOrganizationName(@Param("organizationName") String organizationName);

//	String findByOrganizationNameAndStartDate =
//			"select u from User u " +
//			"inner join u.organization o " +
//			"where u.email = :email " +
//			"and o.organizationName = :organizationName";
//	@Query(findByOrganizationNameAndUserEmail)
//	User findByOrganizationNameAndUserEmail(@Param("organizationName") String organizationName, @Param("email") String email);
}