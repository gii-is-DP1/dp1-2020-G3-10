
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "clientes")
public class Cliente extends Persona {

	@Column(name = "ciudad")
	@NotEmpty
	private String ciudad;

	@Column(name = "codigo_postal")
	@NotEmpty
	private String codigoPostal;

	@Column(name = "direccion")
	@NotEmpty
	private String direccion;


	@Column(name = "tarjeta_credito")
	@NotEmpty
	private String	tarjetaCredito;
	
	@Column(name = "cartera")
	private Double cartera;
	
	public Cliente(@NotEmpty String nombre, @NotEmpty String apellidos, @NotEmpty @NotEmpty @NotEmpty LocalDate fechaNacimiento,
			@NotEmpty String dni, @NotEmpty @Email String email,
			@NotEmpty @Digits(fraction = 0, integer = 10) String telefono, @NotEmpty String ciudad,
			@NotEmpty String codigoPostal, @NotEmpty String direccion, @NotEmpty String tarjetaCredito,
			@NotEmpty Double cartera) {
		super(nombre, apellidos, fechaNacimiento, dni, email, telefono);
		this.ciudad = ciudad;
		this.codigoPostal = codigoPostal;
		this.direccion = direccion;
		this.tarjetaCredito = tarjetaCredito;
		this.cartera = cartera;
	}

	public Cliente(@NotEmpty String nombre, @NotEmpty String apellidos, @NotEmpty @NotEmpty @NotEmpty LocalDate fechaNacimiento,
			@NotEmpty String dni, @NotEmpty @Email String email,
			@NotEmpty @Digits(fraction = 0, integer = 10) String telefono) {
		super(nombre, apellidos, fechaNacimiento, dni, email, telefono);
	}

	public Cliente() {
	}
    
	//Este no lo comento porque si no el login no funciona
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	@Valid
	private User	user;

	
	@ManyToMany(mappedBy = "clientes")
	private Collection<Reproductor> reproductores;
	
	@OneToMany(mappedBy = "cliente")
	private Collection<@Valid Comentario>	comentarios;


	
	public void addComment(Comentario comentario) {
		getComentarios().add(comentario);
	}

	@Override
	public String toString() {
		return "Cliente [ciudad=" + ciudad + ", codigoPostal=" + codigoPostal + ", direccion=" + direccion
				+ ", tarjetaCredito=" + tarjetaCredito + ", cartera=" + cartera + ", user=" + user + ", reproductores="
				+ reproductores + ", comentarios=" + comentarios + "]";
	}
	
	/*
	
	@OneToMany(mappedBy = "cliente")
	private Collection<@Valid Pedido>	pedidos;
	
	
	*/
	
	//private List<Pedido> pedidos; NO ME DEJA
	
	
	
	
}

