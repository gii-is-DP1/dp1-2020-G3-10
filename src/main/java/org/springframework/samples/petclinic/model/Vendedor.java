
package org.springframework.samples.petclinic.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
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
