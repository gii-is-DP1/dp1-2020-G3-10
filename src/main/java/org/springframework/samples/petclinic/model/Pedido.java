
package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cliente_id")
	@Valid
	private Cliente						cliente;

	@NotEmpty
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vendedor_id")
	@Valid
	private Vendedor					vendedor;

	@OneToMany(mappedBy = "pedido")
	private Collection<@Valid Oferta>	ofertas;

	@OneToMany(mappedBy = "pedido")
	private Collection<@Valid Mensaje>	mensaje;

}
