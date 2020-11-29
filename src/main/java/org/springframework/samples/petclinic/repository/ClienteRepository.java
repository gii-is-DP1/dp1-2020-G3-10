package org.springframework.samples.petclinic.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Plataforma;
import org.springframework.samples.petclinic.model.User;


public interface ClienteRepository extends  CrudRepository<Cliente, Integer>{
	
	
}
