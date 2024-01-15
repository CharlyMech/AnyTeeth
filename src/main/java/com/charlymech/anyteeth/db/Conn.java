package com.charlymech.anyteeth.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoTimeoutException;
import com.mongodb.ServerAddress;

public class Conn {
	public static MongoClient mongo;

	public static boolean connectDB(String ip, int port) {
		try { // Intentar establecer la conexión con el servidor
			MongoClientOptions.Builder o = MongoClientOptions.builder().connectTimeout(2000);
			mongo = new MongoClient(new ServerAddress(ip, port), o.build());
			mongo.getAddress();
			System.out.println("Conexión exitosa");
			return true;
		} catch (MongoTimeoutException tie) {
			System.out.println("Error en la conexión");
			System.out.println(tie.getMessage());
			return false;
		}
	}

	// Método para cerrar la conexión a la base de datos
	public static void closeConnection() {
		mongo.close();
	}

}
