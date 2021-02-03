
package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vendedor")

public class Vendedor extends Persona {

	private Boolean								vacaciones;

	private Double								valoracion;

	@NotEmpty
	private String								nombreTienda;

	@NotEmpty
	private String								direccionTienda;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	@Valid
	private User								user;

	@OneToMany
	private Collection<@Valid Pelicula>			peliculas;
	@OneToMany
	private Collection<@Valid Videojuego>		videojuegos;
	@OneToMany
	private Collection<@Valid Merchandasing>	merchandasings;

}
