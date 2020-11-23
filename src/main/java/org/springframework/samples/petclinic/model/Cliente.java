package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "cliente")
public class Cliente extends BaseEntity {

	@NotEmpty
	@Length(min = 9, max = 9, message="la longitud debe ser de 9 caracteres")
	private String	dni;

	@NotEmpty
	@Email
	private String	email;
	
	@NotEmpty
	private String	nombre;
	
	@NotEmpty
	private String	apellidos;
	
	@NotEmpty
	private String	direccion;

	//@CreditCardNumber
	@NotEmpty
	private String	tarjeta_credito;
	
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate f_nacimiento;
	
	@NotNull
	private Double cartera;

	@NotNull
	private Boolean admin;
	
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
