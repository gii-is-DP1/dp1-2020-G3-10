package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.User;


public interface ClienteRepository extends  CrudRepository<Cliente, String>{
	
}
