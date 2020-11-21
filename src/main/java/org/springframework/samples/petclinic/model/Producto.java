
package org.springframework.samples.petclinic.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "producto")
public class Producto extends BaseEntity {
	
	/*
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "videojuego_id")
	@Valid
	private Videojuego Videojuego;
	*/
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "merchandasing_id")
	@Valid
	private Merchandasing merchandasing;
	
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pelicula_id")
	@Valid
	private Pelicula pelicula;
	
	/*
	@OneToMany(mappedBy = "producto")
	private Collection<@Valid Vendedor>	vendedores;
    */
	/*
	@OneToMany(mappedBy = "producto")
	private Collection<@Valid Oferta>	ofertas;
	*/
	
	@OneToMany(mappedBy = "producto")
	private Collection<@Valid Comentario>	comentarios;
}
