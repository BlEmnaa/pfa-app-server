package com.backend.model;

import java.util.HashMap;

import java.util.Map;

import javax.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.json.JSONArray;
import org.json.simple.JSONObject;

@Entity
@Table(name="analyses")
public class Analyse {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotBlank
    private String analyseDemande;

	private String resultat;
    
	@NotBlank
    private String valNormal;


    public Analyse() {}
	
	public Analyse(String analyseDemande, String resultat, String valNormal) {
		this.analyseDemande = analyseDemande;
		this.resultat = resultat;
		this.valNormal = valNormal;
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
	
} 