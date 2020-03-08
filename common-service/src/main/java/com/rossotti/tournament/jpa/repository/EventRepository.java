package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.jpa.enumeration.TemplateType;
import com.rossotti.tournament.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends Repository<Event, Long> {

	Event findById(Long id);

	List<Event> findAll();

	void save(Event event);

	void deleteById(Long id);

	String findByEventName =
			"select e from Event e " +
			"where e.eventName = :eventName";
	@Query(findByEventName)
	List<Event> findByEventName(@Param("eventName") String eventName);

	String findByOrganizationName =
			"select e from Event e " +
			"inner join e.organization o " +
			"where o.organizationName = :organizationName";
	@Query(findByOrganizationName)
	List<Event> findByOrganizationName(@Param("organizationName") String organizationName);

	String findByEventNameAsOfDate =
			"select e from Event e " +
			"where e.eventName = :eventName " +
			"and e.startDate <= :asOfDate " +
			"and e.endDate >= :asOfDate";
	@Query(findByEventNameAsOfDate)
	List<Event> findByEventNameAsOfDate(@Param("eventName") String eventName, @Param("asOfDate") LocalDate asOfDate);

	String findByOrganizationNameAsOfDate =
			"select e from Event e " +
			"inner join e.organization o " +
			"where o.organizationName = :organizationName " +
			"and e.startDate <= :asOfDate " +
			"and e.endDate >= :asOfDate";
	@Query(findByOrganizationNameAsOfDate)
	List<Event> findByOrganizationNameAsOfDate(@Param("organizationName") String organizationName, @Param("asOfDate") LocalDate asOfDate);

	String findByEventNameAsOfDateTemplateType =
			"select e from Event e " +
			"inner join e.template t " +
			"where e.eventName = :eventName " +
			"and e.startDate <= :asOfDate " +
			"and e.endDate >= :asOfDate " +
			"and t.templateType = :templateType";
	@Query(findByEventNameAsOfDateTemplateType)
	Event findByEventNameAsOfDateTemplateType(@Param("eventName") String eventName, @Param("asOfDate") LocalDate asOfDate, @Param("templateType") TemplateType templateType);

	String findByOrganizationNameAsOfDateTemplateType =
			"select e from Event e " +
			"inner join e.organization o " +
			"inner join e.template t " +
			"where o.organizationName = :organizationName " +
			"and e.startDate <= :asOfDate " +
			"and e.endDate >= :asOfDate " +
			"and t.templateType = :templateType";
	@Query(findByOrganizationNameAsOfDateTemplateType)
	Event findByOrganizationNameAsOfDateTemplateType(@Param("organizationName") String organizationName, @Param("asOfDate") LocalDate asOfDate, @Param("templateType") TemplateType templateType);
}