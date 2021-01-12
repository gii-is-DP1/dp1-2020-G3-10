
package org.springframework.samples.petclinic.model;


import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

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
	
	@NotEmpty
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cliente_id")
	@Valid
	private Cliente					    cliente;
	
	@OneToMany
	private Collection<@Valid Pelicula>	peliculas;
	@OneToMany
	private Collection<@Valid Videojuego>	videojuegos;
	@OneToMany
	private Collection<@Valid Merchandasing>	merchandasings;
	
	/*
	@NotEmpty
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "vendedor_id")
	@Valid
	private Vendedor					vendedor;
	*/
	

}
