
package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "oferta")
public class Oferta extends BaseEntity {

	@NotEmpty
	private Double			precio;

	@NotEmpty
	private Conservacion	conservacion;

	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vendedor_id")
	@Valid
	private Vendedor		vendedor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pedido_id")
	@NotNull
	@Valid
	private Pedido			pedido;
	
	
	
	
	/*
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id")
	@NotNull para pobrar una cosita
	@Valid
	private Producto		producto;
	*/
}
