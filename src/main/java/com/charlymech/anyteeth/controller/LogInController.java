package com.charlymech.anyteeth.controller;

import com.charlymech.anyteeth.App;
import com.charlymech.anyteeth.db.Conn;
import com.charlymech.anyteeth.db.Person;
import com.charlymech.anyteeth.db.Staff;
import com.charlymech.anyteeth.gui.LoadApp;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.charlymech.anyteeth.App.rb;

public class LogInController {
	// Inyecciones FXML
	@FXML
	private Button login;
	@FXML
	private ToggleButton showPasswordToggle;
	@FXML
	private TextField email, passwdTf;
	@FXML
	private PasswordField passwd;

	// Método lanzado desde el evento del botón para acceder al método de comprobación del login
	public void tryLaunchLogin(ActionEvent event) throws IOException {
		Stage loginStage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Coger el Stage del evento
		login(loginStage); // Lanzar el método de comprobación
	}

	// Método para comprobar si el login es correcto
	public void login(Stage loginStage) throws IOException {
		// Únicamente se modificará el mensaje del error
		if (email.getText().trim().isEmpty() || passwd.getText().trim().isEmpty()) { // En caso de que alguno de los campos no esté rellenado
			App.showWarningAlert(rb.getString("alertTitle"), rb.getString("warningLogin"), rb.getString("warningLoginEmpty"));
		} else if (!email.getText().trim().matches(Staff.corporationEmailRegex)) { // Comprobar que el email corporativo es válido mediante una expresión regular
			App.showWarningAlert(rb.getString("alertTitle"), rb.getString("warningLogin"), rb.getString("warningLoginBadEmail"));
		} else { // Los parámetros son correctos
			// Verificar los datos con la Base de Datos
			MongoDatabase db = Conn.mongo.getDatabase(App.getDatabase()); // Especificar la base de datos a usar
			MongoCollection<Document> collection = db.getCollection("staff"); // Especificar la colección

			// Crear un filtro para encontrar documentos con los valores deseados
			Document query = new Document("email", new Document("$eq", email.getText()))
					.append("password", new Document("$eq", passwd.getText())); // TODO -> Falta hacer el hash de la contraseña
			Document result = collection.find(query).first(); // Documento resultado

			// Verificar si se encontró un usuario
			if (result != null) {
				if (!result.getBoolean("active")) { // Comprobar que el usuario esté activo
					// Lo compruebo de forma separada para proporcionar un mensaje de error personalizado
					Platform.runLater(() -> {
						Optional<ButtonType> choice = App.showErrorAlert(rb.getString("alertTitle"), rb.getString("errorAccount"), rb.getString("errorAccountContent"));
						if (choice.get() == ButtonType.OK){
							App.closeApp(loginStage);
						}
					});
				}
				// Crear el objeto Staff y cargar toda la información necesaria
				Staff userSession = new Staff();
				userSession.setStaffID(result.getString("_id"));
				Document identificationDocument = result.get("identification", Document.class);
				userSession.setIdentification(identificationDocument.getString("type"));
				userSession.setIdentification(identificationDocument.getString("idNumber"));
				userSession.setFullName(result.getString("name"));
				userSession.setGender(Person.Gender.valueOf(result.getString("gender")));
				userSession.setEmail(result.getString("email"));
				userSession.setTelephoneNumber(result.getString("phone"));
				userSession.setAddress(result.getString("address"));
				userSession.setRole(Staff.Role.valueOf(result.getString("role")));
				userSession.setPassword(result.getString("password"));
				List<String> comments = result.getList("comments", String.class);
				userSession.setComments((ArrayList<String>) comments);
				// TODO -> Mirar que la cuenta esté activa

				System.out.println(rb.getString("welcome") + " " + userSession.getFullName());
				// Ejecutar la pantalla principal y asignar el email de usuario
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/charlymech/anyteeth/layout/main.fxml"));
				Parent root = loader.load();
				MainController mainController = loader.getController(); // Cargar el controlador de la vista
				mainController.setUserSession(userSession); // Asignar el objeto Staff para la sesión de usuario
				Stage stage = loginStage;
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setTitle("AnyTeeth");
				stage.setResizable(true);
				stage.setOnCloseRequest(event -> { // Asignar el método de cierre -> Cerrar sesión
					event.consume(); // Si se presiona "Cancelar" no se cierra el Stage
					mainController.logout(stage); // Llamar al método para salir de la sesión de usuario
				});
				stage.show();
				mainController.setProperties(); // Llamar al método para aplicar las propiedades de idioma
			} else {
				App.showWarningAlert(rb.getString("alertTitle"), rb.getString("warningLogin"), rb.getString("warningLoginBadUserEmail"));
				// Reset text fields
				email.setText("");
				passwd.setText("");
			}
		}
	}

	// Método para aplicar propiedades gráficas
	public void setGraphics() {
		// Login Button
		this.login.setCursor(Cursor.HAND);
		// ToggleButton contraseña
		this.showPasswordToggle.setCursor(Cursor.HAND);
	}

	// Método para aplicar las propiedades de idioma a los elementos gráficos
	public void setLanguageProperties() {
		email.setPromptText(rb.getString("loginEmailPrompt"));
		passwd.setPromptText(rb.getString("loginPasswdPrompt"));
		login.setText(rb.getString("loginBtn"));


	}

	public void passwordToggle(ActionEvent event) {
		// Crear ImageView para cada uno de los iconos
		ImageView show = new ImageView(String.valueOf(LoadApp.class.getResource("/com/charlymech/anyteeth/img/light/show_password_light_30.png")));
		ImageView hide = new ImageView(String.valueOf(LoadApp.class.getResource("/com/charlymech/anyteeth/img/light/show_password_active_light_30.png")));
		String passwdString;
		// Cuando suceda el evento -> Comprobar el estado y asignar el icono pertinente
		if (this.showPasswordToggle.isSelected()) { // Toggle seleccionado -> Mostrar contraseña
			this.showPasswordToggle.setGraphic(hide);
			passwdString = this.passwd.getText();
			this.passwd.setVisible(false);
			this.passwdTf.setText(passwdString);
			this.passwdTf.setVisible(true);
		} else { // Toggle no seleccionado -> Ocultar contraseña
			this.showPasswordToggle.setGraphic(show);
			this.passwd.setVisible(true);
			this.passwdTf.setVisible(false);
		}
	}
}
