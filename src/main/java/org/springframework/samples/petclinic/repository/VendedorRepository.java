
package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Vendedor;

public interface VendedorRepository extends CrudRepository<Vendedor, Integer> {

	@Query("SELECT ALL v from Vendedor v where v.user.username =:username")
	Vendedor findByUsername(@Param("username") String username);

	@Query("SELECT vendedor FROM Vendedor vendedor WHERE vendedor.id =:id")
	Vendedor findById2(@Param("id") int id);
}
