package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "oferta")
public class Oferta extends BaseEntity {

	@NotEmpty
	private Double				precio;
	
	@NotEmpty
	private Conservacion        conservacion;
	
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vendedor_id")
	@Valid
	private Vendedor vendedor;
}

