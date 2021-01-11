
package org.springframework.samples.petclinic.model;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "pedido")
public class Pedido extends BaseEntity {

	//@NotEmpty         para evitar que pete al crear uno
	private EstadoPedido	estado;

	//@NotEmpty
	@Column(name = "precio_total")
	private Double			precioTotal;

	//@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "fecha")
	private LocalDate		fecha;

	@NotEmpty
	@Column(name = "direccion_envio")
	private String			direccionEnvio;
	
	//private List<LineaPedido> lineasPedido;

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
