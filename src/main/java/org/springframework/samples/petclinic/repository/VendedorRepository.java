
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.model.Videojuego;

public interface VendedorRepository extends CrudRepository<Vendedor, Integer> {

	
	@Query("SELECT peliculas FROM Vendedor vendedor WHERE vendedor.id =: id")
	public Collection<Pelicula> peliculasVendedor(@Param("vendedorId") int id);
	
	@Query("SELECT videojuegos FROM Vendedor vendedor WHERE vendedor.id =: id")
	public Collection<Videojuego> videojuegosVendedor(@Param("vendedorId") int id);
	
	@Query("SELECT merchandasings FROM Vendedor vendedor WHERE vendedor.id =: id")
	public Collection<Merchandasing> merchandasingsVendedor(@Param("vendedorId") int id);
	
}
