package com.charlymech.anyteeth.gui;

import java.io.IOException;

import com.charlymech.anyteeth.App;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LogIn extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/com/charlymech/anyteeth/layout/login.fxml")); // Cargar la vista
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("AnyTeeth - Log In");
		stage.setResizable(false);
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() { // Utilizar el método de cierre de app cuando se usa el botón de cerrar superior
			@Override
			public void handle(WindowEvent we) {
				App.closeApp(stage);
			}
		});
	}
}