package com.charlymech.anyteeth.gui;

import com.charlymech.anyteeth.App;
import com.charlymech.anyteeth.controller.LogInController;
import com.charlymech.anyteeth.db.Conn;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Optional;

public class LoadApp extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/com/charlymech/anyteeth/layout/load-app.fxml")); // Cargar la vista
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("AnyTeeth");
		stage.setResizable(false);
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent windowEvent) {
				App.closeApp(stage);
			}
		});

		// Crear nuevo hilo para realizar la conexión a la base de datos mientras se ejecuta la vista de carga
		new Thread(() -> {
				boolean connected = Conn.connectDB(App.getServerIP(), App.getPort()); // Llamar al método para la conexión y verificar que se ha conectado o no
				if(connected) {
					Platform.runLater(() -> this.launchLogIn(stage));
				} else {
					Platform.runLater(() -> {
						Optional<ButtonType> choice = App.showErrorAlert("ERROR", "Error ejecutando el programa", "No se ha podido ejecutar correctamente el programa debido a un error con el servidor. Póngase en contacto con su administrador");
						if (choice.get() == ButtonType.OK){
							App.closeApp(stage);
						}
					});
				}
		}).start();
	}

	// Método para iniciar la interfaz del LogIn y cerrar la de carga del programa
	private void launchLogIn(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/charlymech/anyteeth/layout/login.fxml"));
			Parent root = loader.load();
			Stage loginStage = new Stage();
			Scene loginScene = new Scene(root);
			loginStage.setScene(loginScene);
			loginStage.setTitle("AnyTeeth - Log In");
			loginStage.setResizable(false);
			loginStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent windowEvent) {
					App.closeApp(loginStage);
				}
			});
			loginStage.show();

			LogInController loginController = loader.getController();
			loginController.setLanguageProperties();
			loginScene.setOnKeyPressed(new EventHandler<KeyEvent>() { // Añadir una escucha a la tecla de "ENTER" para ejecutar el método de login
				@Override
				public void handle(KeyEvent ke) {
					if(ke.getCode() == KeyCode.ENTER) {
						try {
							loginController.login(loginStage);
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}
				}
			});

			stage.close(); // Cerrar el stage de la pantalla de carga
		} catch (IOException e) {
			System.err.println("Error intentando abrir el LogIn");
			System.out.println(e.getMessage());
		}

	}
}
