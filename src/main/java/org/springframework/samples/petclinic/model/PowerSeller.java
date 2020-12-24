
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "powerseller")
public class PowerSeller extends BaseEntity {

	//Propiedades
	@Column(name = "nombreTienda")
	@NotEmpty
	private String	nombreTienda;

	@Column(name = "direccion")
	@NotEmpty
	private String	direccion;

	@Column(name = "telefono")
	@NotEmpty
	private Integer	telefono;

}
