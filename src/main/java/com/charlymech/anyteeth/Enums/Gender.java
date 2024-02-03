package com.charlymech.anyteeth.Enums;

import static com.charlymech.anyteeth.App.rb;

/*
 * Como esta clase enumerada depende del Idioma de la App decido aplicar
 * un método diferente para el uso que tendrá en comparación con la clase
 * enumerada de Province (ya que esta no lo requiere)
 */
public enum Gender {
	MALE {
		public String toString() {
			return rb.getString("genderMale");
		}
	},
	FEMALE {
		public String toString() {
			return rb.getString("genderFemale");
		}
	};
}
