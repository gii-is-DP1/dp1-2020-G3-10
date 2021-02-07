package org.springframework.samples.petclinic.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Persona;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@DataJpaTest()
public class MayorEdadValidatorTest {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean =
		new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
		}

	private Persona persona;
	
	private int TEST_PERSONA_ID = 200;
	
	@BeforeEach
	void setup() {
		
		persona = new Persona();
		persona.setId(TEST_PERSONA_ID);
		persona.setNombre("Paco");
		persona.setApellidos("Pruebas");
		persona.setDni("32097886Y");
		persona.setEmail("pacopruebas@hotmail.es");
		persona.setFechaNacimiento(LocalDate.of(1990, 01, 01));
		persona.setTelefono("956085319");
	}
	
	@Test
	void noValidaMenorDeEdad() {
	
	this.persona.setFechaNacimiento(LocalDate.of(2005, 01, 01));
	Validator validator = createValidator();
	Set<ConstraintViolation<Persona>> constraintViolations =
	validator.validate(this.persona);
	assertThat(constraintViolations.size()).isEqualTo(1);
	ConstraintViolation<Persona> violation =
	constraintViolations.iterator().next();
	assertThat(violation.getPropertyPath().toString())
	.isEqualTo("fechaNacimiento");
	assertThat(violation.getMessage()).isEqualTo("El usuario debe ser mayor de edad");
	}
	
	@Test
	void noValidaEdadImposible() {
	
	this.persona.setFechaNacimiento(LocalDate.of(2030, 01, 01));
	Validator validator = createValidator();
	Set<ConstraintViolation<Persona>> constraintViolations =
	validator.validate(this.persona);
	assertThat(constraintViolations.size()).isEqualTo(1);
	ConstraintViolation<Persona> violation =
	constraintViolations.iterator().next();
	assertThat(violation.getPropertyPath().toString())
	.isEqualTo("fechaNacimiento");
	assertThat(violation.getMessage()).isEqualTo("El usuario debe ser mayor de edad");
	}
	
	@Test
	void validadEdadValida() {
	
	this.persona.setFechaNacimiento(LocalDate.of(1990, 01, 01));
	Validator validator = createValidator();
	Set<ConstraintViolation<Persona>> constraintViolations =
	validator.validate(this.persona);
	assertThat(constraintViolations.size()).isEqualTo(0);
	}

}
