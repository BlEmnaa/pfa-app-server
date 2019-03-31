package com.backend.payload;

public class UserSummary {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String birthDate;
    private String address;

    public UserSummary(Long id, String username, String fullName, String email, String birthDate, String address) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
