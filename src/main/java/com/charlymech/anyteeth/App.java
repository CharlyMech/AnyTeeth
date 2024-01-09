package com.charlymech.anyteeth;

import com.charlymech.anyteeth.gui.LogIn;
import javafx.application.Application;

public class App {
	// Esta clase únicamente está pensada para establecer la conexión a la base de datos y llamar al Log In
	public static void main(String[] args) {
		System.out.println("BIENVENIDO A ANYTEETH"); // TEST
		Application.launch(LogIn.class, args);
	}
}
