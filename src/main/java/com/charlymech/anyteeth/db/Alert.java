package com.charlymech.anyteeth.db;

public class Alert {
	// Variables de clase
	private String alertID;
	private String title;
	private String description;
	private boolean custom;

	// Constructores
	public Alert(String title, String description) { // Alerta con descripción
		// Si se llama a este constructor quiere decir que se crea una alerta customizada
		this.alertID = this.generateAlertID(); // Llamar al método de generación de IDs para las alertas
		this.title = title;
		this.description = description;
		this.custom = true;
	}

	public Alert(String title) { // Alerta sin descripción
		// Si se llama a este constructor quiere decir que se crea una alerta customizada
		this.alertID = this.generateAlertID(); // Llamar al método de generación de IDs para las alertas
		this.title = title;
		this.description = null; // Dejar el campo en null para insertarlo así en la base de datos
		this.custom = true;
	}

	public Alert() { // Instancio el constructor vacío por si fuera necesario
	}

	/*
	* Métodos de la clase
	*/
	// Generar IDs únicos para las alertas
	private String generateAlertID() {
		String newAlertID = "";

		// Realizar las operaciones con la base de datos necesarias para verificar que el ID sea único

		return newAlertID;
	}

	// GETTERS //
	public String getAlertID() {
		return alertID;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public boolean isCustom() {
		return custom;
	}

	// SETTERS //
	public void setAlertID(String alertID) {
		this.alertID = alertID;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}
}
