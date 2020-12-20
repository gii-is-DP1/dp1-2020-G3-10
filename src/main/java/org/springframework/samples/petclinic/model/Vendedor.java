
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "vendedor")
public class Vendedor extends Person {

	@NotBlank
	private Boolean	vacaciones;

	@NotBlank
	private Double	valoracion;

	@NotBlank
	private String	nombreTienda;

	@NotBlank
	private String	direccionTienda;

	@NotBlank
	@Digits(fraction = 0, integer = 10)
	private String	telefono;

	//	@NotEmpty
	//	@OneToOne(cascade = CascadeType.ALL)
	//	@JoinColumn(name = "powerseller_id")
	//	@Valid
	//	private PowerSeller powerseller;
	//
	//	@OneToMany(mappedBy = "vendedor")
	//	private Collection<@Valid Oferta>	misOfertas;
	//
	//	@OneToMany(mappedBy = "vendedor")
	//	private Collection<@Valid Pedido>	tareas;

}
