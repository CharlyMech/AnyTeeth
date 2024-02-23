package com.charlymech.anyteeth.controller;

import com.charlymech.anyteeth.App;
import com.charlymech.anyteeth.Enums.Gender;
import com.charlymech.anyteeth.Enums.Identification;
import com.charlymech.anyteeth.Enums.MaritalStatus;
import com.charlymech.anyteeth.db.Person;
import com.charlymech.anyteeth.db.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static com.charlymech.anyteeth.App.rb;
import static com.charlymech.anyteeth.Enums.Identification.*;
import static com.charlymech.anyteeth.controller.ScreenController.getScreenNumber;
import static com.charlymech.anyteeth.controller.ScreenController.manageScreen;

public class StaffController {
	// Inyecciones FXML
	@FXML
	private BorderPane pane;
	@FXML
	private ImageView staffPhoto;
	@FXML
	private Button takePhotoBtn, searchPhotoBtn, removePhotoBtn, saveChanges, changePasswordBtn;
	@FXML
	private ToggleButton showPasswordToggleBtn;
	@FXML
	private CheckBox isActive;
	@FXML
	private Label nameLabel, surnameLabel, idTypeLabel, idNumberLabel, staffIdNumberLabel, birthDateLabel, ageLabel, genreLabel, maritalStatusLabel, registrationDateLabel, corporationEmailLabel, personalEmailLabel, telephoneLabel, addressLabel, cpLabel, populationLabel, provinceLabel, countryLabel, personalDataTitle, userDataTitle, roleLabel, passwordLabel, otherTitle, commentsLabel;
	@FXML
	public TextField nameTextField, surnameTextField, idNumberTextField, staffIdNumberTextField, ageTextField, corporationEmailTextField, personalEmailTextField, telephoneTextField, addressTextField, cpTextField, populationTextField, provinceTextField, countryTextField, passwordTextField;
	@FXML
	private PasswordField passwordPasswordField;
	@FXML
	private ComboBox idTypeComboBox, genreComboBox, maritalStatusComboBox, roleComboBox;
	@FXML
	private DatePicker birthDateDatePicker, registrationDateDatePicker;
	@FXML
	private ListView commentsListView;
	// Variables de clase
	private Stage staffStage;
	private final ObservableList<String> identifications = FXCollections.observableArrayList(Identification.getIdentifications());
	private final ObservableList<String> genders = FXCollections.observableArrayList(Gender.getGenders());
	private final ObservableList<String> maritalStatus = FXCollections.observableArrayList(MaritalStatus.getMaritalStatus());
	private final ObservableList<Staff.Role> roles = FXCollections.observableArrayList(Staff.Role.STAFF, Staff.Role.CLINIC_ADMIN, Staff.Role.ADMIN);
	protected Staff staff;
	protected static PasswordField staticPasswordPasswordField;
	protected static TextField staticPasswordTextField;
	private Stage passwordStage; // Stage para la ventana de asignación de contraseña
	private boolean hasMadeChanges = false; // Esta variable se usará para detectar cualquier cambio en el formulario

	// Método de comprobación que todos los campos estén rellenados
	private boolean checkAllFieldsFilled() {
		// Comprobar que todos los campos han sido rellenados
		if (!this.nameTextField.getText().trim().isEmpty()
				&& !this.surnameTextField.getText().trim().isEmpty()
				&& this.idTypeComboBox.getValue() != null
				&& !this.idNumberTextField.getText().trim().isEmpty()
				&& this.birthDateDatePicker.getValue() != null
				&& this.genreComboBox.getValue() != null
				&& this.maritalStatusComboBox.getValue() != null
				&& !this.corporationEmailTextField.getText().trim().isEmpty()
				&& !this.personalEmailTextField.getText().trim().isEmpty()
				&& !this.telephoneTextField.getText().trim().isEmpty()
				&& !this.addressTextField.getText().trim().isEmpty()
				&& !this.cpTextField.getText().trim().isEmpty()
				&& !this.populationTextField.getText().trim().isEmpty()
				&& !this.provinceTextField.getText().trim().isEmpty()
				&& this.roleComboBox.getValue() != null
				&& !this.passwordPasswordField.getText().trim().isEmpty()
				&& !this.passwordTextField.getText().trim().isEmpty() // Por si acaso
		) { // Todos los campos han sido rellenados
			return true;
		}
		return false; // Faltan campos a rellenar
	}

	// Método para comprobar los cambios en la ventana emergente y modificar el valor de ventana en ejecución en el cierre
	public void checkCloseEvent() {
		if (!checkAllFieldsFilled() && this.hasMadeChanges) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // Crear la alerta de tipo confirmación
			alert.setTitle(rb.getString("staffChangesTitle"));
			alert.setHeaderText(rb.getString("staffChangesHeader"));
			alert.setContentText(rb.getString("staffChangesContent"));

			if (alert.showAndWait().get() == ButtonType.OK) { // El usuario desea salir de la app
				Stage stage = (Stage) this.staffStage.getScene().getWindow();
				stage.close();
			}
		} else {
			Stage stage = (Stage) this.pane.getScene().getWindow();
			stage.close();
		}
	}

	// Método de comprobación de campos y creación de usuarios
	public void saveChanges(ActionEvent event) {
		// TODO -> Listener para presionar el enter
		if (checkAllFieldsFilled()) { // Todos los campos han sido rellenados
			// Comprobar el formato del identificador personal
			boolean idFormat = false;
			Identification[] identificationList = Identification.values();
			Identification identification = identificationList[this.idTypeComboBox.getSelectionModel().getSelectedIndex()];
			if (identification == DNI) {
				idFormat = checkDNI(this.idNumberTextField.getText().trim().toUpperCase());
			} else if (identification == NIE) {
				idFormat = checkNIE(this.idNumberTextField.getText().trim().toUpperCase());
			} else {
				System.out.println("Pasaporte no implementado aún");
			}
			// Comprobar las expresiones de campos que requieren de comprobación de formato
			if (!idFormat) { // La expresión para el tipo de ID no es correcta
				App.showWarningAlert(rb.getString("alertTitle"), rb.getString("staffBadIDHeader"), rb.getString("staffBadIDBody"));
			} else if (!this.corporationEmailTextField.getText().trim().matches(Staff.corporationEmailRegex)) { // Email de empresa incorrecto
				App.showWarningAlert(rb.getString("alertTitle"), rb.getString("staffBadEmailHeader"), rb.getString("staffBadCorporationEmailBody"));
			} else if (!this.personalEmailTextField.getText().trim().matches(Person.genericEmailRegex)) { // Email personal incorrecto
				App.showWarningAlert(rb.getString("alertTitle"), rb.getString("staffBadEmailHeader"), rb.getString("staffBadPersonalEmailBody"));
			} else if (!this.telephoneTextField.getText().trim().matches(Person.telephoneNumberRegex)) { // Formato de teléfono incorrecto
				App.showWarningAlert(rb.getString("alertTitle"), rb.getString("staffBadTelephoneHeader"), rb.getString("staffBadTelephoneBody"));
			} else if (!this.cpTextField.getText().trim().matches("\\d{5}")) { // Formato de CP incorrecto
				App.showWarningAlert(rb.getString("alertTitle"), rb.getString("staffBadCPHeader"), rb.getString("staffBadCPBody"));
			} else { // Todos los formatos son correctos
				// Identificar los valores de los ComboBox mediante el índice
				Gender[] genderList = Gender.class.getEnumConstants();
				Gender staffGender = genderList[this.genreComboBox.getSelectionModel().getSelectedIndex()];
				MaritalStatus[] statusList = MaritalStatus.class.getEnumConstants();
				MaritalStatus staffStatus = statusList[this.maritalStatusComboBox.getSelectionModel().getSelectedIndex()];
				Staff createStaff = new Staff(this.staffIdNumberTextField.getText().trim(), // Crear el objeto Staff para que lo lea el método de inserción en la BD
						this.idNumberTextField.getText().trim(),
						Identification.valueOf(this.idTypeComboBox.getValue().toString()),
						this.nameTextField.getText().trim(),
						this.surnameTextField.getText().trim(),
						staffGender,
						Date.from(this.birthDateDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
						this.telephoneTextField.getText(),
						this.personalEmailTextField.getText(),
						this.addressTextField.getText(),
						this.cpTextField.getText(),
						this.populationTextField.getText(),
						this.provinceTextField.getText(),
						staffStatus,
						Date.from(this.registrationDateDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
						this.corporationEmailTextField.getText().trim(),
						App.hash(this.passwordPasswordField.getText()), // Contraseña pasada por un hash
						Staff.Role.valueOf(this.roleComboBox.getValue().toString())
				);
				// Comprobar que no existe un usuario con el mismo email corporativo en la DB
				if (createStaff.getStaffLogin(createStaff.getCorporationEmail()) == null) { // No existe usuario en el sistema
					createStaff.insertStaff(createStaff);
					// TODO -> Actualizar en tiempo real la tabla (TableView)
					// TODO -> ? Mostrar mensaje informativo ?
					this.staffStage.close();
				} else { // Existe usuario con el mismo mail en el sistema
					App.showWarningAlert(rb.getString("alertTitle"), rb.getString("staffCorporationMailAlreadyStoredHeader"), rb.getString("staffCorporationMailAlreadyStoredBody"));
				}
			}
		} else {
			App.showWarningAlert(rb.getString("staffMissingFieldsTitle"), rb.getString("staffMissingFieldsHeader"), rb.getString("staffMissingFieldsBody"));
		}
		//! BUG
		// TODO -> Cuando están todos los campos rellenados me deja cerrar sin preguntar si desea guardar los cambios
	}

	// Método para mostrar y esconder la contraseña de la pantalla
	public void showHidePassword(ActionEvent event) {
		if (this.showPasswordToggleBtn.isSelected()) { // Toggle seleccionado -> Mostrar contraseña
			this.passwordPasswordField.setVisible(false);
			this.passwordTextField.setVisible(true);
		} else {
			this.passwordPasswordField.setVisible(true);
			this.passwordTextField.setVisible(false);
		}
	}

	// Método para lanzar la pantalla de configuración de contraseña
	public void launchChangePassword(ActionEvent event) {
		if (this.passwordStage == null || !this.passwordStage.isShowing()) { // No existe ventana externa abierta -> Ejecutar nueva ventana
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/charlymech/anyteeth/layout/new-password.fxml"));
				Parent root = loader.load();
				NewPasswordController newPasswordController = loader.getController();
				newPasswordController.setLanguage();
				this.passwordStage = new Stage();
				this.passwordStage.setTitle(rb.getString("newPasswordTitle") + "-" + staff.getStaffID());
				this.passwordStage.setScene(new Scene(root));
				this.passwordStage.setWidth(400);
				this.passwordStage.setHeight(350);
				int currentScreen = getScreenNumber(this.staffStage);
				manageScreen(this.staffStage, currentScreen, false);
				this.passwordStage.show();
				this.passwordStage.setResizable(false);
			} catch (Exception e) {
				App.showErrorAlert(rb.getString("alertTitle"), rb.getString("openWindowError"), rb.getString("staffNewPasswordError"));
				e.printStackTrace();
			}
		} else { // Existe una ventana externa en ejecución -> Traerla al frente
			this.passwordStage.toFront();
		}
	}

	public void takePhoto(ActionEvent event) {
	}

	public void searchPhoto(ActionEvent event) {
	}

	public void removePhoto(ActionEvent event) {
	}

	public void setGraphics() {
		// Añadir eventos de cambios a los elementos editables -//
		this.nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
				this.hasMadeChanges = true;
			}
		});
		this.surnameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
				this.hasMadeChanges = true;
			}
		});
		this.idTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.hasMadeChanges = true;
			}
		});
		this.idNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
				this.hasMadeChanges = true;
			}
		});
		this.birthDateDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.hasMadeChanges = true;
			}
		});
		this.genreComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.hasMadeChanges = true;
			}
		});
		this.maritalStatusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.hasMadeChanges = true;
			}
		});
		this.corporationEmailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
				this.hasMadeChanges = true;
			}
		});
		this.personalEmailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
				this.hasMadeChanges = true;
			}
		});
		this.telephoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
				this.hasMadeChanges = true;
			}
		});
		this.addressTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
				this.hasMadeChanges = true;
			}
		});
		this.cpTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
				this.hasMadeChanges = true;
			}
		});
		this.provinceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				this.hasMadeChanges = true;
			}
		});
		this.populationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
				this.hasMadeChanges = true;
			}
		});
		this.roleComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.hasMadeChanges = true;
			}
		});
		this.passwordPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
				this.hasMadeChanges = true;
			}
		});
		this.passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
				this.hasMadeChanges = true;
			}
		});
		// TODO Sistema para añadir comentarios y su listener
		//- Añadir eventos de cambios a los elementos editables //

		// Tipo de ID ComboBox
		this.idTypeComboBox.setItems(this.identifications);
		// Staff ID
		this.staffIdNumberTextField.setText(this.staff.getStaffID());
		// Género ComboBox
		this.genreComboBox.setItems(this.genders);
		// Estado civil ComboBox
		this.maritalStatusComboBox.setItems(this.maritalStatus);
		// Día de Registro -> En caso de que se acceda desde addUser se mostrará esta fecha
		this.registrationDateDatePicker.setValue(LocalDate.now());
		// Roles ComboBox
		this.roleComboBox.setItems(this.roles);
		// Campos para la contraseña; Asignar los objetos FXML a los estáticos para la comunicación con la ventana de nueva contraseña
		staticPasswordPasswordField = this.passwordPasswordField;
		staticPasswordTextField = this.passwordTextField;
	}

	public void setLanguage() {
		this.isActive.setText(rb.getString("staffActiveCheckBox"));
		// Personal Data //
		this.personalDataTitle.setText(rb.getString("staffPersonalDataTitle"));
		this.nameLabel.setText(rb.getString("staffPersonaDataName"));
		this.surnameLabel.setText(rb.getString("staffPersonaDataSurname"));
		this.idTypeLabel.setText(rb.getString("staffPersonalDataIDType"));
		this.idNumberLabel.setText(rb.getString("staffPersonalDataIDNumber"));
		this.staffIdNumberLabel.setText(rb.getString("staffPersonalDataStaffIDNumber"));
		this.birthDateLabel.setText(rb.getString("staffPersonalDataBirthDate"));
		this.ageLabel.setText(rb.getString("staffPersonalDataAge"));
		this.genreLabel.setText(rb.getString("staffPersonalDataGenre"));
		this.maritalStatusLabel.setText(rb.getString("staffPersonalDataMaritalStatus"));
		this.registrationDateLabel.setText(rb.getString("staffPersonalDataRegistrationDate"));
		this.corporationEmailLabel.setText(rb.getString("staffPersonalDataCorporationEmail"));
		this.personalEmailLabel.setText(rb.getString("staffPersonalDataPersonalEmail"));
		this.telephoneLabel.setText(rb.getString("staffPersonalDataTelephone"));
		this.addressLabel.setText(rb.getString("staffPersonalDataAddress"));
		this.cpLabel.setText(rb.getString("staffPersonalDataCP"));
		this.populationLabel.setText(rb.getString("staffPersonalDataPopulation"));
		this.provinceLabel.setText(rb.getString("staffPersonalDataProvince"));
		this.countryLabel.setText(rb.getString("staffPersonalDataCountry"));
		// User Data //
		this.userDataTitle.setText(rb.getString("staffUserDataTitle"));
		this.roleLabel.setText(rb.getString("staffUserDataRole"));
		this.passwordLabel.setText(rb.getString("staffUserDataCurrentPassword"));
		this.changePasswordBtn.setText(rb.getString("staffUserDataChangePassword"));
		// Other //
		this.otherTitle.setText(rb.getString("staffOtherTitle"));
		this.commentsLabel.setText(rb.getString("staffOtherComments"));
		// Save //
		this.saveChanges.setText(rb.getString("staffSave"));
	}

	// Método para rellenar todos los campos de un usuario dado
	public void setUserData() {
		this.isActive.setSelected(staff.isActive());
		this.nameTextField.setText(staff.getName());
		this.surnameTextField.setText(staff.getSurnames());
		// TODO -> ID Type ComboBox
		this.idNumberTextField.setText(staff.getIdentification());
		this.staffIdNumberTextField.setText(staff.getStaffID());
		this.birthDateDatePicker.setValue(staff.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		// TODO -> Calcular edad
		// TODO -> Genre ComboBox
		// TODO -> MaritalStatus ComboBox
		this.registrationDateDatePicker.setValue(staff.getRegistrationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		this.corporationEmailTextField.setText(staff.getCorporationEmail());
		this.personalEmailTextField.setText(staff.getEmail());
		this.telephoneTextField.setText(staff.getTelephoneNumber());
		this.addressTextField.setText(staff.getAddress());
		this.cpTextField.setText(staff.getCp());
		this.populationTextField.setText(staff.getPopulation());
		this.provinceTextField.setText(staff.getProvince());
		// TODO -> Country
		// TODO -> Staff Role ComboBox
		// TODO Comments
	}

	// SETTERS //
	public void setStaffStage(Stage stage) {
		this.staffStage = stage;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void setStaffActive(ActionEvent event) {
	}
}