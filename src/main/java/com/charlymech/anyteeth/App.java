package com.charlymech.anyteeth;

import com.charlymech.anyteeth.db.Conn;
import com.charlymech.anyteeth.gui.LoadApp;
import com.mongodb.lang.NonNull;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.util.Optional;
import java.util.Properties;

public class App {
	private static File serverFile;
	@NonNull
	private static String serverIP;
	@NonNull
	private static int port;
	private static Parent root;

	// Esta clase únicamente está pensada para establecer la conexión a la base de datos y llamar al Log In
	public static void main(String[] args) {
		// Leer las configuraciones iniciales de los archivos
		serverFile = new File("./target/classes/com/charlymech/anyteeth/config/server");
		try (FileReader reader = new FileReader(serverFile)) {
			Properties properties = new Properties();
			properties.load(reader);
			// Asignar las propiedades a las variables
			serverIP = properties.getProperty("serverIp");
			port = Integer.parseInt(properties.getProperty("port"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Una vez cargadas las configuraciones, se inicia la aplicación
		System.out.println("BIENVENIDO A ANYTEETH"); // TEST
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
		System.out.println("Nos vemos pronto desde AnyTeeth :)");
		Conn.closeConnection();
		stage.close();
		System.exit(0); // Por si acaso
	}

	// GETTERS
	public static String getServerIP() {
		return serverIP;
	}

	public static int getPort() {
		return port;
	}
}
