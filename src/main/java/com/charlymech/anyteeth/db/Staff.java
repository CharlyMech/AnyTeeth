package com.charlymech.anyteeth.db;

import com.charlymech.anyteeth.App;
import com.charlymech.anyteeth.Enums.Gender;
import com.charlymech.anyteeth.Enums.Identification;
import com.charlymech.anyteeth.Enums.MaritalStatus;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
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
	public Staff(String staffID, String identification, Identification identificationType, String name, String surnames, Gender gender, Date birthDate, String telephoneNumber, String email, String address, String cp, String population, String province, MaritalStatus maritalStatus, Date registrationDate, String corporationEmail, String password, Role role) {
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
		Document result = getStaffByID(newStaffID);
		if (result != null) {
			generateStaffID(); // Aplicar recursión para volver a generar un nuevo ID
		}
		return newStaffID;
	}

	// Método para buscar Staff mediante ID
	public Document getStaffByID(String id) {
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

	// Método sobrecargado para obtener un Documento de Staff mediante el email o el email y la contraseña
	//? Considero que el mail que debe ser único en la DB es el corporativo aunque podría ser también el personal, ya que perdería el sentido duplicar un email personal
	public Document getStaffLogin(String corporationEmail) {
		MongoDatabase db = Conn.mongo.getDatabase(App.getDatabase()); // Especificar la base de datos a usar
		MongoCollection<Document> collection = db.getCollection("staff"); // Especificar la colección
		// Crear un filtro para encontrar documentos con los valores deseados
		Document query = new Document("email", new Document("$eq", corporationEmail));
		// Retornar la primera coincidencia en la DB (solo habrá una, ya que el mail corporativo es único)
		return collection.find(query).first();
	}

	public Document getStaffLogin(String corporationEmail, String password) {
		MongoDatabase db = Conn.mongo.getDatabase(App.getDatabase()); // Especificar la base de datos a usar
		MongoCollection<Document> collection = db.getCollection("staff"); // Especificar la colección
		// Crear un filtro para encontrar documentos con los valores deseados
		Document query = new Document("corporationEmail", new Document("$eq", corporationEmail))
				.append("password", new Document("$eq", password));
		// Retornar la primera coincidencia en la DB (solo habrá una, ya que el mail corporativo es único)
		return collection.find(query).first();
	}

	public ArrayList<Staff> findAllStaff() {
		ArrayList<Staff> staffList = new ArrayList<>();
		MongoDatabase db = Conn.mongo.getDatabase(App.getDatabase()); // Especificar la base de datos a usar
		MongoCollection<Document> collection = db.getCollection("staff"); // Especificar la colección
		try (MongoCursor<Document> cursor = collection.find().iterator();) {
			while(cursor.hasNext()) {
				Document doc = cursor.next();
				Staff stf = this.fromDocumentToStaff(doc);
				staffList.add(stf);
			}
		}

		return staffList;
	}

	public Staff fromDocumentToStaff(Document doc) {
		Staff staff = new Staff();
		staff.setStaffID(doc.getString("_id"));
		Document identificationDocument = doc.get("identification", Document.class);
		staff.setIdentificationType(Identification.valueOf(identificationDocument.getString("type")));
		staff.setIdentification(identificationDocument.getString("number"));
		staff.setName(doc.getString("name"));
		staff.setSurnames(doc.getString("surnames"));
		staff.setGender(Gender.valueOf(doc.getString("genre")));
		staff.setBirthDate(doc.getDate("birthDate"));
		staff.setEmail(doc.getString("email"));
		staff.setTelephoneNumber(doc.getString("telephone"));
		staff.setAddress(doc.getString("address"));
		staff.setCp(doc.getString("cp"));
		staff.setPopulation(doc.getString("population"));
		staff.setProvince(doc.getString("province"));
		staff.setMaritalStatus(MaritalStatus.valueOf(doc.getString("maritalStatus")));
		staff.setRegistrationDate(doc.getDate("registrationDate"));
		staff.setCorporationEmail(doc.getString("corporationEmail"));
		staff.setRole(Role.valueOf(doc.getString("role")));
		staff.setActive(doc.getBoolean("active"));
		// TODO -> Get all comments

		return staff;
	}

	// Método para crear un nuevo usuario en la DB
	public void insertStaff(Staff staff) {
		System.out.println(staff.toString());
		// Crear el Document para el nuevo Staff e ir añadiendo los campos
		Document doc = new Document();
		doc.append("_id", staff.getStaffID()); // Staff ID
		doc.append("identification", new Document("type", staff.getIdentificationType().toString()).append("number", staff.getIdentification())); // Identificación
		doc.append("name", staff.getName()); // Nombre
		doc.append("surnames", staff.getSurnames()); // Apellidos
		doc.append("genre", staff.getGender().toString()); // Sexo
		doc.append("birthDate", staff.getBirthDate()); // Fecha de nacimiento
		doc.append("email", staff.getEmail()); // Email personal
		doc.append("telephone", staff.getTelephoneNumber()); // Número de teléfono
		doc.append("address", staff.getAddress()); // Dirección
		doc.append("cp", staff.getCp()); // Código postal
		doc.append("population", staff.getPopulation()); // Población
		doc.append("province", staff.getProvince()); // Provincia
		doc.append("maritalStatus", staff.getMaritalStatus().toString()); // Estado civil
		doc.append("registrationDate", staff.getRegistrationDate()); // Fecha de registro del usuario
		doc.append("comments", staff.getComments()); // Comentarios sobre el usuario
		doc.append("corporationEmail", staff.getCorporationEmail()); // Email corporativo
		doc.append("password", staff.getPassword()); // Contraseña pasada por un hash
		doc.append("role", staff.getRole().toString()); // Rol del usuario en el sistema
		doc.append("active", staff.isActive()); // Estado del usuario (activo por defecto and crearse)

		// Instanciar los objetos de MongoDB e insertar el nuevo Document
		MongoDatabase db = Conn.mongo.getDatabase(App.getDatabase()); // Especificar la base de datos a usar
		MongoCollection<Document> staffCollection = db.getCollection("staff"); // Seleccionar la colección
		staffCollection.insertOne(doc);
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

	// Clase enumerada privada para delimitar los roles de los empleados en la aplicación
	public enum Role {
		ADMIN, CLINIC_ADMIN, STAFF
	}
}
