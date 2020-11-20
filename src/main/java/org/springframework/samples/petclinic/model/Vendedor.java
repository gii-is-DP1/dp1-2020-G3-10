
package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "vendedor")
public class Vendedor extends BaseEntity {

	@NotEmpty
	private String			nombre_tienda;

	@NotEmpty
	private Boolean			vacaciones;

	@NotEmpty
	private Double			valoracion;

	
	/* HAY QUE PONER LAS TAGS CON LAS RELACIONES
	@NotEmpty
	private List<Producto>	ofertas;

	@NotEmpty
	private List<Pedido>	tareas;
	*/

}
