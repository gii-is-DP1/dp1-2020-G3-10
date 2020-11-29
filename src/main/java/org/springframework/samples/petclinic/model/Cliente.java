package org.springframework.samples.petclinic.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	
	private Double cartera;

	private Boolean admin;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "clientes")
	private Set<Plataforma> plataformas;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	@Valid
	private User	user;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vendedor_id")
	private Vendedor vendedor;
	
	@OneToMany(mappedBy = "cliente")
	private Collection<@Valid Mensaje>	bandeja;
	
	@OneToMany(mappedBy = "cliente")
	private Collection<@Valid Producto>	deseado;
	
	@OneToMany(mappedBy = "cliente")
	private Collection<@Valid Pedido>	pedidos;
	
	
	
	
}
