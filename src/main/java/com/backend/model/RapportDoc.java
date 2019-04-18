package com.backend.model;

import java.sql.Blob;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "rapportDocs", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "idRapport"
        })
})
public class RapportDoc {
	
	@Id
	private Long idRapport;
	
	
	@JoinTable(name = "users", 
			joinColumns = @JoinColumn(name = "id"),
			foreignKey = @ForeignKey(name = "fk_idPatient_idUser"))
	private Long idPatient;
	
	@JoinTable(name = "users", 
			joinColumns = @JoinColumn(name = "fullName"),
			foreignKey = @ForeignKey(name = "fk_fullnamePatient_fullName"))
	private String fullnamePatient;
	
	private String nameFile;
	
	private String typeFile;
	
	private String filePath;
	
	private String dateCreation;
	
	
	public RapportDoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFullnamePatient() {
		return fullnamePatient;
	}

	public void setFullnamePatient(String fullnamePatient) {
		this.fullnamePatient = fullnamePatient;
	}

	public RapportDoc(Long idRapport, Long idPatient, String fullnamePatient, String nameFile,
			String typeFile, String filePath, String dateCreation) {
		super();
		this.idRapport = idRapport;
		this.idPatient = idPatient;
		this.fullnamePatient = fullnamePatient;
		this.nameFile = nameFile;
		this.typeFile = typeFile;
		this.filePath = filePath;
		this.dateCreation = dateCreation;
	}

	public Long getIdRapport() {
		return idRapport;
	}

	public void setIdRapport(Long idRapport) {
		this.idRapport = idRapport;
	}

	public Long getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(Long idPatient) {
		this.idPatient = idPatient;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public String getTypeFile() {
		return typeFile;
	}

	public void setTypeFile(String typeFile) {
		this.typeFile = typeFile;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

}
