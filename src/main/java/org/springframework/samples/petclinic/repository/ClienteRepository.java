package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Comentario;


public interface ClienteRepository extends  CrudRepository<Cliente, Integer>{
	
	@Query("SELECT cliente FROM Cliente cliente WHERE cliente.id =:id")
	public Cliente findById(@Param("id") int id);
	
	@Query("SELECT ALL c from Cliente c where c.user.username =:username")
	public Cliente findByUsername(@Param("username") String username);
	
	
}
