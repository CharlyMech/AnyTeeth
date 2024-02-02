package com.charlymech.anyteeth.db;

import com.charlymech.anyteeth.Enums.Gender;
import com.charlymech.anyteeth.Enums.Identification;
import com.charlymech.anyteeth.Enums.MaritalStatus;
import com.charlymech.anyteeth.Enums.Province;

import java.util.ArrayList;
import java.util.Date;

public class Patient extends Person{
	// Variables de clase
	private String patientID;
	private ArrayList<Client> clients;
	private Client defaultClient;
	private ArrayList<Alert> alerts;

	// Constructors
	public Patient(String identification, Identification identificationType, String fullName, Gender gender, Date birthDate, int cp, String population, Province province, MaritalStatus maritalStatus, Date registrationDate, String telephoneNumber, String email, String address, Client defaultClient, ArrayList<Alert> alerts) {
		// En este constructor no considero que haya una lista de más de 1 Cliente, ya que por defecto el propio paciente es el mismo Cliente y este constructor será llamado en la creación de nuevos Pacientes
		// Se añaden Clientes a la lista inicializada mediante el método correspondiente
		super(identification, identificationType, fullName, gender, birthDate, telephoneNumber, email, address, cp, population, province, maritalStatus, registrationDate); // Llamar al constructor de la clase padre
		this.patientID = this.generatePatientID();
		this.defaultClient = defaultClient;
		this.clients = new ArrayList<Client>();
		this.clients.add(this.defaultClient); // Añadir automáticamente el Cliente creado por defecto
		this.alerts = new ArrayList<Alert>(alerts);
	}

	public Patient() { // Instancio el constructor vacío por si fuera necesario
	}

	/*
	 * Métodos de la clase
	 */
	// Generar IDs únicos para las alertas
	private String generatePatientID() {
		String newClientID = "";

		// Realizar las operaciones con la base de datos necesarias para verificar que el ID sea único

		return newClientID;
	}

	// GETTERS //
	public String getPatientID() {
		return patientID;
	}

	public ArrayList<Client> getClients() {
		return clients;
	}

	public Client getDefaultClient() {
		return defaultClient;
	}

	public ArrayList<Alert> getAlerts() {
		return alerts;
	}

	// SETTERS //
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public void setClients(ArrayList<Client> clients) {
		this.clients = clients;
	}

	public void setDefaultClient(Client defaultClient) {
		this.defaultClient = defaultClient;
	}

	public void setAlerts(ArrayList<Alert> alerts) {
		this.alerts = alerts;
	}
}
