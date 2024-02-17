package com.charlymech.anyteeth.db;

import com.charlymech.anyteeth.Enums.*;

import java.util.ArrayList;
import java.util.Date;

public class Person {
	// Variables de clase
	protected String identification;
	protected Identification identificationType;
	protected String name;
	protected String surnames;
	protected Gender gender;
	protected Date birthDate;
	protected String telephoneNumber;
	public static final String telephoneNumberRegex = "^\\d{3}[-\\s]?\\d{3}[-\\s]?\\d{3}$";
	protected String email;
	public static final String genericEmailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	protected String address;
	protected String cp;
	protected String population;
	protected Province province;
	protected MaritalStatus maritalStatus;
	protected Date registrationDate;
	protected ArrayList<String> comments;
	protected boolean active;

	// Constructores
	public Person(String identification, Identification identificationType, String name, String surnames, Gender gender, Date birthDate , String telephoneNumber, String email, String address, String cp, String population, Province province, MaritalStatus maritalStatus, Date registrationDate) {
		this.identification = identification;
		this.identificationType = identificationType;
		this.name = name;
		this.surnames = surnames;
		this.gender = gender;
		this.birthDate = birthDate;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.address = address;
		this.cp = cp;
		this.population = population;
		this.province = province;
		this.maritalStatus = maritalStatus;
		this.registrationDate = registrationDate;
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
		return this.identification;
	}

	public Identification getIdentificationType() {
		return this.identificationType;
	}

	public String getName() {
		return this.name;
	}

	public String getSurnames() {
		return this.surnames;
	}

	public Gender getGender() {
		return this.gender;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public String getTelephoneNumber() {
		return this.telephoneNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public String getAddress() {
		return this.address;
	}

	public String getCp() {
		return this.cp;
	}

	public String getPopulation() {
		return this.population;
	}

	public Province getProvince() {
		return this.province;
	}

	public MaritalStatus getMaritalStatus() {
		return this.maritalStatus;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public ArrayList<String> getComments() {
		return this.comments;
	}

	public boolean isActive() {
		return this.active;
	}

	// SETTERS //
	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public void setIdentificationType(Identification identificationType) {
		this.identificationType = identificationType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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

	public void setCp(String cp) {
		this.cp = cp;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
