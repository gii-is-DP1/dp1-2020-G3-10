
package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;
/**
 * @author Marta Díaz
 */
@Entity
@Getter
@Setter
@Table(name = "peliculas")
public class Pelicula extends Producto {

	@Override
	public String toString() {
		return "Pelicula [director=" + director + ", agno=" + agno + ", duracion=" + duracion 
				+ ", formato=" + formato + ", edicion=" + edicion + ", imagen=" + getImagen() + ", comentarios="
				+ comentarios + "]";
	}

	@Column(name = "director")
	@NotEmpty(message = "El director es obligatorio.")
	private String	director;

	@Column(name = "agno")
	@Range(min = 1980, max = 2021, message = "El año debe estar entre 1980 y 2021")
	private Integer	agno;
	
	@Column(name = "duracion")
	@DecimalMin("1.0")
	private Double	duracion;

	
	@Column(name = "formato")
	@Enumerated(EnumType.STRING)
	private Formato formato;

	@Column(name = "edicion")
	@Min(1)
	private Integer edicion;

	
	@OneToMany(mappedBy = "pelicula")
	private Collection<@Valid Comentario>	comentarios;
	
	public void addComment(Comentario comentario) {
		getComentarios().add(comentario);
		comentario.setPelicula(this);
	}
	
}
