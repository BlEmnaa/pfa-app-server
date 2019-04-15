package com.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.backend.model.RapportDoc;


@Repository
public interface RapportDocRepository extends CrudRepository<RapportDoc, Long> {
	
	List<RapportDoc> findAll();
	
	List<RapportDoc> findByDateCreationAndIdPatient(String dateCreation, Long id);
	
	List<RapportDoc> findByIdPatient(Long id);
	
	List<RapportDoc> findByfullnamePatient(String fullName);
	
	RapportDoc findByIdRapport(Long id);

}
