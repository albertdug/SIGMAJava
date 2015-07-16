package org.ucla.sigma.components;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Clase para el manejo de la lectura y escritura de archivos de texto
 */
public abstract class HelperFileStream {

	/**
	 * Funcion que lee un archivo del disco
	 * 
	 * @param ruta
	 *            Ruta del archivo
	 * @return Arreglo de bytes
	 * @throws IOException
	 */
	public static byte[] readFile(String ruta) throws IOException {
		return readFile(new File(ruta));
	}

	/**
	 * Funcion que lee un archivo del disco
	 * 
	 * @param file
	 *            Archivo a leer
	 * @return Arreglo de bytes
	 * @throws IOException
	 */
	public static byte[] readFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];

		for (int readNum; (readNum = fis.read(buf)) != -1;) {
			bos.write(buf, 0, readNum);
		}		
		
		bos.flush();
		byte[] temp =  bos.toByteArray();
		bos.close();		
		
		return temp;
	}

	/**
	 * Funcion para escribir un archivo en disco
	 * 
	 * @param ruta
	 *            Ruta del archivo a escribir, con extension
	 * @param bytes
	 *            Datos a escribir
	 * @throws IOException
	 */
	public static void writeFile(String ruta, byte[] bytes) throws IOException {
		writeFile(new File(ruta), bytes);
	}

	/**
	 * Funcion que escribe un archivo en disco
	 * 
	 * @param file
	 *            Archivo a escribir
	 * @param bytes
	 *            Datos a escribir en archivo
	 * @throws IOException
	 */
	public static void writeFile(File file, byte[] bytes) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(bytes);
		fos.flush();
		fos.close();
	}
}
