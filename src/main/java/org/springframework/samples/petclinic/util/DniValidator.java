
package org.springframework.samples.petclinic.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.petclinic.constraints.DniConstraint;

public class DniValidator implements ConstraintValidator<DniConstraint, String> {


	private boolean soloNumeros(String dni) {

		int i, j = 0;
		String numero = "";
		String miDNI = "";
		String[] unoNueve = {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
		};

		for (i = 0; i < dni.length() - 1; i++) {
			numero = dni.substring(i, i + 1);

			for (j = 0; j < unoNueve.length; j++) {
				if (numero.equals(unoNueve[j])) {
					miDNI += unoNueve[j];
				}
			}
		}

		if (miDNI.length() != 8) {
			return false;
		} else {
			return true;
		}
	}

	private String letraDNI(String dni) {

		int miDNI = Integer.parseInt(dni.substring(0, 8));
		int resto = 0;
		String miLetra = "";
		String[] asignacionLetra = {
			"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"
		};

		resto = miDNI % 23;

		miLetra = asignacionLetra[resto];

		return miLetra;
	}

	public void initialize(DniConstraint dni) {
		
	}
	
	@Override
	public boolean isValid(String dni, ConstraintValidatorContext context) {
		String letraMayuscula = "";
		if (dni.length() != 9 || Character.isLetter(dni.charAt(8)) == false) {
			return false;
		}

		letraMayuscula = dni.substring(8).toUpperCase();

		if (this.soloNumeros(dni) == true && this.letraDNI(dni).equals(letraMayuscula)) {
			return true;
		} else {
			return false;
		}
	}
}
