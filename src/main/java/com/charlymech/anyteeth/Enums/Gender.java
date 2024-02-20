package com.charlymech.anyteeth.Enums;

import static com.charlymech.anyteeth.App.rb;

public enum Gender {
	MALE,	FEMALE;

	// Método para retornar una lista de los géneros dependiendo del idioma del OS
	public static String[] getGenders() {
		return rb.getString("genders").split(";");
	}
}
