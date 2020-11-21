
package org.springframework.samples.petclinic.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "vendedor")
public class Vendedor extends BaseEntity {

	@NotEmpty
	private Boolean			vacaciones;

	@NotEmpty
	private Double			valoracion;
	
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "powerseller_id")
	@Valid
	private PowerSeller powerseller;
	
	@OneToMany(mappedBy = "vendedor")
	private Collection<@Valid Oferta>	misOfertas;

	/* HAY QUE PONER LAS TAGS CON LAS RELACIONES
	 * 
	@NotEmpty
	private List<Pedido>	tareas;
	*/

}
