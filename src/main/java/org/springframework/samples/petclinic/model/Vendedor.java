
package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

	@Column(name = "vacaciones")
	private Boolean								vacaciones;

	@Column(name = "valoracion")
	private Double								valoracion;

	@NotEmpty
	@Column(name = "nombre_tienda")
	private String								nombreTienda;

	@NotEmpty
	@Column(name = "direccion_tienda")
	private String								direccionTienda;

	@Column(name = "votos")
	private Integer								votos;

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


	public void setValoracion2(final Double valoracion) {
		this.valoracion = valoracion;
		this.votos = this.votos + 1;
	}

	public Double valoracionFinal() {
		Double votosFinal = this.valoracion / this.votos;
		return votosFinal;

	}

	@Override
	public String toString() {
		return "Vendedor [ciudad=" + this.vacaciones + ", codigoPostal=" + this.nombreTienda + ", direccion=" + this.direccionTienda + ", peliculas=" + this.peliculas + ", merchandasings=" + this.merchandasings + ", user=" + this.user + ", videojuegos="
			+ this.videojuegos + "]";
	}

}
