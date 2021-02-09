package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.Valid;
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
@Table(name = "videojuegos")
public class Videojuego extends Producto {

	
	@Column(name = "agno")
	@Range(min = 1980, max = 2021, message = "El año debe estar entre 1980 y 2021")
	private Integer	agno;

	@NotEmpty(message = "El estudio es obligatorio.")
	@Column(name = "estudio")
	private String	estudio;

	
	@Column(name = "plataforma")
	@Enumerated(EnumType.STRING)
	private Plataforma plataforma;
	
	
	@OneToMany(mappedBy = "videojuego")
	private Collection<@Valid Comentario>	comentarios;
	
	public void addComment(Comentario comentario) {
		getComentarios().add(comentario);
		comentario.setVideojuego(this);
	}
	
}

