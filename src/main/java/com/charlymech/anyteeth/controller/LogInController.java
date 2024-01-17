package com.charlymech.anyteeth.controller;

import com.charlymech.anyteeth.App;
import com.charlymech.anyteeth.db.Conn;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;

public class LogInController {
	// Inyecciones FXML
	@FXML
	Button login;
	@FXML
	TextField email, passwd;

	private static final String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"; // Variable estática ya que posiblemente se necesite más adelante

	public void login(ActionEvent event) throws IOException {
		// Únicamente se modificará el mensaje del error
		if (email.getText().trim().isEmpty() || passwd.getText().trim().isEmpty()) { // En caso de que alguno de los campos no esté rellenado
			App.showWarningAlert("ERROR", "Error en el Log In", "Debe de rellenar ambos campos para poder entrar al panel principal");
		} else if (!email.getText().trim().matches(emailRegex)) { // Comprobar que el email es válido mediante una expresión regular
			App.showWarningAlert("ERROR", "Error en el Log In", "El email introducido no es válido");
		} else { // Los parámetros son correctos
			// Verificar los datos con la Base de Datos
			MongoDatabase db = Conn.mongo.getDatabase("test_login"); // Especificar la base de datos a usar
			MongoCollection<Document> collection = db.getCollection("users"); // Especificar la colección

			// Crear un filtro para encontrar documentos con los valores deseados
			Document query = new Document("mail", new Document("$eq", email.getText()))
					.append("password", new Document("$eq", passwd.getText())); // TODO -> Falta hacer el hash de la contraseña
			Document result = collection.find(query).first(); // Documento resultado

			// Verificar si se encontró un usuario
			if (result != null) {
				// Ejecutar la pantalla principal y asignar el email de usuario
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/charlymech/anyteeth/layout/main.fxml"));
				Parent root = loader.load();
				MainController mainController = loader.getController(); // Cargar el controlador de la vista
				mainController.setEmail(email.getText()); // Asignar el email del usuario mediante el controlador de la vista principal
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setTitle("AnyTeeth");
				stage.setResizable(true);
				stage.show();
			} else {
				App.showWarningAlert("ERROR", "Error en el Log In", "El email o la contraseña introducidos no son válidos");
				// Reset text fields
				email.setText("");
				passwd.setText("");
			}
		}
	}
}
