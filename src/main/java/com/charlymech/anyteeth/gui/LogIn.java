package com.charlymech.anyteeth.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LogIn extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/com/charlymech/anyteeth/layout/login.fxml")); // Cargar la vista
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setTitle("Log In");
		stage.setResizable(false);
		stage.show();
	}
}
