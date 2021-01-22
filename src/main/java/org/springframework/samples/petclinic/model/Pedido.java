
package org.springframework.samples.petclinic.model;


import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "pedido")
public class Pedido extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
	private EstadoPedido	estado;

	@Column(name = "precio_total")
	private Double			precioTotal;

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "fecha")
	private LocalDate		fecha;

	@Column(name = "direccion_envio")
	private String			direccionEnvio;
	
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
	

}
