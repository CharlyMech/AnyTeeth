package com.charlymech.anyteeth.controller;

/*
 * Esta interfaz está pensada para ser implementada en los controladores de las GUI y
 * ejecutar los métodos de carga de propiedades, lenguaje, etc.
 */
public interface Properties {
	void setGraphics(); // Aplicar las propiedades gráficas necesarias

	void setLanguage(); // Aplicar las propiedades de lenguaje
}
