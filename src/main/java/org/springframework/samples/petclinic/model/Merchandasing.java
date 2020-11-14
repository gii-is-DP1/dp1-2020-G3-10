package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name="merchandasing")
public class Merchandasing extends BaseEntity {

	@NotEmpty
	private String nombre;
	
	//tipo merchandasing
	@NotEmpty
	private String tipo;
	
	@NotEmpty
	private String fabricante;
	
	@NotEmpty
	private String caracteristicas;

	/*
	 * Cuando este el tipo producto descomentar
	 * 
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "producto_id")
	@Valid
	private Producto producto;
	*/
}
