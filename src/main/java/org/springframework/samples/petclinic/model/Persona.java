package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.constraints.DniConstraint;
import org.springframework.samples.petclinic.constraints.MayorEdadConstraint;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Persona extends BaseEntity {

	@Column(name = "nombre")
	@NotEmpty
	protected String nombre;

	@Column(name = "apellidos")
	@NotEmpty
	protected String apellidos;

	@Column(name = "fecha_nacimiento")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@MayorEdadConstraint
	protected LocalDate	fechaNacimiento;

	@Column(name = "dni")
	@NotEmpty
	@DniConstraint
	protected String dni;

	@Column(name = "email")
	@NotEmpty
	@Email
	protected String email;

	@Column(name = "telefono")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	protected String telefono;

	public Persona(@NotEmpty String nombre, @NotEmpty String apellidos, @NotEmpty @NotEmpty LocalDate fechaNacimiento,
			@NotEmpty String dni, @NotEmpty @Email String email,
			@NotEmpty @Digits(fraction = 0, integer = 10) String telefono) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.dni = dni;
		this.email = email;
		this.telefono = telefono;
	}

	public Persona() {
		super();
	}

	
}