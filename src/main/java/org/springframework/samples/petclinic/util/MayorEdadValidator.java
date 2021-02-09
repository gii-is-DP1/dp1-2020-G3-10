
package org.springframework.samples.petclinic.util;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.petclinic.constraints.MayorEdadConstraint;

public class MayorEdadValidator implements ConstraintValidator<MayorEdadConstraint, LocalDate> {

	public void initialize(MayorEdadConstraint dni) {

	}

	public boolean isValid(LocalDate fechaNacimiento, ConstraintValidatorContext contactNumber) {

		boolean result = false;
		
		LocalDate ahora = LocalDate.now();

		Period periodo = Period.between(fechaNacimiento, ahora);
		
		if(periodo.getYears() >= 18) {
			result = true;
		}
		return result;
	}

}
