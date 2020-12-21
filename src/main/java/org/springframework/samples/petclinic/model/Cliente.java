package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
	
	/*
	
	@OneToMany(mappedBy = "cliente")
	private Collection<@Valid Pedido>	pedidos;
	
	@OneToMany(mappedBy = "cliente")
	private Collection<@Valid Comentario>	comentarios;
	
	*/
	
	
}