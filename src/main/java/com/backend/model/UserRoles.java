package com.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "user_roles")
public class UserRoles {
	
	@Id
    private Long user_id;

    @NaturalId
    private Long role_id;

    public UserRoles() {}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	public UserRoles(Long user_id, Long role_id) {
		super();
		this.user_id = user_id;
		this.role_id = role_id;
	}

    
}
