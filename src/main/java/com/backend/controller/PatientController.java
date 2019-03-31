package com.backend.controller;

import java.util.ArrayList;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.message.request.SignUpForm;
import com.backend.message.response.ResponseMessage;
import com.backend.model.Role;
import com.backend.model.RoleName;
import com.backend.model.User;
import com.backend.repository.PatientRepository;
import com.backend.repository.RoleRepository;
import com.backend.repository.UserRepository;
import com.backend.repository.UserRolesRepository;
import com.backend.security.jwt.JwtProvider;

@RestController
@RequestMapping("/api")
public class PatientController {
 
  @Autowired
  PatientRepository repository;
  
  @Autowired
  AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRolesRepository userRolesRepo;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;
 
  @GetMapping("/patients")
  public List<User> getAllPatients() {
    System.out.println("Get all Patients...");
 
    List<User> patients = new ArrayList<>();
    repository.findPatients().forEach(patients::add);
 
    return patients;
  }
 
  @PostMapping(value = "/patients/create")
  public ResponseEntity<?> postPatient(@Valid @RequestBody SignUpForm signUpRequest) {
	  if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User patient = new User(signUpRequest.getFullName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getBirthDate(), 
				signUpRequest.getAddress());
		
		Set<Role> roles = new HashSet<>();
		
		Role userRole = roleRepository.findByName(RoleName.ROLE_PATIENT)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
		roles.add(userRole);
		patient.setRoles(roles);
		
		userRepository.save(patient);
		
		return new ResponseEntity<>(new ResponseMessage("Patient registered successfully!"), HttpStatus.OK);
  }
 
  @DeleteMapping("/patients/{id}")
  public ResponseEntity<?> deletePatient(@PathVariable("id") long id) {
    System.out.println("Delete Patient with ID = " + id + "...");
 
    userRolesRepo.deleteById(id);
    userRepository.deleteById(id);
 
    return new ResponseEntity<>(new ResponseMessage("Patient has been deleted!"), HttpStatus.OK);
  }

 
  @PutMapping("/patients/{id}")
  public ResponseEntity<?> updatePatient(@PathVariable("id") long id, @RequestBody User patient) {
    System.out.println("Update Patient with ID = " + id + "...");
    try {
      Optional<User> patientData = repository.findById(id);
    	 
    	    if (patientData.isPresent()) {
    	    	User _patient = patientData.get();
    	    	User pat = new User();
    	    	pat.setId(id);
    	    	pat.setFullName( (patient.getFullName() != null ? patient.getFullName() : _patient.getFullName()) );
    	    	pat.setUsername( (patient.getUsername() != null ? patient.getUsername() : _patient.getUsername()) );
    	    	pat.setPassword( (patient.getPassword() != null ? patient.getPassword() : _patient.getPassword()) );
    	    	pat.setEmail( (patient.getEmail() != null ? patient.getEmail() : _patient.getEmail()) );
    	    	pat.setBirthDate( (patient.getBirthDate() != null ? patient.getBirthDate() : _patient.getBirthDate()));
    	    	pat.setAddress( (patient.getAddress() != null ? patient.getAddress() : _patient.getAddress()));
    	      repository.save(pat);
    	    }
        return new ResponseEntity<>(new ResponseMessage("Patient has been Modified!"), HttpStatus.OK);
    } catch (Exception e) {
    	return new ResponseEntity<>(new ResponseMessage(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
 

  }
}
 
