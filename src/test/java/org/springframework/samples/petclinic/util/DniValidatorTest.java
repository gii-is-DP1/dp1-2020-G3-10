package org.springframework.samples.petclinic.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;                                                                                                     import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Persona;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@DataJpaTest()
public class DniValidatorTest  {

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
	void noValidaDniCorto() {
	
	this.persona.setDni("320978Y");
	Validator validator = createValidator();
	Set<ConstraintViolation<Persona>> constraintViolations =
	validator.validate(this.persona);
	assertThat(constraintViolations.size()).isEqualTo(1);
	ConstraintViolation<Persona> violation =
	constraintViolations.iterator().next();
	assertThat(violation.getPropertyPath().toString())
	.isEqualTo("dni");
	assertThat(violation.getMessage()).isEqualTo("DNI No Valido");
	}
	
	@Test
	void noValidaDniSinLetra() {
	
	this.persona.setDni("320978861");
	Validator validator = createValidator();
	Set<ConstraintViolation<Persona>> constraintViolations =
	validator.validate(this.persona);
	assertThat(constraintViolations.size()).isEqualTo(1);
	ConstraintViolation<Persona> violation =
	constraintViolations.iterator().next();
	assertThat(violation.getPropertyPath().toString())
	.isEqualTo("dni");
	assertThat(violation.getMessage()).isEqualTo("DNI No Valido");
	}
	
	@Test
	void noValidaDniLargo() {
	
	this.persona.setDni("320978861123Y");
	Validator validator = createValidator();
	Set<ConstraintViolation<Persona>> constraintViolations =
	validator.validate(this.persona);
	assertThat(constraintViolations.size()).isEqualTo(1);
	ConstraintViolation<Persona> violation =
	constraintViolations.iterator().next();
	assertThat(violation.getPropertyPath().toString())
	.isEqualTo("dni");
	assertThat(violation.getMessage()).isEqualTo("DNI No Valido");
	}
	
	@Test
	void noValidaLetraErronea() {
	
	this.persona.setDni("32097886A");
	Validator validator = createValidator();
	Set<ConstraintViolation<Persona>> constraintViolations =
	validator.validate(this.persona);
	assertThat(constraintViolations.size()).isEqualTo(1);
	ConstraintViolation<Persona> violation =
	constraintViolations.iterator().next();
	assertThat(violation.getPropertyPath().toString())
	.isEqualTo("dni");
	assertThat(violation.getMessage()).isEqualTo("DNI No Valido");
	}
	
	@Test
	void validaDniLegal() {
	
	this.persona.setDni("32097886Y");
	Validator validator = createValidator();
	Set<ConstraintViolation<Persona>> constraintViolations =
	validator.validate(this.persona);
	assertThat(constraintViolations.size()).isEqualTo(0);
	}
}