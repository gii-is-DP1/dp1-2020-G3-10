
package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@Table(name = "pelicula")
public class Pelicula extends BaseEntity {

	@NotEmpty
	private String	titulo;

	@NotEmpty
	private String	director;

	@NotEmpty
	private Integer	agno;

	@NotEmpty
	private String	estudio;

	@NotEmpty
	private String	descripcion;
	
	@NotEmpty
	private Plataforma plataforma;
	
	/*
	 *  Si nos hace falta nevegabilidad doble , descomentar esto.
	 * 
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "producto_id")
	@Valid
	private Producto producto;
	*/

}
