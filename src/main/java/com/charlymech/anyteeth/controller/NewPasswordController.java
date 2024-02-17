package com.charlymech.anyteeth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static com.charlymech.anyteeth.App.rb;
import static com.charlymech.anyteeth.App.showWarningAlert;
import static com.charlymech.anyteeth.controller.StaffController.staticPasswordPasswordField;
import static com.charlymech.anyteeth.controller.StaffController.staticPasswordTextField;

public class NewPasswordController {
	// Inyecciones FXML
	@FXML
	private AnchorPane pane;
	@FXML
	private Pane infoPane;
	@FXML
	private Label newPasswordTitle, passwordLabel1, passwordLabel2, passwordInfoTitle;
	@FXML
	private PasswordField password1PasswordField, password2PasswordField;
	@FXML
	private TextField password1TextField, password2TextField;
	@FXML
	private CheckBox showBothPasswordsCheckBox;
	@FXML
	private Button setNewPasswordBtn;
	@FXML
	public TextArea passwordRulesTextArea;
	// Variables de clase
	private final String passwordRegex = "^(?=.*\\d)(?=.*[A-Z])(?=.*[=\\-+*&^%$#@!.]).{8,}$";

	public void showBothPasswords(ActionEvent event) {
		if(this.showBothPasswordsCheckBox.isSelected()) { // Mostrar las contraseñas
			// Password 1
			this.password1PasswordField.setVisible(false);
			this.password1TextField.setText(this.password1PasswordField.getText().trim());
			this.password1TextField.setVisible(true);
			// Password 2
			this.password2PasswordField.setVisible(false);
			this.password2TextField.setText(this.password2PasswordField.getText().trim());
			this.password2TextField.setVisible(true);
		} else {
			// Password 1
			this.password1PasswordField.setText(this.password1TextField.getText().trim());
			this.password1PasswordField.setVisible(true);
			this.password1TextField.setVisible(false);
			// Password 2
			this.password2PasswordField.setText(this.password2TextField.getText().trim());
			this.password2PasswordField.setVisible(true);
			this.password2TextField.setVisible(false);
		}
	}

	public void setNewPassword(ActionEvent event) {
		if (this.password1PasswordField.getText().trim().isEmpty() || this.password2PasswordField.getText().trim().isEmpty()) { // Caso que alguno de los campos esté vacío
			showWarningAlert(rb.getString("alertTitle"), rb.getString("newPasswordNotFilledHeader"), rb.getString("newPasswordNotFilledBody"));
		} else if (!this.password1PasswordField.getText().trim().equals(this.password2PasswordField.getText().trim())) { // Caso no coinciden
			showWarningAlert(rb.getString("alertTitle"), rb.getString("newPasswordNotEqualsHeader"), rb.getString("newPasswordNotEqualsBody"));
		} else if (!this.password1PasswordField.getText().trim().matches(this.passwordRegex)) { // No cumple los parámetros requeridos de la contraseña
			showWarningAlert(rb.getString("alertTitle"), rb.getString("newPasswordBadPasswordHeader"), rb.getString("newPasswordBadPasswordBody"));
		} else { // Ambas son iguales y no están vacías
			Stage stage = (Stage) this.pane.getScene().getWindow();
			stage.close();
			staticPasswordPasswordField.setText(this.password1PasswordField.getText().trim());
			staticPasswordTextField.setText(this.password1PasswordField.getText().trim());
		}
	}

	protected void setLanguage() {
		this.newPasswordTitle.setText(rb.getString("newPasswordTitle"));
		this.passwordLabel1.setText(rb.getString("newPasswordPassword1"));
		this.passwordLabel2.setText(rb.getString("newPasswordPassword2"));
		this.showBothPasswordsCheckBox.setText(rb.getString("newPasswordPasswordShowPasswords"));
		this.setNewPasswordBtn.setText(rb.getString("newPasswordSetNewPassword"));
		this.passwordInfoTitle.setText(rb.getString("newPasswordInfoTitle"));
		this.passwordRulesTextArea.setText(rb.getString("newPasswordInfoTextArea"));
	}

	public void showPasswordInfo(MouseEvent mouseEvent) {
		this.infoPane.setVisible(true);
	}

	public void hidePasswordInfo(MouseEvent mouseEvent) {
		this.infoPane.setVisible(false);
	}
}
