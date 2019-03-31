package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.model.UserRoles;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
	
	

}
