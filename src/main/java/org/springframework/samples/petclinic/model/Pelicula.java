
package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "peliculas")
public class Pelicula extends Producto {

	@Column(name = "director")
	@NotEmpty
	private String	director;

	@Column(name = "agno")
	@NotEmpty
	private Integer	agno;
	
	@Column(name = "duracion")
	@NotEmpty
	private Double	duracion;

//	@NotEmpty
//	private String	estudio;
//
//	@NotEmpty
//	private String	sinopsis;
	
	@Column(name = "formato")
	@NotEmpty
	private Formato formato;

	@NotEmpty
	private Integer edicion;
	
	@OneToMany
	private Collection<@Valid Oferta>	ofertas;
	
	@OneToMany //(mappedBy = "producto")
	private Collection<@Valid Comentario>	comentarios;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	@Valid
	private Cliente	cliente;
	
}
