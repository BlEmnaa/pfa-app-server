package com.backend.model;

import javax.persistence.Entity;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "analyses")
public class Analyse{
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String analyseDemande;


    @NotBlank
    private String resultat;
    
    @NotBlank
    private String valNormal;
    
    @NotBlank
    private String remarque;
    
    @NotBlank
    @Size(max = 50)
    private String doctorName;
    
    @NotBlank
    @Size(max = 50)
    @Email
    private String emailDoctor;
    
   


	public Analyse() {}
	

	public Analyse(@NotBlank String analyseDemande, @NotBlank String resultat, @NotBlank String valNormal,
			@NotBlank String remarque, @NotBlank @Size(max = 50) String doctorName,
			@NotBlank @Size(max = 50) @Email String emailDoctor) {
		super();
		this.analyseDemande = analyseDemande;
		this.resultat = resultat;
		this.valNormal = valNormal;
		this.remarque = remarque;
		this.doctorName = doctorName;
		this.emailDoctor = emailDoctor;
		
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnalyseDemande() {
		return analyseDemande;
	}

	public void setAnalyseDemande(String analyseDemande) {
		this.analyseDemande = analyseDemande;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public String getValNormal() {
		return valNormal;
	}

	public void setValNormal(String valNormal) {
		this.valNormal = valNormal;
	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getEmailDoctor() {
		return emailDoctor;
	}

	public void setEmailDoctor(String emailDoctor) {
		this.emailDoctor = emailDoctor;
	}

    

} 