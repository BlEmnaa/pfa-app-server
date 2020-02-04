package com.backend.repository;

import java.util.List;


import org.springframework.data.repository.CrudRepository;

import com.backend.model.Analyse;

public interface AnalyseRepository extends CrudRepository<Analyse, Long> {

	List<Analyse> findAll();
}