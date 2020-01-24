package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.jpa.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UserRepository extends Repository<User, Long> {

	User findById(Long id);

	List<User> findAll();

	void save(User team);

	void deleteById(Long id);

	List<User> findByEmail(String email);

	String findByOrganizationNameAndUserEmail =
			"select u from User u " +
			"inner join u.organization o " +

			"where u.email = :email " +
			"and o.organizationName = :organizationName";
	@Query(findByOrganizationNameAndUserEmail)
	User findByOrganizationNameAndUserEmail(@Param("organizationName") String organizationName, @Param("email") String email);
}