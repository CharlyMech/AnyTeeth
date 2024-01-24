package com.charlymech.anyteeth.db;

public class Treatment {
	// Variables de clase
	private String treatmentID;
	private String name;
	private float cost;
	private float durationHour;

	// Constructores
	public Treatment(String name, float cost, float durationHour) {
		this.treatmentID = this.generateTreatmentID(); // Llamar al método de generación de IDs para los tratamientos
		this.name = name;
		this.cost = cost;
		this.durationHour= durationHour;
	}

	public Treatment() { // Instancio el constructor vacío por si fuera necesario
	}

	/*
	 * Métodos de la clase
	 */
	// Generar IDs únicos para los tratamientos
	private String generateTreatmentID() {
		String newTreatmentID = "";

		// Realizar las operaciones con la base de datos necesarias para verificar que el ID sea único

		return newTreatmentID;
	}

	// GETTERS //
	public String getTreatmentID() {
		return treatmentID;
	}

	public String getName() {
		return name;
	}

	public float getCost() {
		return cost;
	}

	public float getDurationHour() {
		return durationHour;
	}

	// SETTERS //
	public void setTreatmentID(String treatmentID) {
		this.treatmentID = treatmentID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public void setDurationHour(float durationHour) {
		this.durationHour = durationHour;
	}
}
