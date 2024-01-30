package com.charlymech.anyteeth.controller;

import com.calendarfx.view.CalendarView;
import com.charlymech.anyteeth.App;
import com.charlymech.anyteeth.db.Staff;
import com.charlymech.anyteeth.gui.LoadApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static com.charlymech.anyteeth.App.rb;

public class MainController {
	// Inyecciones FXML
	@FXML
	private Menu menuBarFile, menuBarFileImport, menuBarFileExport, menuBarEdit, menuBarWindow, menuBarSession, menuBarCreate, menuBarHelp;
	@FXML
	private MenuItem menuBarFileImportJson, menuBarFileImportMongo, menuBarFileExportJson, menuBarFileExportMongo, menuBarFileSettings, menuBarFileClose, menuBarWindowFullScreen, menuBarWindowWindowed, menuBarSessionUser, menuBarSessionClose, menuBarCreateAppointment, menuBarCreatePatient, menuBarCreateClient, menuBarCreateBudget, menuBarCreatePlan, menuBarCreateTicket, menuBarCreateInvoice, menuBarCreateReport, menuBarCreateStaff, menuBarHelpAbout, menuBarHelpDocumentation, menuBarHelpAdmin;
	@FXML
	private RadioMenuItem menuBarWindowLightTheme, menuBarWindowDarkTheme;
	@FXML
	private Button asideAppointments, asidePatients, asideBudgets, asidePlans, asideDocuments, asideStaff, addStaffBtn;
	@FXML
	private ImageView bgLogo;
	@FXML
	private BorderPane staffPane;
	@FXML
	private TabPane patientsTabPane, budgetsTabPane, plansTabPane, documentsTabPane;
	@FXML
	private Tab patientsPatients, patientsClients, budgetsActive, budgetsTemplates, budgetsNew, plansActive, plansNew, documentsTickets, documentsInvoices, documentsReports, documentsOthers;
	@FXML
	private TextField staffSearchBar;
	@FXML
	private ComboBox staffSearchFilter;
	@FXML
	private CalendarView appointmentsPane;
	// Variables de clase
	public Staff userSession;
	private boolean windowOpened = false; // Variable indicativa de una ventana externa abierta -> Inicializada en falso

	// Método para aplicar todas las propiedades necesarias en el programa principal
	public void setProperties() {
		this.setUserSessionPermissions(); // Asignar los permisos de usuario
		this.setLanguageProperties(); // Cargar los elementos de idioma
		this.setGraphics(); // Cargar las propiedades gráficas
	}

	// Método para cargar propiedades gráficas
	private void setGraphics() {
		// STAFF //
		this.addStaffBtn.setCursor(Cursor.HAND);
		this.staffSearchFilter.setCursor(Cursor.HAND);
	}

	// Método para leer el tipo de usuario y mostrar el contenido acorde a sus permisos
	private void setUserSessionPermissions() {
		// En este punto el usuario Staff ya ha sido asignado
		if (userSession.getRole() == Staff.Role.ADMIN || userSession.getRole() == Staff.Role.CLINIC_ADMIN) {
			this.asideStaff.setVisible(true);
			this.menuBarCreateStaff.setVisible(true);
		}
	}

	// Método para aplicar las propiedades de idioma a los elementos gráficos
	private void setLanguageProperties() {
		// Menu Bar -//
		// Archivo / File
		this.menuBarFile.setText(rb.getString("menuBarFile"));
		this.menuBarFileImport.setText(rb.getString("menuBarFileImport"));
		this.menuBarFileImportJson.setText(rb.getString("menuBarFileImportJson"));
		this.menuBarFileImportMongo.setText(rb.getString("menuBarFileImportMongo"));
		this.menuBarFileExport.setText(rb.getString("menuBarFileExport"));
		this.menuBarFileExportJson.setText(rb.getString("menuBarFileExportJson"));
		this.menuBarFileExportMongo.setText(rb.getString("menuBarFileExportMongo"));
		this.menuBarFileSettings.setText(rb.getString("menuBarFileSettings"));
		this.menuBarFileClose.setText(rb.getString("menuBarFileClose"));
		// Editar / Edit
		this.menuBarEdit.setText(rb.getString("menuBarEdit"));
		//TODO -> Más elementos aquí, decidir

		// Ventana / Window
		this.menuBarWindow.setText(rb.getString("menuBarWindow"));
		this.menuBarWindowFullScreen.setText(rb.getString("menuBarWindowFullScreen"));
		this.menuBarWindowWindowed.setText(rb.getString("menuBarWindowWindowed"));
		this.menuBarWindowLightTheme.setText(rb.getString("menuBarWindowLightTheme"));
		this.menuBarWindowDarkTheme.setText(rb.getString("menuBarWindowDarkTheme"));
		// Sesión / Session
		this.menuBarSession.setText(rb.getString("menuBarSession"));
		this.menuBarSessionUser.setText(rb.getString("menuBarSessionUser"));
		this.menuBarSessionClose.setText(rb.getString("menuBarSessionClose"));
		// Crear / Create
		this.menuBarCreate.setText(rb.getString("menuBarCreate"));
		this.menuBarCreateAppointment.setText(rb.getString("menuBarCreateAppointment"));
		this.menuBarCreatePatient.setText(rb.getString("menuBarCreatePatient"));
		this.menuBarCreateClient.setText(rb.getString("menuBarCreateClient"));
		this.menuBarCreateBudget.setText(rb.getString("menuBarCreateBudget"));
		this.menuBarCreatePlan.setText(rb.getString("menuBarCreatePlan"));
		this.menuBarCreateTicket.setText(rb.getString("menuBarCreateTicket"));
		this.menuBarCreateInvoice.setText(rb.getString("menuBarCreateInvoice"));
		this.menuBarCreateReport.setText(rb.getString("menuBarCreateReport"));
		this.menuBarCreateStaff.setText(rb.getString("menuBarCreateStaff"));
		// Ayuda / Help
		this.menuBarHelp.setText(rb.getString("menuBarHelp"));
		this.menuBarHelpAbout.setText(rb.getString("menuBarHelpAbout"));
		this.menuBarHelpDocumentation.setText(rb.getString("menuBarHelpDocumentation"));
		this.menuBarHelpAdmin.setText(rb.getString("menuBarHelpAdmin"));
		//- Menu Bar //

		// Menu lateral -//
		this.asideAppointments.setText(rb.getString("asideAppointments"));
		this.asidePatients.setText(rb.getString("asidePatients"));
		this.asideBudgets.setText(rb.getString("asideBudgets"));
		this.asidePlans.setText(rb.getString("asidePlans"));
		this.asideDocuments.setText(rb.getString("asideDocuments"));
		this.asideStaff.setText(rb.getString("asideStaff"));
		//- Menu lateral //

		// Contenido principal -//
		// Pacientes / Patients TabPane
		this.patientsPatients.setText(rb.getString("patientsPatientsTab"));
		this.patientsClients.setText(rb.getString("patientsClientsTab"));

		// Presupuestos / Budgets TabPane
		//? No necesario por el momento

		// Planes / Plans TabPane
		//? No necesario por el momento

		// Documentos / Documents TabPane
		this.documentsTickets.setText(rb.getString("documentsTicketsTab"));
		this.documentsInvoices.setText(rb.getString("documentsInvoicesTab"));
		this.documentsReports.setText(rb.getString("documentsReportsTab"));
		this.documentsOthers.setText(rb.getString("documentsOthersTab"));

		//- Contenido principal //
	}

	// Método para mostrar el panel de Citas
	public void showAppointments(ActionEvent event) {
		this.appointmentsPane.setVisible(true); // Panel de Citas visible
		// Resto de paneles invisibles
		this.patientsTabPane.setVisible(false);
		this.budgetsTabPane.setVisible(false);
		this.plansTabPane.setVisible(false);
		this.documentsTabPane.setVisible(false);
		this.staffPane.setVisible(false);
		this.bgLogo.setVisible(false);
	}

	// Método para mostrar el panel de Pacientes
	public void showPatiens(ActionEvent event) {
		this.patientsTabPane.setVisible(true); // Panel de Pacientes visible
		// Resto de paneles invisibles
		this.appointmentsPane.setVisible(false);
		this.budgetsTabPane.setVisible(false);
		this.plansTabPane.setVisible(false);
		this.documentsTabPane.setVisible(false);
		this.staffPane.setVisible(false);
		this.bgLogo.setVisible(false);
	}

	// Método para mostrar el panel de Presupuestos
	public void showBudgets(ActionEvent event) {
		this.budgetsTabPane.setVisible(true); // Panel de Presupuestos visible
		// Resto de paneles invisibles
		this.appointmentsPane.setVisible(false);
		this.patientsTabPane.setVisible(false);
		this.plansTabPane.setVisible(false);
		this.documentsTabPane.setVisible(false);
		this.staffPane.setVisible(false);
		this.bgLogo.setVisible(false);
	}

	// Método para mostrar el panel de Planes
	public void showPlans(ActionEvent event) {
		this.plansTabPane.setVisible(true); // Panel de Planes visible
		// Resto de paneles invisibles
		this.appointmentsPane.setVisible(false);
		this.patientsTabPane.setVisible(false);
		this.budgetsTabPane.setVisible(false);
		this.documentsTabPane.setVisible(false);
		this.staffPane.setVisible(false);
		this.bgLogo.setVisible(false);
	}

	// Método para mostrar el panel de Documentos
	public void showDocuments(ActionEvent event) {
		this.documentsTabPane.setVisible(true); // Panel de Planes visible
		// Resto de paneles invisibles
		this.appointmentsPane.setVisible(false);
		this.patientsTabPane.setVisible(false);
		this.budgetsTabPane.setVisible(false);
		this.plansTabPane.setVisible(false);
		this.staffPane.setVisible(false);
		this.bgLogo.setVisible(false);
	}

	public void showStaff(ActionEvent event) {
		this.staffPane.setVisible(true);
		// Resto de paneles invisibles
		this.documentsTabPane.setVisible(false); // Panel de Planes visible
		this.appointmentsPane.setVisible(false);
		this.patientsTabPane.setVisible(false);
		this.budgetsTabPane.setVisible(false);
		this.plansTabPane.setVisible(false);
		this.bgLogo.setVisible(false);
	}

	// Método de muestra del error de ventana externa en ejecución
	private void showExecutingWindowAlert() {
		// Como este mismo código puede verse repetido (es el mismo mensaje) lo agrupo en la función
		App.showWarningAlert(rb.getString("executingWindowTitle"),  rb.getString("executingWindowHead"), rb.getString("executingWindowContent"));
	}

	public void addNewAppointment(ActionEvent event) {
		System.out.println("NEW APPOINTMENT");
	}

	// SETTERS
	public void setUserSession(Staff staff) {
		this.userSession = staff;
	}

	public void logout(Stage stage) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // Crear la alerta de tipo confirmación
		alert.setTitle(rb.getString("alertTitle"));
		alert.setHeaderText(rb.getString("logoutAlert"));
		alert.setContentText(rb.getString("logoutAlertContent"));

		if (alert.showAndWait().get() == ButtonType.OK) { // El usuario desea salir de la app
			LoadApp.launchLogIn(stage);
		}
	}

	public void addNewStaff(ActionEvent event) {
		System.out.println("Open create staff window");
		if (!this.windowOpened) { // No existe ventana externa abierta -> Ejecutar nueva ventana
			this.windowOpened = true;
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/charlymech/anyteeth/layout/new-staff.fxml"));
				Parent root = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.setTitle("Second Window");
				stage.setScene(new Scene(root));
				stage.show();
			} catch (Exception e) {
				App.showErrorAlert("ERROR", "Error trying to open the window", "The new Staff window couldn't be opened");
				System.out.println("Cant load new window");
			}
		} else { // Existe una ventana externa en ejecución -> Monstrar mensaje de error
			this.showExecutingWindowAlert();
		}
	}
}
// TODO -> center bg logo