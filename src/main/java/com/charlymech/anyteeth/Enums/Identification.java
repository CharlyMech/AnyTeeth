package com.charlymech.anyteeth.Enums;

import static com.charlymech.anyteeth.App.rb;

public enum Identification {
	DNI, NIE, PASSPORT;

	// Método de comprobación de formato de DNI y cálculo de letra
	public static boolean checkDNI(String dni) { // Método para verificar el DNI
		String dniRegex = "\\d{8}[A-Z]";
		char[] chars = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
		if (!dni.matches(dniRegex)) { // Comprobar si cumple la expresión regular
			return false;
		} else { // Comprobar que la letra sea la correcta
			int dniNums = Integer.parseInt(dni.substring(0, dni.length() - 1));
			char dniChar = dni.charAt(dni.length() - 1);
			char calculatedDniLetter = chars[dniNums % 23];
			if (dniChar != calculatedDniLetter) { // La letra no es correcta
				return false;
			}
		}
		// A este punto está bien el dni
		return true;
	}

	// Método de comprobación de formato de NIE y cálculo de letra
	public static boolean checkNIE(String nie) { // Método para verificar el NIE
		//! BUG: Caused by: java.lang.NumberFormatException: For input string: "0X1234567"
		String nieRegex = "[XYZ]\\d{7}[A-Z]";
		char[] chars = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
		if (!nie.matches(nieRegex)) { // Comprobar si cumple la expresión regular
			return false;
		} else { // Comprobar que la letra sea la correcta
			char startChar = nie.charAt(0);
			String transformedChar = null;
			switch (startChar) { // Transformar la letra inicial al número correcto
				case 'X' -> transformedChar = "0";
				case 'Y' -> transformedChar = "1";
				case 'Z' -> transformedChar = "2";
			}
			int nieNums = Integer.parseInt(transformedChar + nie.substring(0, nie.length() - 1));
			char nieChar = nie.charAt(nie.length() - 1);
			char calculatedNieLetter = chars[nieNums % 23];
			if (nieChar != calculatedNieLetter) { // La letra no es correcta
				return false;
			}
		}
		// A este punto está bien el nie
		return true;
	}

	// Método de comprobación de formato de PASSPORT y cálculo
	public static boolean checkPassport(String passport) {
		return false; // TODO!!
	}

	// Método para retornar una lista de los tipos de identificación dependiendo del idioma del OS
	public static String[] getIdentifications() {
		return rb.getString("identifications").split(";");
	}
}
