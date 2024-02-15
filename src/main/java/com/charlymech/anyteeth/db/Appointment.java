package com.charlymech.anyteeth.db;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static com.charlymech.anyteeth.App.rb;

public class Appointment {
	// Variables de clase
	private String title;
	private String appointmentID;
	private Patient patient;
	private Staff staff;
	private LocalDate date;
	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT); // Formato corto de fecha (XX/XX/XXXX) dependiendo del Locale del sistema
	private LocalTime hStart;
	private LocalTime hEnd;
	private final DateTimeFormatter timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT); // Formato corto de hora (HH:MM o H:MMam/pm) dependiendo del Locale del sistema

	// Constructores
	public Appointment(Patient patient, Staff staff, LocalDate date, LocalTime hStart, LocalTime hEnd) {
		this.appointmentID = this.generateAppointmentID();
		this.patient = patient;
		this.staff = staff;
		this.title = this.patient.getName() + " - " + this.staff.getName(); // Título a mostrar en el elemento gráfico de la cita
		this.date = date;
		this.hStart = hStart;
		this.hEnd = hEnd;
	}

	public Appointment() { // Instancio el constructor vacío por si fuera necesario
	}

	/*
	 * Métodos de la clase
	 */
	// Generar IDs únicos para las alertas
	private String generateAppointmentID() {
		String newAppointmentID = "";

		// Realizar las operaciones con la base de datos necesarias para verificar que el ID sea único

		return newAppointmentID;
	}

	// GETTERS //
	public String getTitle() {
		return title;
	}

	public String getAppointmentID() {
		return appointmentID;
	}

	public Patient getPatient() {
		return patient;
	}

	public Staff getStaff() {
		return staff;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime gethStart() {
		return hStart;
	}

	public LocalTime gethEnd() {
		return hEnd;
	}

	// SETTERS //
	public void setTitle(String title) {
		this.title = title;
	}

	public void setAppointmentID(String appointmentID) {
		this.appointmentID = appointmentID;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void sethStart(LocalTime hStart) {
		this.hStart = hStart;
	}

	public void sethEnd(LocalTime hEnd) {
		this.hEnd = hEnd;
	}
}
