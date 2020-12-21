
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "vendedor")
public class Vendedor extends BaseEntity {

	@NotEmpty
	private Boolean			vacaciones;

	@NotEmpty
	private Double			valoracion;
	
	
	//Como producto no es una entidad, 
	//hay que relacionar vendedor con las 3 entidades por separado.
	
	/*
	@OneToMany(mappedBy = "vendedor")
	private Collection<@Valid Pedido>	pedidos;
	
	@OneToMany(mappedBy = "vendedor")
	private Collection<@Valid Pelicula>	peliculas;
	
	@OneToMany(mappedBy = "vendedor")
	private Collection<@Valid Videojuego> videojuego;
	
	@OneToMany(mappedBy = "vendedor")
	private Collection<@Valid Merchandasing> merchandasing;
	*/

}
