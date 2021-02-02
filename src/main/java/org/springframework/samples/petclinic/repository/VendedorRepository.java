
package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.model.Videojuego;

public interface VendedorRepository extends CrudRepository<Vendedor, Integer> {

	
	@Query("SELECT ALL v from Vendedor v where v.user.username =:username")
	public Vendedor findByUsername(@Param("username") String username);
	}
