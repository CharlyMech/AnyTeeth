package com.charlymech.anyteeth.db;

import com.charlymech.anyteeth.App;
import com.charlymech.anyteeth.Enums.Gender;
import com.charlymech.anyteeth.Enums.Identification;
import com.charlymech.anyteeth.Enums.MaritalStatus;
import com.charlymech.anyteeth.Enums.Province;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Date;
import java.util.Random;

public class Staff extends Person {
	// Variables de clase
	public static final String corporationEmailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@anyteeth.es$";
	private String staffID;
	private String corporationEmail;
	private String password;
	private Role role;

	// Constructores
	public Staff(String staffID, String identification, Identification identificationType, String name, String surnames, Gender gender, Date birthDate, String telephoneNumber, String email, String address, int cp, String population, Province province, MaritalStatus maritalStatus, Date registrationDate, String corporationEmail, String password, Role role) {
		super(identification, identificationType, name, surnames, gender, birthDate, telephoneNumber, email, address, cp, population, province, maritalStatus, registrationDate); // Llamar al constructor de la clase padre
		this.staffID = staffID;
		this.corporationEmail = corporationEmail;
		this.password = password;
		this.role = role;
	}

	public Staff() { // Instancio el constructor vacío por si fuera necesario
	}

	/*
	 * Métodos de la clase
	 */
	// Generar IDs únicos para las alertas
	public String generateStaffID() {
		char[] availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		StringBuilder newStaffIDBuilder = new StringBuilder("STF");

		// Generar los caracteres aleatorios del ID
		Random random = new Random();
		for (int i = 0; i < 5; i++) { // 5 caracteres extra para el ID
			char c = availableChars[random.nextInt(availableChars.length)];
			newStaffIDBuilder.append(c);
		}
		String newStaffID = newStaffIDBuilder.toString();
		// Llamar al método para comprobar si el ID generado existe
		Document result = searchStaffByID(newStaffID);
		if (result != null) {
			generateStaffID(); // Aplicar recursión para volver a generar un nuevo ID
		}
		return newStaffID;
	}

	// Método para buscar Staff mediante ID
	public Document searchStaffByID(String id) {
		if (!id.matches("^STF[A-Z0-9]{5}$")) { // Comprobar que el ID valide la expresión regular
			System.out.println("Invalid Staff ID to search");
			return null;
		}

		MongoDatabase db = Conn.mongo.getDatabase(App.getDatabase()); // Especificar la base de datos a usar
		MongoCollection<Document> staffCollection = db.getCollection("staff"); // Seleccionar la colección
		Document result = staffCollection.findOneAndDelete(new Document("_id", new Document("$eq", id))); // Ejecutar la consulta
		// Retornar el Document -> Puede ser null o contener un ID existente
		return result;
	}

	// GETTERS //
	public String getStaffID() {
		return this.staffID;
	}

	public String getCorporationEmail() {
		return this.corporationEmail;
	}

	public String getPassword() {
		return this.password;
	}

	public Role getRole() {
		return this.role;
	}

	// SETTERS //
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public void setCorporationEmail(String corporationEmail) {
		this.corporationEmail = corporationEmail;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	// TO STRING //
	@Override
	public String toString() {
		return "Staff{" +
				"staffID='" + staffID + '\'' +
				", corporationEmail='" + corporationEmail + '\'' +
				", password='" + password + '\'' +
				", role=" + role +
				", identification='" + identification + '\'' +
				", identificationType=" + identificationType +
				", name='" + name + '\'' +
				", surnames='" + surnames + '\'' +
				", gender=" + gender +
				", birthDate=" + birthDate +
				", telephoneNumber='" + telephoneNumber + '\'' +
				", email='" + email + '\'' +
				", address='" + address + '\'' +
				", cp=" + cp +
				", population='" + population + '\'' +
				", province=" + province +
				", maritalStatus=" + maritalStatus +
				", registrationDate=" + registrationDate +
				", comments=" + comments +
				", active=" + active +
				'}';
	}

	// Métodos CRUD //
	public static void insertStaff(Staff createStaff) {
		System.out.println(createStaff.toString());
	}

	// Clase enumerada privada para delimitar los roles de los empleados en la aplicación
	public enum Role {
		ADMIN, CLINIC_ADMIN, STAFF
	}
}
