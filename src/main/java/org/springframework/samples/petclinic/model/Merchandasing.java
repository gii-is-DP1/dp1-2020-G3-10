package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="merchandasing")
public class Merchandasing extends Producto {


	@NotEmpty
	private TipoMerchandasing tipo;
	
	@NotEmpty
	private String fabricante;
	
	@NotEmpty
	private String caracteristicas;
	
	/*
	
	@OneToMany (mappedBy = "merchandasing")
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
