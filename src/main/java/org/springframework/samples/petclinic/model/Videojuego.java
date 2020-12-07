package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "videojuego")
public class Videojuego extends Producto {


	@NotEmpty
	private Integer	agno;

	@NotEmpty
	private String	estudio;

	@NotEmpty
	private String	descripcion;
	
	@OneToOne(optional=false)
	private Plataforma plataforma;
	
	@OneToMany
	private Collection<@Valid Oferta>	ofertas;
	
	@OneToMany //(mappedBy = "producto")
	private Collection<@Valid Comentario>	comentarios;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	@Valid
	private Cliente	cliente;
	
}

