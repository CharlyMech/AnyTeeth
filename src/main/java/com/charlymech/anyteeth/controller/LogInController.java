package com.charlymech.anyteeth.controller;

import com.charlymech.anyteeth.gui.LogIn;
//import com.charlymech.guiviewtest.gui.MainPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {
	// Inyecciones FXML
	@FXML
	Button login;
	@FXML
	TextField email, passwd;

	private Stage stage;
	private Scene scene;
	private Parent root;

	public void login(ActionEvent event) throws IOException {
		if(email.getText().trim().isEmpty() || passwd.getText().trim().isEmpty()) { // Comprobar que ambos campos están rellenados
			System.out.println("Rellena ambos campos por favor"); // TEST
		} else {
			//* Código para iniciar el panel principal  // OLD //
//			System.out.println(email.getText() + "\t" + email.getText().trim());
//			System.out.println(passwd.getText() + "\t" + passwd.getText().trim());
//
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/charlymech/guiviewtest/fxml/mainpane.fxml"));
//			this.root = loader.load();
//
//			MainPaneController main = loader.getController();
//			main.setEmail(email.getText());
//
//			this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//			this.scene = new Scene(root);
//			this.stage.setScene(scene);
//			this.stage.show();

			// TEST
			System.out.println("Se entra a la pestaña principal");
			System.out.println(email.getText() + "\t" + email.getText().trim());
			System.out.println(passwd.getText() + "\t" + passwd.getText().trim());
		}
	}
}
