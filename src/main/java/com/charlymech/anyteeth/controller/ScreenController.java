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
		/*
		// Instanciar los objetos necesarios para obtener el número de la pantalla
		Rectangle2D stageBounds = new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getWidth()); // Bound con las propiedades de la pantalla en la que se encuentra el stage
		ObservableList<Screen> screens = Screen.getScreens(); // Listado de las pantallas disponibles
		// Iterar sobre la lista de pantallas para buscar la intersección con el bound de la pantalla del stage
		for(Screen screen : screens) {
			Rectangle2D screenBounds = screen.getScreensForRectangle();
			if (screenBounds.intersects(stageBounds)) {
				return screens.indexOf(screen)+1; // Normalmente la numeración de las pantallas empieza por 1
			}
		}
		// En caso de que algo vaya mal
		return -1;
		*/
		Point2D mouseLocation = new Point2D( // Assuming the cursor location is (0, 0)
				Screen.getPrimary().getBounds().getMinX(),
				Screen.getPrimary().getBounds().getMinY()
		);

		// Iterate over available screens and find the screen with an intersection
		Screen[] screens = Screen.getScreensForRectangle(mouseLocation.getX(), mouseLocation.getY(), 1, 1).toArray(new Screen[0]);
		for (int i = 0; i < screens.length; i++) {
			Rectangle2D screenBounds = screens[i].getBounds();
			if (screenBounds.contains(mouseLocation)) {
				return i + 1; // Screen numbers are typically 1-indexed
			}
		}

		return -1; // Not found
	}
}
