package com.charlymech.anyteeth.controller;

import com.charlymech.anyteeth.App;
import com.charlymech.anyteeth.Enums.Gender;
import com.charlymech.anyteeth.Enums.Identification;
import com.charlymech.anyteeth.Enums.MaritalStatus;
import com.charlymech.anyteeth.Enums.Province;
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
	private Label fullNameLabel, idTypeLabel, idNumberLabel, staffIdNumberLabel, birthDateLabel, ageLabel, genreLabel, maritalStatusLabel, registrationDateLabel, corporationEmailLabel, personalEmailLabel, telephoneLabel, addressLabel, cpLabel, populationLabel, provinceLabel, countryLabel, personalDataTitle, userDataTitle, roleLabel, passwordLabel, otherTitle, commentsLabel;
	@FXML
	public TextField fullNameTextField, idNumberTextField, staffIdNumberTextField, ageTextField, corporationEmailTextField, personalEmailTextField, telephoneTextField, addressTextField, cpTextField, populationTextField, countryTextField,passwordTextField;
	@FXML
	private PasswordField passwordPasswordField;
	@FXML
	private ComboBox idTypeComboBox, genreComboBox, maritalStatusComboBox, roleComboBox, provinceComboBox;
	@FXML
	private DatePicker birthDateDatePicker, registrationDateDatePicker;
	@FXML
	private ListView commentsListView;
	// Variables de clase
	private Stage staffStage;
	private final ObservableList<Identification> identifications = FXCollections.observableArrayList(Identification.DNI, Identification.NIE);
	private final ObservableList<String> genders = FXCollections.observableArrayList(Gender.MALE.toString(), Gender.FEMALE.toString());
	private final ObservableList<String> maritalStatus = FXCollections.observableArrayList(MaritalStatus.SINGLE.toString(), MaritalStatus.MARRIED_JOINTLY.toString(), MaritalStatus.MARRIED_SEPARATELY.toString(), MaritalStatus.HEAD_FAMILY.toString(), MaritalStatus.WIDOWER_DEPENDENT_CHILD.toString());
	private final ObservableList<Staff.Role> roles = FXCollections.observableArrayList(Staff.Role.STAFF, Staff.Role.CLINIC_ADMIN, Staff.Role.ADMIN);
	private final ObservableList<String> provinces = FXCollections.observableArrayList(Province.getProvincesNames());
	protected Staff staff;
	protected static boolean madeChanges = false; // Variable para manejar si el usuario modifica la información del formulario
	private Stage passwordStage; // Stage para la ventana de asignación de contraseña

	// Método de comprobación que todos los campos estén rellenados
	private boolean checkAllFields() {
		System.out.println(this.fullNameTextField);
		System.out.println(this.fullNameTextField == null);
		return true; // Todos los campos han sido rellenados
	}

	// Método para comprobar los cambios en la ventana emergente y modificar el valor de ventana en ejecución en el cierre
	public void checkCloseEvent() {
		if (!checkAllFields()) {
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
		System.out.println(this.staff);
	}

	// Método para mostrar y esconder la contraseña de la pantalla
	public void showHidePassword(ActionEvent event) {
		if(this.showPasswordToggleBtn.isSelected()) { // Toggle seleccionado -> Mostrar contraseña
			this.passwordPasswordField.setVisible(false);
			this.passwordTextField.setVisible(true);
		} else {
			this.passwordPasswordField.setVisible(true);
			this.passwordTextField.setVisible(false);
		}
	}

	// Método para lanzar la pantalla de configuración de contraseña
	public void launchChangePassword(ActionEvent event) {
		 if(this.passwordStage == null || !this.passwordStage.isShowing()) { // No existe ventana externa abierta -> Ejecutar nueva ventana
			 try {
				 FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/charlymech/anyteeth/layout/new-password.fxml"));
				 Parent root = loader.load();
				 NewPasswordController newPasswordController = loader.getController();
				 newPasswordController.setLanguage();
				 newPasswordController.setGraphics();
				 this.passwordStage = new Stage();
				 this.passwordStage.setTitle(rb.getString("newPasswordTitle") + "-" + staff.getStaffID()); // TODO -> aplicar propiedad de idioma para el título de la ventana de Nueva Contraseña
				 this.passwordStage.setScene(new Scene(root));
				 this.passwordStage.show();
				 this.passwordStage.setResizable(false);
			 } catch (Exception e) {
				 App.showErrorAlert(rb.getString("alertTitle"), rb.getString("openWindowError"), "The new Password window couldn't be opened"); // TODO -> aplicar propiedad de idioma
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
		// Tipo de ID ComboBox
		this.idTypeComboBox.setItems(this.identifications);
		// Staff ID
		this.staffIdNumberTextField.setText(this.staff.getStaffID());
		// Género ComboBox
		this.genreComboBox.setItems(this.genders);
		// Estado civil ComboBox
		this.maritalStatusComboBox.setItems(this.maritalStatus);
		// Día de Registro
		LocalDate registration;
		if ((new Staff()).searchStaffByID(staff.getStaffID()) == null) { // No existe el ID en el sistema, el origen es añadir nuevo Staff
			registration = LocalDate.now();
		} else {
			Date registrationDate = staff.getRegistrationDate();
			registration = registrationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;
		}
		this.registrationDateDatePicker.setValue(registration);
		// Provincias ComboBox
		this.provinceComboBox.setItems(this.provinces);
		// Roles ComboBox
		this.roleComboBox.setItems(this.roles);
	}

	public void setLanguage() {
		this.isActive.setText(rb.getString("staffActiveCheckBox"));
		// Personal Data //
		this.personalDataTitle.setText(rb.getString("staffPersonalDataTitle"));
		this.fullNameLabel.setText(rb.getString("staffPersonaDataFullName"));
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

//	@Override
//	public void initialize(URL url, ResourceBundle resourceBundle) {
//		setLanguage();
//		setGraphics();
//	}

	// SETTERS //
	public void setStaffStage(Stage stage) {
		this.staffStage = stage;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
}