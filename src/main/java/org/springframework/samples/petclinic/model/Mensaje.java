package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="mensaje")
public class Mensaje {

	@NotEmpty
	private String identificador;
	
	@NotEmpty
	private String titulo;
	
	@NotEmpty
	private String texto;
	
	@NotNull
	private Boolean borrador;
	
	@NotEmpty
	private Cliente emisor;
	
	@NotEmpty
	private Cliente receptor;
	
	@NotEmpty
	private Pedido pedido;
}
