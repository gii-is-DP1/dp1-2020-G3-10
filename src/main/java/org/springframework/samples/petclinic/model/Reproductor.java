package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "reproductores")
public class Reproductor extends BaseEntity {
	
	@NotEmpty
	@Size(min=2,max=20)
	@Column(unique=true)
	private String nombre;
	
	@NotEmpty
	private String descripcion;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Cliente> clientes;	

	
}