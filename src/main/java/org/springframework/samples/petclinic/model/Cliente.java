package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente extends Persona {

	@Column(name = "ciudad")
	@NotEmpty
	private String ciudad;

	@Column(name = "codigoPostal")
	@NotEmpty
	private String codigoPostal;

	@Column(name = "direccion")
	@NotEmpty
	private String direccion;

	@Column(name = "tarjetaCredito")
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
    
	//Este ya no hace falta?
	/*
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