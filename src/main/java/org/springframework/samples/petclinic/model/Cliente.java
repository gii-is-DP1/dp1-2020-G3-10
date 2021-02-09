
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Collection;
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

import org.hibernate.validator.constraints.CreditCardNumber;

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
	@CreditCardNumber
	private String	tarjetaCredito;
	
	@Column(name = "cartera")
	private Double cartera;

	public Cliente(@NotEmpty String nombre, @NotEmpty String apellidos, @NotEmpty @NotEmpty @NotEmpty LocalDate fechaNacimiento,
			@NotEmpty String dni, @NotEmpty @Email String email,
			@NotEmpty @Digits(fraction = 0, integer = 10) String telefono) {
		super(nombre, apellidos, fechaNacimiento, dni, email, telefono);
		this.ciudad = ciudad;
		this.codigoPostal = codigoPostal;
		this.direccion = direccion;
		this.tarjetaCredito = tarjetaCredito;
		this.cartera = cartera;
		this.pedidos = null;
	}

	public Cliente() {
	}
    
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
		comentario.setCliente(this);
	}

	@Override
	public String toString() {
		return "Cliente [ciudad=" + ciudad + ", codigoPostal=" + codigoPostal + ", direccion=" + direccion
				+ ", tarjetaCredito=" + tarjetaCredito + ", cartera=" + cartera + ", user=" + user + ", reproductores="
				+ reproductores + ", comentarios=" + comentarios + "]";
	}
	
	public Cliente(@NotEmpty String nombre, @NotEmpty String apellidos, @NotEmpty @NotEmpty LocalDate fechaNacimiento,
			@NotEmpty String dni, @NotEmpty @Email String email,
			@NotEmpty @Digits(fraction = 0, integer = 10) String telefono, @NotEmpty String ciudad,
			@NotEmpty String codigoPostal, @NotEmpty String direccion, @NotEmpty String tarjetaCredito, Double cartera,
			@Valid User user, Collection<Reproductor> reproductores, Collection<@Valid Comentario> comentarios,
			Collection<@Valid Pedido> pedidos) {
		super(nombre, apellidos, fechaNacimiento, dni, email, telefono);
		this.ciudad = ciudad;
		this.codigoPostal = codigoPostal;
		this.direccion = direccion;
		this.tarjetaCredito = tarjetaCredito;
		this.cartera = cartera;
		this.user = user;
		this.reproductores = reproductores;
		this.comentarios = comentarios;
		this.pedidos = pedidos;
	}

	@OneToMany(mappedBy = "cliente")
	private Collection<@Valid Pedido>	pedidos;
	
	
}

