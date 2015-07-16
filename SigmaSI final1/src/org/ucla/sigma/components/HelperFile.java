package org.ucla.sigma.components;

import java.io.File;

/**
 * Clase para el manejo de los archivos
 */
public abstract class HelperFile {

	public static final String TYPE_PNG = "png";
	public static final String TYPE_JPG = "jpg";
	public static final String TYPE_GIF = "gif";

	/**
	 * Esta funcion extrae el nombre del archivo sin la extension
	 * 
	 * @param file
	 *            Archivo
	 * @return Nombre del archivo
	 */
	public static String getOnlyName(File file) {
		return getOnlyName(file.getName());
	}

	/**
	 * Esta funcion extrae el nombre del archivo sin la extension
	 * 
	 * @param name
	 *            Nombre del archivo con extension
	 * @return Nombre del archivo
	 */
	public static String getOnlyName(String name) {
		return name.substring(0, name.lastIndexOf('.'));
	}

	/**
	 * Esta funcion calcula los Bytes, KB y MB a partir de un archivo
	 * 
	 * @param file
	 *            Archivo a calcular tama#o
	 * @return String con el tama#o
	 */
	public static String getSize(File file) {
		return getSize(file.length());
	}

	/**
	 * Esta funcion calcula los Bytes, KB y MB a partir de un arreglo de bytes
	 * 
	 * @param byteArray
	 *            Bytes a calcular tama#o
	 * @return String con el tama#o
	 */
	public static String getSize(byte[] byteArray) {
		return getSize(byteArray.length);
	}

	/**
	 * Esta funcion calcula los Bytes, KB y MB de una cantidad
	 * 
	 * @param size
	 *            Tama#o del archivo en entero
	 * @return String con el tama#o
	 */
	public static String getSize(long size) {
		String sizeString = "";
		double KB = 1024;
		double MB = KB * 1024;
		if (size < KB) {
			sizeString = size + " Bytes";
		} else if (size > KB && size < MB) {
			sizeString = String.format("%.2f KB", (size / KB));
		} else {
			sizeString = String.format("%.2f MB", (size / MB));
		}
		return sizeString;
	}

	/**
	 * Esta funcion toma los ultimos caracteres a partir del punto
	 * 
	 * @param file
	 *            Archivo
	 * @return Extension del archivo ejemplo: png, jpg, txt
	 */
	public static String getExtension(File file) {
		return getExtension(file.getName());
	}

	/**
	 * Esta funcion toma los ultimos caracteres a partir del punto
	 * 
	 * @param fileName
	 *            Nombre del archivo
	 * @return Extension del archivo ejemplo: png, jpg, txt
	 */
	public static String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.') + 1,
				fileName.length());
	}

	/**
	 * Esta funcion verifica si el archivo posee alguna de las extensiones
	 * pasadas por parametros
	 * 
	 * @param file
	 *            Archivo a verificar
	 * @param extensions
	 *            Extensiones permitidas
	 * @return Verdadero si coincide con alguna extension, false si no
	 */
	public static boolean isFileType(File file, String... extensions) {
		return isFileType(file.getName(), extensions);
	}

	/**
	 * Esta funcion verefica si el archivo posee alguna de las extensiones
	 * pasadas por parametros
	 * 
	 * @param fileName
	 *            Nombre de archivo a verificar
	 * @param extensions
	 *            Extensiones permitidas
	 * @return Verdadero si coincide con alguna extension, false si no
	 */
	public static boolean isFileType(String fileName, String... extensions) {
		for (String extension : extensions) {
			if (fileName.toUpperCase().endsWith(extension.toUpperCase()))
				return true;
		}
		return false;
	}
}
