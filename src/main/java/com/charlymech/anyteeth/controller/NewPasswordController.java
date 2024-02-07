package com.charlymech.anyteeth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.charlymech.anyteeth.App.rb;
import static com.charlymech.anyteeth.App.showWarningAlert;

public class NewPasswordController {
	// Inyecciones FXML
	@FXML
	private AnchorPane pane;
	@FXML
	private Label newPasswordTitle, passwordLabel1, passwordLabel2;
	@FXML
	private PasswordField password1PasswordField, password2PasswordField;
	@FXML
	private TextField password1TextField, password2TextField;
	@FXML
	private CheckBox showBothPasswordsCheckBox;
	@FXML
	private Button setNewPasswordBtn;

	public void showBothPasswords(ActionEvent event) {
		if(this.showBothPasswordsCheckBox.isSelected()) { // Mostrar las contraseñas
			// Password 1
			this.password1PasswordField.setVisible(false);
			this.password1TextField.setText(this.password1PasswordField.getText());
			this.password1TextField.setVisible(true);
			// Password 2
			this.password2PasswordField.setVisible(false);
			this.password2TextField.setText(this.password2PasswordField.getText());
			this.password2TextField.setVisible(true);
		} else {
			// Password 1
			this.password1PasswordField.setVisible(true);
			this.password1TextField.setVisible(false);
			// Password 2
			this.password2PasswordField.setVisible(true);
			this.password2TextField.setVisible(false);
		}
	}

	public void setNewPassword(ActionEvent event) {
		if (this.password1PasswordField.getText().trim().isEmpty() || this.password2PasswordField.getText().trim().isEmpty()) { // Caso que alguno de los campos esté vacío
			showWarningAlert(rb.getString("alertTitle"), rb.getString("newPasswordNotFilledHeader"), rb.getString("newPasswordNotFilledBody"));
		} else if (this.password1PasswordField.getText().trim().isEmpty() != this.password2PasswordField.getText().trim().isEmpty()) { // Caso no coinciden
			showWarningAlert(rb.getString("alertTitle"), rb.getString("newPasswordNotEqualsHeader"), rb.getString("newPasswordNotEqualsBody"));
		} else { // Ambas son iguales y no están vacías
			// TODO -> Asignar la nueva contraseña a los campos de la pantalla de Staff
			Stage stage = (Stage) this.pane.getScene().getWindow();
			stage.close();
		}
	}

	protected void setGraphics() {
	}

	protected void setLanguage() {
		this.newPasswordTitle.setText(rb.getString("newPasswordTitle"));
		this.passwordLabel1.setText(rb.getString("newPasswordPassword1"));
		this.passwordLabel2.setText(rb.getString("newPasswordPassword2"));
		this.showBothPasswordsCheckBox.setText(rb.getString("newPasswordPasswordShowPasswords"));
		this.setNewPasswordBtn.setText(rb.getString("newPasswordSetNewPassword"));
	}
}
