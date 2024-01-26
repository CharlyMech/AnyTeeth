package com.charlymech.anyteeth.controller;

import com.calendarfx.view.CalendarView;
import com.charlymech.anyteeth.App;
import com.charlymech.anyteeth.db.Staff;
import com.charlymech.anyteeth.gui.LoadApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static com.charlymech.anyteeth.App.rb;

public class MainController {
	// Inyecciones FXML
	@FXML
	private Menu menuBarFile, menuBarFileImport, menuBarFileExport, menuBarEdit, menuBarWindow, menuBarSession, menuBarCreate, menuBarHelp;
	@FXML
	private MenuItem menuBarFileImportJson, menuBarFileImportMongo, menuBarFileExportJson, menuBarFileExportMongo, menuBarFileSettings, menuBarFileClose, menuBarWindowFullScreen, menuBarWindowWindowed, menuBarSessionUser, menuBarSessionClose, menuBarCreateAppointment, menuBarCreatePatient, menuBarCreateClient, menuBarCreateBudget, menuBarCreatePlan, menuBarCreateTicket, menuBarCreateInvoice, menuBarCreateStaff, menuBarHelpAbout, menuBarHelpDocumentation, menuBarHelpAdmin;
	@FXML
	private RadioMenuItem menuBarWindowLightTheme, menuBarWindowDarkTheme;
	@FXML
	private Button asideAppointments, asidePatients, asideBudgets, asidePlans, asideDocuments, asideStaff;
	@FXML
	private ImageView bg_logo;
	@FXML
	private TabPane patients_tab_pane, budgets_tab_pane, plans_tab_pane, documents_tab_pane;
	@FXML
	private CalendarView appointments_pane;
	// Variables de clase
	public Staff userSession;

	// Método para aplicar las propiedades de idioma a los elementos gráficos
	public void setLanguageProperties() {
		// Menu Bar //
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
		this.menuBarCreateStaff.setText(rb.getString("menuBarCreateStaff"));
		// Ayuda / Help
		this.menuBarHelp.setText(rb.getString("menuBarHelp"));
		this.menuBarHelpAbout.setText(rb.getString("menuBarHelpAbout"));
		this.menuBarHelpDocumentation.setText(rb.getString("menuBarHelpDocumentation"));
		this.menuBarHelpAdmin.setText(rb.getString("menuBarHelpAdmin"));

		// Menu lateral
		this.asideAppointments.setText(rb.getString("asideAppointments"));
		this.asidePatients.setText(rb.getString("asidePatients"));
		this.asideBudgets.setText(rb.getString("asideBudgets"));
		this.asidePlans.setText(rb.getString("asidePlans"));
		this.asideDocuments.setText(rb.getString("asideDocuments"));
		this.asideStaff.setText(rb.getString("asideStaff"));

		// Contenido principal
	}

	// Método para mostrar el panel de Citas
	public void showAppointments(ActionEvent event) {
		this.appointments_pane.setVisible(true); // Panel de Citas visible
		// Resto de paneles invisibles
		this.patients_tab_pane.setVisible(false);
		this.budgets_tab_pane.setVisible(false);
		this.plans_tab_pane.setVisible(false);
		this.documents_tab_pane.setVisible(false);
		this.bg_logo.setVisible(false);
	}

	// Método para mostrar el panel de Pacientes
	public void showPatiens(ActionEvent event) {
		this.patients_tab_pane.setVisible(true); // Panel de Pacientes visible
		// Resto de paneles invisibles
		this.appointments_pane.setVisible(false);
		this.budgets_tab_pane.setVisible(false);
		this.plans_tab_pane.setVisible(false);
		this.documents_tab_pane.setVisible(false);
		this.bg_logo.setVisible(false);
	}

	// Método para mostrar el panel de Presupuestos
	public void showBudgets(ActionEvent event) {
		this.budgets_tab_pane.setVisible(true); // Panel de Presupuestos visible
		// Resto de paneles invisibles
		this.appointments_pane.setVisible(false);
		this.patients_tab_pane.setVisible(false);
		this.plans_tab_pane.setVisible(false);
		this.documents_tab_pane.setVisible(false);
		this.bg_logo.setVisible(false);
	}

	// Método para mostrar el panel de Planes
	public void showPlans(ActionEvent event) {
		this.plans_tab_pane.setVisible(true); // Panel de Planes visible
		// Resto de paneles invisibles
		this.appointments_pane.setVisible(false);
		this.patients_tab_pane.setVisible(false);
		this.budgets_tab_pane.setVisible(false);
		this.documents_tab_pane.setVisible(false);
		this.bg_logo.setVisible(false);
	}

	// Método para mostrar el panel de Documentos
	public void showDocuments(ActionEvent event) {
		this.documents_tab_pane.setVisible(true); // Panel de Planes visible
		// Resto de paneles invisibles
		this.appointments_pane.setVisible(false);
		this.patients_tab_pane.setVisible(false);
		this.budgets_tab_pane.setVisible(false);
		this.plans_tab_pane.setVisible(false);
		this.bg_logo.setVisible(false);
	}

	public void addNewAppointment(ActionEvent event) {
		System.out.println("NEW APPOINTMENT");
	}

	public void openStaffManagement(ActionEvent event) {
	}

	// SETTERS
	public void setUserSession(Staff staff) {
		this.userSession = staff;
	}

	public void logout(Stage stage) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // Crear la alerta de tipo confirmación
		alert.setTitle("TITLE");
		alert.setHeaderText("Header");
		alert.setContentText("This is the alert content");

		if (alert.showAndWait().get() == ButtonType.OK) { // El usuario desea salir de la app
			LoadApp.launchLogIn(stage);
		}
	}
}
// TODO -> center bg logo