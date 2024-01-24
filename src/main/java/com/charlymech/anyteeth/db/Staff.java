package com.charlymech.anyteeth.db;

public class Staff extends Person {
	// Variables de clase
	public static final String corporationEmailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@anyteeth.es$";
	private String staffID;
	private String password;
	private Role role;

	// Constructores
	public Staff(String identification, Identification identificationType, String fullName, Gender gender, String telephoneNumber, String email, String password, String address, Role role) {
		super(identification, identificationType, fullName, gender, telephoneNumber, email, address); // Llamar al constructor de la clase padre
		this.staffID = this.generateStaffID();
		this.password = password;
		this.role = role;
	}

	public Staff() { // Instancio el constructor vacío por si fuera necesario
	}

	/*
	 * Métodos de la clase
	 */
	// Generar IDs únicos para las alertas
	private String generateStaffID() {
		String newStaffID = "";

		// Realizar las operaciones con la base de datos necesarias para verificar que el ID sea único

		return newStaffID;
	}

	// GETTERS //
	public String getStaffID() {
		return staffID;
	}

	public String getPassword() {
		return password;
	}

	public Role getRole() {
		return role;
	}

	// SETTERS //
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	// Clase enumerada privada para delimitar los roles de los empleados en la aplicación
	public enum Role {
		ADMIN, CLINIC_ADMIN, STAFF
	}
}
