
package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Entity
@Table(name = "peliculas")
public class Pelicula extends BaseEntity {

	@Column
	private String	titulo;

	@Column
	@NotEmpty
	private String	director;

	@Column
	@NotEmpty
	private Integer	agno;
	
	@Column
	@NotEmpty
	private Double	duracion;

//	@NotEmpty
//	private String	estudio;
//
//	@NotEmpty
//	private String	sinopsis;
//	
//	@NotEmpty
//	private Formato formato;
//	
//	@NotEmpty
//	private Integer edicion;
	

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "producto_id")
	@Valid
	private Producto producto;

}
