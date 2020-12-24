package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Comentario;

public interface ComentarioRepository extends CrudRepository<Comentario, Integer>{
	
	@Query("SELECT comentario FROM Comentario comentario WHERE comentario.id =:id")
	public Comentario findById(@Param("id") int id);

}
