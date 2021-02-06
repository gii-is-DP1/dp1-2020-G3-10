package org.springframework.samples.petclinic.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.samples.petclinic.util.MayorEdadValidator;


@Documented
@Constraint(validatedBy = MayorEdadValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MayorEdadConstraint {

	String message() default "El usuario debe ser mayor de edad";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
}