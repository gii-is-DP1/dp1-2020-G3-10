
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "pedido")
public class Pedido extends BaseEntity {

	//@NotEmpty         para evitar que pete al crear uno
	private EstadoPedido	estado;

	//@NotEmpty
	@Column(name = "precioTotal")
	private Double			precioTotal;

	//@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "fecha")
	private LocalDate		fecha;

	@NotEmpty
	@Column(name = "direccionEnvio")
	private String			direccionEnvio;

	/*
	 * @NotEmpty
	 *
	 * @OneToOne(cascade = CascadeType.ALL)
	 *
	 * @JoinColumn(name = "cliente_id")
	 *
	 * @Valid
	 * private Cliente cliente;
	 *
	 * @NotEmpty
	 *
	 * @ManyToOne(cascade = CascadeType.ALL)
	 *
	 * @JoinColumn(name = "vendedor_id")
	 *
	 * @Valid
	 * private Vendedor vendedor;
	 *
	 * @OneToMany(mappedBy = "pedido")
	 * private Collection<@Valid Oferta> ofertas;
	 *
	 * @OneToMany(mappedBy = "pedido")
	 * private Collection<@Valid Mensaje> mensaje;
	 */

}
