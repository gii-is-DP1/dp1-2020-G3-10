
package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vendedor")

public class Vendedor extends Person {

	private Boolean	vacaciones;

	private Double	valoracion;

	@NotEmpty
	private String	nombreTienda;

	@NotEmpty
	private String	direccionTienda;

	@Digits(fraction = 0, integer = 10)
	private String	telefono;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	@Valid
	private User	user;
	
	

}
