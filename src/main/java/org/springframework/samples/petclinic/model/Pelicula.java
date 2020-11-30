
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

	@Column(name = "director")
	@NotEmpty
	private String	director;

	@Column(name = "agno")
	@NotEmpty
	private Integer	agno;
	
	@Column(name = "duracion")
	@NotEmpty
	private Double	duracion;

//	@NotEmpty
//	private String	estudio;
//
//	@NotEmpty
//	private String	sinopsis;
	

	@Column(name = "formato")
	@NotEmpty
	private Formato formato;
	

	@NotEmpty
	private Integer edicion;
	
	
	private Plataforma plataforma;
	

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "producto_id")
	//@Valid
	private Producto producto;


	public String getDirector() {
		return director;
	}


	public void setDirector(String director) {
		this.director = director;
	}


	public Integer getAgno() {
		return agno;
	}


	public void setAgno(Integer agno) {
		this.agno = agno;
	}


	public Double getDuracion() {
		return duracion;
	}


	public void setDuracion(Double duracion) {
		this.duracion = duracion;
	}


	public Formato getFormato() {
		return formato;
	}


	public void setFormato(Formato formato) {
		this.formato = formato;
	}


	public Integer getEdicion() {
		return edicion;
	}


	public void setEdicion(Integer edicion) {
		this.edicion = edicion;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	
	
}
