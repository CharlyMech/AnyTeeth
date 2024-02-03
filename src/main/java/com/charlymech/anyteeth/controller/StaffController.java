package com.charlymech.anyteeth.controller;

import com.charlymech.anyteeth.Enums.Gender;
import com.charlymech.anyteeth.Enums.Identification;
import com.charlymech.anyteeth.Enums.MaritalStatus;
import com.charlymech.anyteeth.Enums.Province;
import com.charlymech.anyteeth.db.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

import static com.charlymech.anyteeth.App.rb;

public class StaffController implements Initializable {
	// Inyecciones FXML
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
	private TextField fullNameTextField, idNumberTextField, staffIdNumberTextField, ageTextField, corporationEmailTextField, personalEmailTextField, telephoneTextField, addressTextField, cpTextField, populationTextField, countryTextField, passwordTextField;
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
	private Staff staff;

	// Método para comprobar los cambios en la ventana emergente y modificar el valor de ventana en ejecución en el cierre
	public void checkCloseEvent() {
		this.staffStage.close();
	}

	public void saveChanges(ActionEvent event) {
	}

	public void showHidePassword(ActionEvent event) {
	}

	public void launchChangePassword(ActionEvent event) {
	}

	public void takePhoto(ActionEvent event) {
	}

	public void searchPhoto(ActionEvent event) {
	}

	public void removePhoto(ActionEvent event) {
	}

	public void setGraphics() {
		// Tipo de ID ComboBox
		this.idTypeComboBox.setValue(Identification.DNI);
		this.idTypeComboBox.setItems(this.identifications);
		Staff staff = new Staff(); //* Test
		// Staff ID
		String id = staff.generateStaffID();
		System.out.println(id);
		this.staffIdNumberTextField.setText(id);
		// Género ComboBox
		this.genreComboBox.setItems(this.genders);
		// Estado civil ComboBox
		this.maritalStatusComboBox.setItems(this.maritalStatus);
		// Día de Registro -> En el caso de que sea un nuevo Objeto hoy
		LocalDate today = LocalDate.now();
		this.registrationDateDatePicker.setValue(today);
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
		this.changePasswordBtn.setText(rb.getString("staffUserDataCurrentPassword"));
		// Other //
		this.otherTitle.setText(rb.getString("staffOtherTitle"));
		this.commentsLabel.setText(rb.getString("staffOtherComments"));
		// Save //
		this.saveChanges.setText(rb.getString("staffSave"));
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		setLanguage();
		setGraphics();
	}

	// SETTERS //
	public void setStaffStage(Stage stage) {
		this.staffStage = stage;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}


}
