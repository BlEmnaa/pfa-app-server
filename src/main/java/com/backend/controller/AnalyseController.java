package com.backend.controller;

import java.util.ArrayList;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.message.response.ResponseMessage;
import com.backend.model.Analyse;
import com.backend.repository.AnalyseRepository;


@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class AnalyseController {
 
  @Autowired
  AnalyseRepository repository;
 
  @GetMapping("/analyses")
  @Secured({"ROLE_PL","ROLE_ADMIN"})
  public List<Analyse> getAllAnalyses() {
    System.out.println("Get all Analyses...");
 
    List<Analyse> analyses = new ArrayList<>();
    repository.findAll().forEach(analyses::add);
 
    return analyses;
  }
 
  @PostMapping(value = "/analyses/create")
  @Secured({"ROLE_ADMIN"})
  public ResponseEntity<?> postAnalyse(@RequestBody Analyse analyse) {
 
	  repository.save(new Analyse(analyse.getAnalyseDemande(),analyse.getResultat(),analyse.getValNormal()));
	  
	  return new ResponseEntity<>(new ResponseMessage("Analyse has been Added!"), HttpStatus.OK);
  }
 
  @DeleteMapping("/analyses/{id}")
  @Secured({"ROLE_ADMIN"})
  public ResponseEntity<String> deleteAnalyse(@PathVariable("id") long id) {
    System.out.println("Delete Analyse with ID = " + id + "...");
 
    repository.deleteById(id);
 
    return new ResponseEntity<>("Analyse has been deleted!", HttpStatus.OK);
  }
 
 
  @PutMapping("/analyse/{id}")
  @Secured({"ROLE_ADMIN"})
  public ResponseEntity<?> updateAnalyse(@PathVariable("id") long id, @RequestBody Analyse analyse) {
    System.out.println("Update Analyse with ID = " + id + "...");
    try {
      Optional<Analyse> analyseData = repository.findById(id);
 
        if (analyseData.isPresent()) {
    	    Analyse _analyse = analyseData.get();
    	    Analyse an = new Analyse();
    	      an.setId(id);
              an.setAnalyseDemande((analyse.getAnalyseDemande() != null ? analyse.getAnalyseDemande(): _analyse.getAnalyseDemande()));
              an.setResultat( (analyse.getResultat() != null ? analyse.getResultat(): _analyse.getResultat() ) );
              an.setValNormal( (analyse.getValNormal() != null ? analyse.getValNormal(): _analyse.getValNormal() ) );
             repository.save(an);
         } 
        return new ResponseEntity<>(new ResponseMessage("Analyse has been Modified!"), HttpStatus.OK);
    } catch (Exception e) {
    	return new ResponseEntity<>(new ResponseMessage(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
  }
}