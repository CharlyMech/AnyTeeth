package com.charlymech.anyteeth.controller;

public class StaffController {
	// Método para comprobar los cambios en la ventana emergente y modificar el valor de ventana en ejecución en el cierre
	public void checkCloseEvent() {
		MainController.windowOpened = false;
	}
}
