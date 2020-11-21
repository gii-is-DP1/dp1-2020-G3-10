package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name="pedido")
public class Pedido extends BaseEntity {
	
	@NotEmpty
	private EstadoPedido estado;
	
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cliente_id")
	@Valid
	private Cliente cliente;
	
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vendedor_id")
	@Valid
	private Vendedor vendedor;
	
	/*
	private List<Producto> contenido;
	
	private List<Mensaje> mensajes;
	*/
}
