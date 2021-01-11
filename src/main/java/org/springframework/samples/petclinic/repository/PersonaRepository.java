package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Persona;

public interface PersonaRepository extends  CrudRepository<Persona, Integer> {

	
	
}
