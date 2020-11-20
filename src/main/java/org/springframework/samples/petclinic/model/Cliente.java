package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "cliente")
public class Cliente extends BaseEntity {

	@NotEmpty
	private String	dni;

	@NotEmpty
	private String	email;
	
	@NotEmpty
	private String	nombre;
	
	@NotEmpty
	private String	apellidos;
	
	@NotEmpty
	private String	direccion;

	@NotEmpty
	private String	tarjeta_credito;
	
	@NotEmpty
	private Double cartera;

	@NotEmpty	
	private Boolean admin;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	@Valid
	private User	user;
	
	/* DESCOMENTAR CUANDO ESTÉN LISTOS LOS DEMÁS MODELOS
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "producto_id")
	private List<Producto> deseados;
	
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vendedor_id")
	@Valid
	private Vendedor vendedor;
	
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pedido_id")
	private List<Pedido> pedidos;
	
	@NotEmpty
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mensaje_id")
	private List<Mensaje> bandeja;
	*/
}
