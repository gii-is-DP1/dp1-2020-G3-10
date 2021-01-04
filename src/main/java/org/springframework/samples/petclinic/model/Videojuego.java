package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "videojuego")
public class Videojuego extends Producto {


	@NotEmpty
	private Integer	agno;

	@NotEmpty
	private String	estudio;

	@NotEmpty
	private String	descripcion;
	

	
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

