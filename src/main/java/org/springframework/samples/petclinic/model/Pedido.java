
package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CascadeType;
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
@Table(name = "pedido")
public class Pedido extends BaseEntity {

	@NotEmpty
	private EstadoPedido				estado;

	//Hay que plantearse bien que cascadeType nos conviene usar
	
	/*
	 
	@NotEmpty
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "vendedor_id")
	@Valid
	private Cliente					    cliente;

	@NotEmpty
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "vendedor_id")
	@Valid
	private Vendedor					vendedor;

	@OneToMany(mappedBy = "pedido")
	private Collection<@Valid Producto>	productos;

	*/
	

}
