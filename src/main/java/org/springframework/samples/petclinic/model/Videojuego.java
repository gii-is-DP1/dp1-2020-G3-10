package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "videojuegos")
public class Videojuego extends Producto {


	@NotEmpty
	@Column(name = "descripcion")
	private String	descripcion;
	
	@Column(name = "agno")
	private Integer	agno;

	@NotEmpty
	@Column(name = "estudio")
	private String	estudio;

	@NotEmpty
	@Column(name = "imagen")
	private String imagen;
	
	
	@Column(name = "plataforma")
	@Enumerated(EnumType.STRING)
	private Plataforma plataforma;
	
	/*
	 
	@OneToMany (mappedBy = "videojuego")
	private Collection<@Valid Comentario>	comentarios;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendedor_id")
	@Valid
	private Vendedor 	vendedor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pedido_id")
	@NotNull
	@Valid
	private Pedido		pedido;
	
	*/
}

