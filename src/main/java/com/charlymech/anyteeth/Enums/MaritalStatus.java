package com.charlymech.anyteeth.Enums;

import static com.charlymech.anyteeth.App.rb;

public enum MaritalStatus {
	SINGLE, MARRIED, UNMARRIED, SEPARATED, DIVORCED, WIDOWER;

	// Método para retornar una lista de los estados civiles dependiendo del idioma del OS
	public static String[] getMaritalStatus() {
		return rb.getString("maritalStatus").split(";");
	}
}
