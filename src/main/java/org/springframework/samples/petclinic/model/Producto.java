
package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "producto")
public class Producto extends BaseEntity {

	@NotEmpty
	private Double				precio;

	
	/*
	 * 
	 * A TODAS ESTAS VARIABLES HAY QUE PONERLE LAS TAGS CON LAS RELACIONES
	@NotEmpty
	private Videojuego videojuego;

	@NotEmpty
	private Merchandasing		merchandising;
	
	@NotEmpty
	private Pelicula			pelicula;

	@NotEmpty
	private List<Vendedor>		vendedores;

	@NotEmpty
	private List<Comentario>	comentarios;
	
	*/

}
