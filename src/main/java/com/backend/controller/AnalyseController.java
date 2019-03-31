package com.backend.controller;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.Analyse;
import com.backend.repository.AnalyseRepository;

@RestController
@RequestMapping("/api")
public class AnalyseController {
 
  @Autowired
  AnalyseRepository repository;
 
  @GetMapping("/analyses")
  public List<Analyse> getAllAnalyses() {
    System.out.println("Get all Analyses...");
 
    List<Analyse> analyses = new ArrayList<>();
    repository.findAll().forEach(analyses::add);
 
    return analyses;
  }
 
  @PostMapping(value = "/analyses/create")
  public Analyse postAnalyse(@RequestBody Analyse analyse) {
 
	  Analyse _analyse = repository.save(new Analyse(analyse.getAnalyseDemande(),analyse.getResultat(),analyse.getValNormal(),analyse.getRemarque(),analyse.getDoctorName(),analyse.getEmailDoctor()));
    return _analyse;
  }
 
  @DeleteMapping("/analyses/{id}")
  public ResponseEntity<String> deleteAnalyse(@PathVariable("id") long id) {
    System.out.println("Delete Analyse with ID = " + id + "...");
 
    repository.deleteById(id);
 
    return new ResponseEntity<>("Analyse has been deleted!", HttpStatus.OK);
  }
 
 
  @PutMapping("/analyse/{id}")
  public ResponseEntity<Analyse> updateAnalyse(@PathVariable("id") long id, @RequestBody Analyse analyse) {
    System.out.println("Update Analyse with ID = " + id + "...");
 
    Optional<Analyse> analyseData = repository.findById(id);
 
    if (analyseData.isPresent()) {
    	Analyse _analyse = analyseData.get();
      _analyse.setAnalyseDemande((analyse.getAnalyseDemande()));
      _analyse.setResultat(analyse.getResultat());
      _analyse.setValNormal(analyse.getValNormal());
      _analyse.setRemarque(analyse.getRemarque());
      _analyse.setDoctorName(analyse.getDoctorName());
      _analyse.setEmailDoctor(analyse.getEmailDoctor());
      return new ResponseEntity<>(repository.save(_analyse), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}