package org.springframework.samples.petclinic.constraints;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.springframework.samples.petclinic.util.DniValidator;


@Documented
@Constraint(validatedBy = DniValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DniConstraint {

	String message() default "DNI No Valido";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
}
