package com.charlymech.anyteeth.gui;

import com.charlymech.anyteeth.App;
import com.charlymech.anyteeth.db.Conn;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

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
			LogIn logIn = new LogIn(); // Instanciar el objeto del LogIn
			Stage logInStage = new Stage(); // Crear un Stage para el LogIn
			logIn.start(logInStage);

			stage.close(); // Cerrar el Stage de la anterior ventana
		} catch (IOException e) {
			System.out.println("Error intentando abrir el LogIn");
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

	}
}
