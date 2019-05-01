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
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.backend.repository.PersonnelRepository;
import com.backend.repository.RoleRepository;
import com.backend.repository.UserRepository;
import com.backend.repository.UserRolesRepository;
import com.backend.security.jwt.JwtProvider;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class PersonnelController {
 
  @Autowired
  PersonnelRepository repository;
  
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
 
  @GetMapping("/personnels")
  public List<User> getAllPersonnels() {
    System.out.println("Get all Personnels...");
 
    List<User> personnels = new ArrayList<>();
    repository.findPersonels().forEach(personnels::add);
 
    return personnels;
  }
 
  @PostMapping(value = "/personnels/create")
  public ResponseEntity<?> postPersonnel(@Valid @RequestBody SignUpForm signUpRequest) {
	  if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User personnel = new User(signUpRequest.getFullName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getBirthDate(), 
				signUpRequest.getAddress());
		
		Set<Role> roles = new HashSet<>();
		
		Role userRole = roleRepository.findByName(RoleName.ROLE_PL)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
		roles.add(userRole);
		personnel.setRoles(roles);
		
		userRepository.save(personnel);
		
		return new ResponseEntity<>(new ResponseMessage("Lab Personnel registered successfully!"), HttpStatus.OK);
  }
 
  @DeleteMapping("/personnels/{id}")
  public ResponseEntity<?> deletePersonnel(@PathVariable("id") long id) {
    System.out.println("Delete Personnel with ID = " + id + "...");
 
    userRolesRepo.deleteById(id);
    userRepository.deleteById(id);
 
    return new ResponseEntity<>(new ResponseMessage("Personnel has been deleted!"), HttpStatus.OK);
  }

 
  @PostMapping("/personnels/{id}")
  public ResponseEntity<?> updatePersonnel(@PathVariable("id") long id, @RequestBody User personnel) {
    System.out.println("Update Personnel with ID = " + id + "...");
    try {
      Optional<User> personnelData = repository.findById(id);
    	 
    	    if (personnelData.isPresent()) {
    	    	User _personnel = personnelData.get();
    	    	User per = new User();
    	    	per.setFullName( (personnel.getFullName() != null ? personnel.getFullName() : _personnel.getFullName()) );
    	    	per.setBirthDate( (personnel.getBirthDate() != null ? personnel.getBirthDate() : _personnel.getBirthDate()));
    	    	per.setAddress( (personnel.getAddress() != null ? personnel.getAddress() : _personnel.getAddress()));
    			
        		repository.save(per);
    	    }
        return new ResponseEntity<>(new ResponseMessage("Personnel has been Modified!"), HttpStatus.OK);
    } catch (Exception e) {
    	return new ResponseEntity<>(new ResponseMessage(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
 

  }
}