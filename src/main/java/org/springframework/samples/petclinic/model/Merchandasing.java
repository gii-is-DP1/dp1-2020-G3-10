package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
