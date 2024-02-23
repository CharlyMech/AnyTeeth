package com.charlymech.anyteeth.controller;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/*
 * Esta clase está pensada únicamente para manejar el comportamiento de las ventanas en los diferentes posibles
 * monitores que puedan existir durante la ejecución del programa.
 *
 * Todos los métodos y variables de esta clase serán accedidos de forma estática dado que no es necesario instanciar objeto de esta
 */
public class ScreenController {
	// Variables de clase
//	protected static mainSt
	// Método para aplicar propiedades a la ventana dada
	/**
	 * @param stage -> El Stage de la nueva ventana a ejecutar o de la ventana
	 * @param screenNum -> Número de la pantalla en caso de que haya varias
	 * @param maximized -> Variable indicativa de si se desea que esté maximizada o no
	 */
	public static void manageScreen(Stage stage, int screenNum, boolean maximized) {
		// Instanciar los objetos para usar las propiedades del monitor principal
		ObservableList<Screen> screens = Screen.getScreens(); // Lista con los monitores instalados
		Screen screen = (screens.size() <= screenNum || screenNum <= 0) ? Screen.getPrimary() : screens.get(screenNum); // Utilizar el objeto Screen para la pantalla indicada
		// Comprobar el valor booleano para maximizar la ventana y aplicar el método correspondiente
		if (maximized) {
			setMaximizedWindow(stage, screen); // Llamar al método para poner la pantalla maximizada
		} else {
			// Ya que no se quiere la pantalla maximizada, centrarla en la pantalla con el número indicado
			setCenteredWindow(stage, screen);
		}
		stage.setMaximized(maximized);
	}

	private static void setCenteredWindow(Stage stage, Screen screen) {
		Rectangle2D bounds = screen.getBounds();
		double centerX = bounds.getMinX() + (bounds.getWidth() - stage.getWidth()) / 2;
		double centerY = bounds.getMinY() + (bounds.getHeight() - stage.getHeight()) / 2;
		stage.setX(centerX);
		stage.setY(centerY);
	}

	private static void setMaximizedWindow(Stage stage, Screen screen) {
		Rectangle2D bounds = screen.getBounds();
		stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
	}

	// Método para conseguir el número de pantalla en la que se encuentra el Stage dado
	public static int getScreenNumber(Stage stage) {
		Rectangle2D stageBounds = new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
		Screen[] screens = Screen.getScreensForRectangle(stageBounds).toArray(new Screen[0]); // Buscar la intersección del stage con las pantallas

		if (screens.length > 0) {
			return Screen.getScreens().indexOf(screens[0]); // Retornar el número de la pantalla
		} else {
			return -1;
		}
	}
}
