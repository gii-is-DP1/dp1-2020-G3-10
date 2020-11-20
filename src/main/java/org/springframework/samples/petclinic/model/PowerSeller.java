
package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@Table(name = "powerSeller")
public class PowerSeller extends BaseEntity {

	@NotEmpty
	private String	nombreTienda;

	@NotEmpty
	private String	direccion;

	@NotEmpty
	private Integer	telefono;

}
