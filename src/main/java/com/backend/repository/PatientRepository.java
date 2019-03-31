package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.model.User;

@Repository
public interface PatientRepository extends CrudRepository<User, Long> {
		

		@Query(value = "select u from User u join UserRoles ur on u.id = ur.user_id join Role r on ur.role_id = r.id where r.name = 'ROLE_PATIENT' ")
		List <User> findPatients();
	    Boolean existsByUsername(String username) ;
	    Boolean existsByEmail(String email) ;

	}