package com.backend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.json.JSONArray;
import org.json.simple.JSONObject;

public class Rapport extends JSONArray{
	
	@Id
	private Long idRapport;
	
	@NotBlank
	private String dateAnalyse;
	
	@NotBlank
	private Long idPatient;
	
	@NotBlank
	private String namePatient;
	
	@NotBlank
	private String nomDocteur;
	
	@NotBlank
	@Email
	private String mailDocteur;

	private List<Analyse> analyseTab = new ArrayList<>();

	public Long getIdRapport() {
		return idRapport;
	}

	public void setIdRapport(Long idRapport) {
		this.idRapport = idRapport;
	}

	public String getDateAnalyse() {
		return dateAnalyse;
	}

	public void setDateAnalyse(String dateAnalyse) {
		this.dateAnalyse = dateAnalyse;
	}

	public Long getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(Long idPatient) {
		this.idPatient = idPatient;
	}

	public String getNamePatient() {
		return namePatient;
	}

	public void setNamePatient(String namePatient) {
		this.namePatient = namePatient;
	}

	public String getNomDocteur() {
		return nomDocteur;
	}

	public void setNomDocteur(String nomDocteur) {
		this.nomDocteur = nomDocteur;
	}

	public String getMailDocteur() {
		return mailDocteur;
	}

	public void setMailDocteur(String mailDocteur) {
		this.mailDocteur = mailDocteur;
	}

	public List<Analyse> getAnalyseTab() {
		return analyseTab;
	}

	public void setAnalyseTab(List<Analyse> analyseTab) {
		this.analyseTab = analyseTab;
	}
	
}
