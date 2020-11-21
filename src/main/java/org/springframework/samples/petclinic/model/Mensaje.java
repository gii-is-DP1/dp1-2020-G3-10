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

import lombok.Data;

@Data
@Entity
@Table(name="mensaje")
public class Mensaje extends BaseEntity{

	@NotEmpty
	private String titulo;
	
	@NotEmpty
	private String texto;
	
	@NotNull
	private Boolean borrador;
	
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "emisor_id")
	@Valid
	private Cliente emisor;
	
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "receptor_id")
	@Valid
	private Cliente receptor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pedido_id")
	@NotNull
	@Valid
	private Pedido		pedido;
	
	
	
	/// ESTO ES TEMPORAL HASTA ENCONTRAR LA MEJOR OPCION PARA LOS MENSAJES
	@NotEmpty
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cliente_id")
	@Valid
	private Cliente cliente;
	
}
