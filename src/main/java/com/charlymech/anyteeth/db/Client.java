package com.charlymech.anyteeth.db;

import com.charlymech.anyteeth.Enums.Gender;
import com.charlymech.anyteeth.Enums.Identification;
import com.charlymech.anyteeth.Enums.MaritalStatus;
import com.charlymech.anyteeth.Enums.Province;

import java.math.BigInteger;
import java.util.Date;

public class Client extends Person {
	// Variables de clase
	private String clientID;
	private String bankAcc; // IBAN

	// Constructores
	public Client(String identification, Identification identificationType, String name, String surnames, Gender gender, Date birthDate, String telephoneNumber, String email, String address, int cp, String population, Province province, MaritalStatus maritalStatus, Date registrationDate, String bankAcc) {
		super(identification, identificationType, name, surnames, gender, birthDate, telephoneNumber, email, address, cp, population, province, maritalStatus, registrationDate); // Llamar al constructor de la clase padre
		this.clientID = this.generateClientID();
		this.bankAcc = bankAcc;
	}

	public Client() { // Instancio el constructor vacío por si fuera necesario
	}

	/*
	 * Métodos de la clase
	 */
	// Generar IDs únicos para las alertas
	private String generateClientID() {
		String newClientID = "";

		// Realizar las operaciones con la base de datos necesarias para verificar que el ID sea único

		return newClientID;
	}

	// Método de comprobación de validez del número de cuenta (IBAN)
	public boolean checkAccountNumber(String bankAcc) {
		// Este método comprueba si el número de cuenta es correcto en cuanto a formato y la comprobación de validez del número IBAN
		// Modificar la cadena de texto por si acaso
		bankAcc.replaceAll("\\s", ""); // Quitar espacios
		bankAcc = bankAcc.toUpperCase(); // Pasarlo a mayúsculas

		String bankAccRegex = "^[A-Z]{2}\\d{2}[A-Z0-9]{1,30}$";
		StringBuilder ibanNumbersStr = new StringBuilder();
		if (!bankAcc.matches(bankAccRegex)) {
			return false;
		}

		// Calcular si el número de cuenta IBAN es correcto
		String iban = bankAcc.substring(4) + bankAcc.substring(0, 4);
		for (char c : iban.toCharArray()) {
			if (Character.isLetter(c)) { // Si es una letra obtener su valor numérico
				ibanNumbersStr.append(Character.getNumericValue(c));
			} else {
				ibanNumbersStr.append(c);
			}
		}
		BigInteger ibanNumber = new BigInteger(ibanNumbersStr.toString()); // Convertirlo a formato numérico

		// Retornar si el módulo del número entre 97 es 1
		return ibanNumber.mod(BigInteger.valueOf(97)).intValue() == 1;
	}

	// GETTERS //
	public String getBankAcc() {
		return bankAcc;
	}

	// SETTERS //
	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}
}
