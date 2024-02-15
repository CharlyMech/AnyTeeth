package com.charlymech.anyteeth;

import com.charlymech.anyteeth.db.Conn;
import com.charlymech.anyteeth.gui.LoadApp;
import com.mongodb.lang.NonNull;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

public class App {
	// Variables de clase
	@NonNull
	private static String serverIP;
	@NonNull
	private static int port;
	@NonNull
	private static String database;
	@NonNull
	public static ResourceBundle rb;

	// Esta clase únicamente está pensada para establecer la conexión a la base de datos y llamar al Log In
	public static void main(String[] args) {
		// Leer las configuraciones iniciales de los archivos
		try (FileInputStream fis = new FileInputStream("./target/classes/com/charlymech/anyteeth/config/server")) {
			Properties properties = new Properties();
			properties.load(fis);
			// Asignar las propiedades a las variables
			serverIP = properties.getProperty("serverIp");
			port = Integer.parseInt(properties.getProperty("port"));
			database = properties.getProperty("database");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Cargar las propiedades del idioma del sistema
		i18n();

		// Una vez cargadas las configuraciones, se inicia la aplicación
		Application.launch(LoadApp.class, args);
	}

	// Método para crear una alerta de Aviso
	public static void showWarningAlert(String title, String header, String content) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show(); // Mostrar la alerta
	}

	// Método para crear una alerta de Error
	public static Optional<ButtonType> showErrorAlert(String title, String header, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		Optional<ButtonType> choice = alert.showAndWait(); // Mostrar la alerta y captar el click en el botón del diálogo
		return choice;
	}

	// Método para finalizar la aplicación por completo
	public static void closeApp(Stage stage) {
		System.out.println(rb.getString("goodbye"));
		Conn.closeConnection();
		stage.close();
		System.exit(0); // Por si acaso
	}

	// Método para cargar las propiedades de idioma de la aplicación
	private static void i18n() {
		// Obtener el objeto Locale del sistema y asignar el bundle correspondiente
		Locale locale = Locale.getDefault();

		if (locale.toString().matches("^ca_ES$")) { // Caso especial: Sistema Operativo en Catalán -> Idioma al Español
			Locale.setDefault(new Locale("es"));
		} else if (!locale.toString().matches("^es_") && !locale.toString().matches("^en_")) { // Cualquier Locale diferente a es_ o en_ se ajusta el idioma a Inglés
			Locale.setDefault(new Locale("en"));
		}
		rb = ResourceBundle.getBundle("com/charlymech/anyteeth/lang/language");
	}

	// Método para aplicar el hash SHA-256
	public static String hash(String passwd) {
		// Hash Password in SHA-256
		// ? https://www.youtube.com/watch?v=ef3kenC4xa0&ab_channel=Randomcode

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] message = md.digest(passwd.getBytes());
			BigInteger bigInt = new BigInteger(1, message);
			return bigInt.toString(16);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("ERROR: " + e);
			return null;
		}
	}

	// GETTERS
	public static String getServerIP() {
		return serverIP;
	}

	public static int getPort() {
		return port;
	}

	public static String getDatabase() {
		return database;
	}
}
