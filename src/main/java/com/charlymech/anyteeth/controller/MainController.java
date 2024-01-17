package com.charlymech.anyteeth.controller;

import com.calendarfx.view.CalendarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;

public class MainController {
	// Inyecciones FXML
	@FXML
	public MenuItem nueva_cita;
	@FXML
	private Button appointment_btn, add_appointment, patients_btn, budgets_btn, plans_btn, documents_btn;
	@FXML
	private ImageView bg_logo;
	@FXML
	private TabPane patients_tab_pane, budgets_tab_pane, plans_tab_pane, documents_tab_pane;
	@FXML
	private CalendarView appointments_pane;
	// Variables de clase
	public static String email; // Static por si se intenta abrir una nueva instancia del programa, que inicie sesión directamente

	// SETTERS
	public void setEmail(String email) {
		this.email = email;
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
}
// TODO -> center bg logo