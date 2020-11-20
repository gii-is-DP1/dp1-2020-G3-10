package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name="comentario")
public class Comentario extends BaseEntity {
	
	@NotEmpty
	private String identificador;
	
	@NotEmpty
	private Cliente autor;
	
	@NotEmpty
	private String titulo;
	
	@NotEmpty
	private String texto;
	
	//@NotEmpty
	//private Producto producto;
	
	//@NotEmpty
	//private Vendedor vendedor;
	

}
