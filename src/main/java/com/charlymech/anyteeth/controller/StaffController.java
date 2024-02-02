package com.charlymech.anyteeth.controller;

import com.charlymech.anyteeth.Enums.Gender;
import com.charlymech.anyteeth.Enums.Identification;
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
import java.util.ResourceBundle;

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
	private final ObservableList<Gender> genders = FXCollections.observableArrayList(Gender.MALE, Gender.FEMALE);
	//	private final ObservableList<Person.Gender> maritalStatus = FXCollections.observableArrayList(Person.Gender.MALE, Person.Gender.FEMALE); // TODO -> Crear clase enumerada
	private final ObservableList<Staff.Role> roles = FXCollections.observableArrayList(Staff.Role.STAFF, Staff.Role.CLINIC_ADMIN, Staff.Role.ADMIN);
	private final ObservableList<String> provinces = FXCollections.observableArrayList("Álava", "Albacete", "Alicante", "Almería", "Asturias ", "Avila", "Badajoz", "Barcelona", " Burgos", "Cáceres", "Cádiz", "Cantabria", "Castellón", "Ciudad Real", "Córdoba", "La Coruña", "Cuenca", "Gerona", "Granada", "Guadalajara", "Guipúzcoa", "Huelva", "Huesca", "Balearic Islands", "Jaén", "León", "Lérida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra", "Orense", "Palencia", "Las Palmas", "Pontevedra", "La Rioja", "Salamanca", "Segovia", "Seville", "Soria", "Tarragona", "Santa Cruz de Tenerife", "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza");
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
		Staff staff = new Staff();
		// Staff ID
		String id = staff.generateStaffID();
		System.out.println(id);
		this.staffIdNumberTextField.setText(id);
	}

	public void setLanguage() {

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
