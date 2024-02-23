package com.charlymech.anyteeth.controller;

import com.charlymech.anyteeth.App;
import com.charlymech.anyteeth.Enums.Gender;
import com.charlymech.anyteeth.Enums.Identification;
import com.charlymech.anyteeth.Enums.MaritalStatus;
import com.charlymech.anyteeth.db.Staff;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

import static com.charlymech.anyteeth.App.hash;
import static com.charlymech.anyteeth.App.rb;
import static com.charlymech.anyteeth.controller.ScreenController.getScreenNumber;
import static com.charlymech.anyteeth.controller.ScreenController.manageScreen;

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
		Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Coger el Stage del evento
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
			// Pasar la contraseña por el hash para realizar la comparación
			String passwdHash = hash(passwd.getText());
			// Crear el objeto Staff para acceder al método de retorno del Documento
			Staff userSession = new Staff();
			Document result = userSession.getStaffLogin(email.getText(), passwdHash);

			// Verificar si se encontró un usuario
			if (result != null) {
				if (!result.getBoolean("active")) { // Comprobar que el usuario esté activo
					// Lo compruebo de forma separada para proporcionar un mensaje de error personalizado
					Platform.runLater(() -> {
						Optional<ButtonType> choice = App.showErrorAlert(rb.getString("alertTitle"), rb.getString("errorAccount"), rb.getString("errorAccountContent"));
						if (choice.get() == ButtonType.OK) {
							App.closeApp(loginStage);
						}
					});
				}
				// Cargar toda la información necesaria
				userSession.setStaffID(result.getString("_id"));
				Document identificationDocument = result.get("identification", Document.class);
				userSession.setIdentificationType(Identification.valueOf(identificationDocument.getString("type")));
				userSession.setIdentification(identificationDocument.getString("number"));
				userSession.setName(result.getString("name"));
				userSession.setSurnames(result.getString("surnames"));
				userSession.setGender(Gender.valueOf(result.getString("genre")));
 				userSession.setBirthDate(result.getDate("birthDate"));
				userSession.setEmail(result.getString("email"));
				userSession.setTelephoneNumber(result.getString("telephone"));
				userSession.setAddress(result.getString("address"));
				userSession.setCp(result.getString("cp"));
				userSession.setPopulation(result.getString("population"));
				userSession.setProvince(result.getString("province"));
 				userSession.setMaritalStatus(MaritalStatus.valueOf(result.getString("maritalStatus")));
				userSession.setBirthDate(result.getDate("registrationDate"));
				userSession.setCorporationEmail(result.getString("corporationEmail"));
				userSession.setRole(Staff.Role.valueOf(result.getString("role")));
				userSession.setPassword(result.getString("password"));
				userSession.setRegistrationDate(result.getDate("registrationDate"));
				List<String> comments = result.getList("comments", String.class);
				userSession.setComments((ArrayList<String>) comments);
				// TODO -> Mirar que la cuenta esté activa

				// Ejecutar la pantalla principal y asignar el email de usuario
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/charlymech/anyteeth/layout/main.fxml"));
				Parent root = loader.load();
				MainController mainController = loader.getController(); // Cargar el controlador de la vista
				mainController.setUserSession(userSession); // Asignar el objeto Staff para la sesión de usuario
				mainController.setGraphics();
				mainController.setLanguage();
				Stage stage = new Stage(); // Crear nuevo Stage para el panel principal
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setTitle("AnyTeeth");
				stage.setResizable(true);
				stage.setMaximized(true);
				stage.setOnCloseRequest(event -> { // Asignar el método de cierre -> Cerrar sesión
					event.consume(); // Si se presiona "Cancelar" no se cierra el Stage
					mainController.logout(); // Llamar al método para salir de la sesión de usuario
				});
				int currentScreen = getScreenNumber(loginStage);
				// Hacer que la pantalla principal se ejecute maximizada en el monitor principal (en caso de tener varios)
				manageScreen(stage, currentScreen, true);
				stage.show();
				loginStage.close(); // Cerrar el stage del Login
				mainController.setUserSessionPermissions();
			} else {
				userSession = null; // No es necesario pero para acelerar al garbage collector
				App.showWarningAlert(rb.getString("alertTitle"), rb.getString("warningLogin"), rb.getString("warningLoginBadUserEmail"));
				// Reset text fields
				email.setText("");
				passwd.setText("");
			}
		}
	}

	public void setLanguage() {
		email.setPromptText(rb.getString("loginEmailPrompt"));
		passwd.setPromptText(rb.getString("loginPasswdPrompt"));
		login.setText(rb.getString("loginBtn"));
	}

	public void passwordToggle(ActionEvent event) {
		// Crear ImageView para cada uno de los iconos
		ImageView show = new ImageView(String.valueOf(getClass().getResource("/com/charlymech/anyteeth/img/light/show_password_light_30.png")));
		ImageView hide = new ImageView(String.valueOf(getClass().getResource("/com/charlymech/anyteeth/img/light/show_password_active_light_30.png")));
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
