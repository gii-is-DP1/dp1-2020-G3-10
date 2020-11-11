package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "videojuegos")
@Getter
@Setter
public class Videojuego extends Producto {
	
	@NotBlank
	@Column(name = "titulo")
	String titulo;
	
	@NotBlank
	@Column(name = "director")
	String director;
	
	@NotNull
	@Column(name = "anyo")
	Integer año;
	
	@NotBlank
	@Column(name = "sinopsis")
	String sinopsis;
	
	@NotNull
	@Column(name = "edicion")
	Integer edicion;
	
	@NotNull
	@Column(name = "formato")
	Formato formato;
	
	
	

}
