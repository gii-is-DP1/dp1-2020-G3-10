
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vendedor")
public class Vendedor extends Person {

	private Boolean	vacaciones;

	private Double	valoracion;

	private String	nombreTienda;

	private String	direccionTienda;

	@Digits(fraction = 0, integer = 10)
	private String	telefono;

	//	@OneToMany(mappedBy = "vendedor")
	//	private Collection<@Valid Oferta>	misOfertas;
	//
	//	@OneToMany(mappedBy = "vendedor")
	//	private Collection<@Valid Pedido>	tareas;

}
