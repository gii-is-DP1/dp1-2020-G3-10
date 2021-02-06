
package org.springframework.samples.petclinic.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.petclinic.constraints.DniConstraint;

public class DniValidator implements ConstraintValidator<DniConstraint, String> {

	private final String dniChars = "TRWAGMYFPDXBNJZSQVHLCKE";

	public void initialize(DniConstraint dni) {

	}

	public boolean isValid(String dni, ConstraintValidatorContext contactNumber) {

		String parteEnteraDNI = dni.trim().replaceAll(" ", "").substring(0, 7);

		char letraDNI = dni.charAt(8);

		int valorNumericoDni = Integer.parseInt(parteEnteraDNI) % 23;
		if (dni.length() != 9 && isNumeric(parteEnteraDNI) == false
				&& (dniChars.charAt(valorNumericoDni) != letraDNI)) {
			return false;
		} else {
			return true;
		}

	}

	private boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
