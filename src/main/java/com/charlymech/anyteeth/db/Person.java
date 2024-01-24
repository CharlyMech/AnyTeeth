package com.charlymech.anyteeth.db;

import java.util.ArrayList;

public class Person {
	// Variables de clase
	protected String identification;
	protected Identification identificationType;
	protected String fullName;
	protected Gender gender;
	protected String telephoneNumber;
	protected String telephoneNumberRegex = "^(\\+\\d{1,3})? (\\d ?){1,14}$";
	protected String email;
	public static final String genericEmailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	protected String address;
	protected ArrayList<String> comments;
	protected boolean active;

	// Constructores
	public Person(String identification, Identification identificationType, String fullName, Gender gender, String telephoneNumber, String email, String address) {
		this.identification = identification;
		this.identificationType = identificationType;
		this.fullName = fullName;
		this.gender = gender;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.address = address;
		this.comments = new ArrayList<>(); // Inicializar el array de comentarios de la Persona
		this.active = true; // Inicializar el valor a verdadero por defecto
	}

	public Person() { // Instancio el constructor vacío por si fuera necesario
	}

	/*
	 * Métodos de la clase
	 */
	public void addComment(String comment) {
		this.comments.add(comment);
	}

	// GETTERS //
	public String getIdentification() {
		return identification;
	}

	public Identification getIdentificationType() {
		return this.identificationType;
	}

	public String getFullName() {
		return fullName;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public ArrayList<String> getComments() {
		return comments;
	}

	public boolean isActive() {
		return active;
	}

	public Gender getGender() {
		return this.gender;
	}

	// SETTERS //
	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public void setIdentificationType(Identification identificationType) {
		this.identificationType = identificationType;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	// Clase enumerada para distinguir los dos únicos géneros
	public enum Gender {
		MALE, FEMALE;
	}

	// Clase enumerada para almacenar los tipos de identificación y sus métodos necesarios
	public enum Identification {
		DNI, NIE;

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

		public static boolean checkNIE(String nie) { // Método para verificar el NIE
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
	}
}
