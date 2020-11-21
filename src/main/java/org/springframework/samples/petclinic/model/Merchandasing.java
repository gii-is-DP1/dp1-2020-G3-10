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

	@NotEmpty
	private TipoMerchandasing tipo;
	
	@NotEmpty
	private String fabricante;
	
	@NotEmpty
	private String caracteristicas;
	
	
	
	
	
	

	/*
	 * Si nos hace falta nevegabilidad doble , descomentar esto.
	 * 
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "producto_id")
	@Valid
	private Producto producto;
	*/
}
