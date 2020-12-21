
package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.websocket.Decoder.Text;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "peliculas")
public class Pelicula extends Producto {

	@Column(name = "director")
	@NotEmpty
	private String	director;

	@Column(name = "agno")
	private Integer	agno;
	
	@Column(name = "duracion")
	private Double	duracion;

	@NotEmpty
	@Column(name = "sinopsis")
	private String	sinopsis;
	
	@Column(name = "formato")
	@Enumerated(EnumType.STRING)
	private Formato formato;

	
	@Column(name = "edicion")
	private Integer edicion;
	
	@Column(name = "imagen")
	@NotEmpty
	private String imagen;
	
	@OneToMany
	private Collection<@Valid Oferta>	ofertas;
	
	@OneToMany //(mappedBy = "producto")
	private Collection<@Valid Comentario>	comentarios;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	@Valid
	private Cliente	cliente;
	
}
